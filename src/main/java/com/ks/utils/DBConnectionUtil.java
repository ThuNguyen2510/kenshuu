package com.ks.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionUtil {
	private static final String URL = "jdbc:postgresql://localhost:5432/kenshuu";

	private static final String DRIVER = "org.postgresql.Driver";

	private static final String USERNAME = "postgres";

	private static final String PASSWORD = "123456";

	private static Connection connection = null;

	public static Connection openConnection() throws SQLException {
            try {
                Class.forName(DRIVER);
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("CONNECT SUCCESS");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
}
