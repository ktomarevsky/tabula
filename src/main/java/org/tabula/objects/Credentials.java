package org.tabula.objects;

import org.tabula.utils.dbutils.DBType;

import java.io.Serializable;

public class Credentials implements Serializable {

    private String connectionName;
    private String login;
    private String password;
    private String host;
    private String port;
    private String sid;

    private DBType dbType;

    public Credentials() {
        connectionName = "";
        login = "";
        password = "";
        host = "";
        port = "";
        sid = "";
    }

    public String getConnectionName() {
        return connectionName;
    }

    public void setConnectionName(String connectionName) {
        this.connectionName = connectionName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUrl() {
        return "jdbc:oracle:thin:@" + host + ":" + port + ":" + sid;
    }

    public DBType getDbType() {
        return dbType;
    }

    public void setDbType(DBType dbType) {
        this.dbType = dbType;
    }
}
