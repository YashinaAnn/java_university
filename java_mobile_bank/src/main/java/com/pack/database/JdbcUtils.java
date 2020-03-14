package com.pack.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {

    private static Connection connection;
    private static final String USER = "jdbc.username";
    private static final String PASSWORD = "jdbc.password";
    private static final String URL = "jdbc.url";
    private static final String DRIVER = "jdbc.driverClassName";
    private static final String PROPERTY = "src/main/resources/mysql.jdbc.properties";

    public static boolean createConnection() {
        Properties property = new Properties();
        try (FileInputStream fis = new FileInputStream(PROPERTY)) {
            property.load(fis);
            String user = property.getProperty(USER);
            String password = property.getProperty(PASSWORD);
            String url = property.getProperty(URL);
            String driver = property.getProperty(DRIVER);
            Class.forName(driver);
            connection = DriverManager.getConnection(url, user, password);
            return true;
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static Connection getConnection() {
        if (null == connection) {
             createConnection();
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) { }
    }
}
