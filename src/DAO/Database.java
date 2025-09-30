package DAO;

import java.sql.Connection;
import java.sql.DriverManager;

public class Database {
    private static Connection connection = null;

    private static final String URL = "jdbc:mysql://localhost:3306/micro_credit";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }

}