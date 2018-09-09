package database;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;


public class ConnectionDB {
    private static Connection connection;
    private static final String DSN = "jdbc:postgresql://localhost:5432/healthtime";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String DRIVER = "org.postgresql.Driver";

    public static Connection getConnection() throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
        if (connection == null) {
            // Carrega o driver
            Class.forName(DRIVER).newInstance();

            // Conecta o BD
            connection = DriverManager.getConnection(DSN, USER, PASSWORD);
        }

        return connection;
    }

}
