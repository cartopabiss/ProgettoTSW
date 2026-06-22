package dao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

    private static DataSource dataSource;

    private DBConnection() {
    }

    public static synchronized DataSource getDataSource() {

        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                dataSource = (DataSource) initContext.lookup("java:comp/env/jdbc/dacapoapiede");
            } catch (NamingException e) {
                throw new RuntimeException("Impossibile recuperare il DataSource", e);
            }
        }

        return dataSource;
    }
}