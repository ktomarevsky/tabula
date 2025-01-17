package org.tabula.utils.dbutils;

import org.tabula.objects.Credentials;
import org.tabula.objects.Tableset;
import org.tabula.objects.model.Model;
import org.tabula.utils.dbutils.oracledbutils.OracleDB;

public enum DBType {

    ORACLE(new OracleDB<>());
    private DBLoader<Credentials, Tableset, Model> dbLoader;
    DBType(DBLoader<Credentials, Tableset, Model> dbLoader) {
        this.dbLoader = dbLoader;
    }

    public DBLoader<Credentials, Tableset, Model> getDBLoader() {
        return dbLoader;
    }
}
