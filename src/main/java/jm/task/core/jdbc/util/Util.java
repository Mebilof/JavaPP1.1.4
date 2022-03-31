package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static Connection util;

    private static final String URL = "jdbc:postgresql://localhost:5432/MyBase";
    private static final String NAME = "postgres";
    private static final String PASS = "254954";

    private Util() {
    }

    public static Connection getConnection() {
        if (util == null) {
            try {
                util = DriverManager.getConnection(URL, NAME, PASS);
            } catch (SQLException e) {
                System.out.println("no connect");
            }
        }
        System.out.println("connection ok");
        return util;
//        Connection conn = null;
//        try {
//            String userName = "root";
//            String password = "09250925";
//            conn = DriverManager.getConnection(URL, NAME, PASS);
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("connection failed");
//        }
//
//        return conn;
    }

    public static void close() throws SQLException {
        util.close();
    }

}
