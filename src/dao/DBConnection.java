package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/ems_db";
    private static final String USER = "root"; // your DB user
    private static final String PASSWORD = "root123"; // your DB password
    private static Connection connection = null;

    private DBConnection() {}

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("✅ Database Connected Successfully!");
            } catch (ClassNotFoundException e) { e.printStackTrace(); }
        }
        return connection;
    }
}
