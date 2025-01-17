package org.tabula.utils.dbutils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

abstract public class DBLoader<C, T, R> {

    protected C credentials;

    protected Connection connection;
    protected Statement statement;
    protected ResultSet resultSet;

//    public DBLoader(C credentials) {
//        this.credentials = credentials;
//    }

    abstract public void setCredentials(C credentials);
    abstract protected void createConnection() throws IOException, ClassNotFoundException, SQLException;
    abstract protected void createStatement(T parameter) throws SQLException, IOException;
    abstract protected void loadData(R model) throws SQLException;
    abstract protected void closeConnection() throws SQLException;

    final public void executeRequest(R model, T parameter) {
        try {
            createConnection();
            createStatement(parameter);
            loadData(model);
        } catch (IOException | ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                closeConnection();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }
    }
}
