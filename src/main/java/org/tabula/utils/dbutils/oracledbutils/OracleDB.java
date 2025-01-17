package org.tabula.utils.dbutils.oracledbutils;

import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;
import org.tabula.objects.model.Model;
import org.tabula.utils.datautils.ModelLinker;
import org.tabula.utils.dbutils.DBLoader;
import org.tabula.utils.fileutils.FileReaderWriter;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDB<T, R> extends DBLoader<Credentials, Tableset, Model> {

    static final private String SELECT_PART1 = "oracle_select_part1";
    static final private String SELECT_PART2 = "oracle_select_part2";

//    public OracleDB(Credentials credentials) {
//        super(credentials);
//    }

    static final private String ORACLE_DRIVER = "oracle_driver";

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }

    @Override
    protected void createConnection() throws IOException, ClassNotFoundException, SQLException {

        Class.forName(FileReaderWriter.getParameter(ORACLE_DRIVER));
        connection = DriverManager.getConnection(credentials.getUrl(), credentials.getLogin(), credentials.getPassword());
    }

    @Override
    protected void createStatement(Tableset tableset) throws SQLException, IOException {
        statement = connection.createStatement();
        String select = FileReaderWriter.getParameter(SELECT_PART1) + tableset.getFormattedTables() + FileReaderWriter.getParameter(SELECT_PART2);
        resultSet = statement.executeQuery(select);
    }

    protected void loadData(Model model) throws SQLException {
        while(resultSet.next()) {
            String tableFromName = resultSet.getString("TABLE_FROM");
            String fieldFromName = resultSet.getString("FIELD_FROM");
            String tableToName = resultSet.getString("TABLE_TO");
            String fieldToName = resultSet.getString("FIELD_TO");

            ModelLinker.compose(model, tableFromName, fieldFromName, tableToName, fieldToName);
        }
    }

    @Override
    protected void closeConnection() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }
}
