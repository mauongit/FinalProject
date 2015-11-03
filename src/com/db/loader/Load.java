package com.db.loader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Load {

	private static String JDBC_CONNECTION_URL = 
			"jdbc:oracle:thin:SCOTT/TIGER@localhost:1500:MyDB";

	
	public void fileUpload(Connection connection) {
		try {

			CSVLoader loader = new CSVLoader(connection);
			loader.loadCSV("address.csv", "address", false,false);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Connection getCon() {
		Connection connection = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(JDBC_CONNECTION_URL);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return connection;
	}
}
