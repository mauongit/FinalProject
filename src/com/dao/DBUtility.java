package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtility {
	private static Connection connection = null;

	public static Connection getConnection() throws Exception {
		/*
		 * if (connection != null) return connection; else { // Store the
		 * database URL in a string String serverName = "113.128.164.85"; String
		 * portNumber = "3306"; String sid = "XE"; String dbUrl =
		 * "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
		 * Class.forName("oracle.jdbc.driver.OracleDriver");
		 * 
		 * // set the url, username and password for the databse connection =
		 * DriverManager.getConnection(dbUrl, "system", "admin"); return
		 * connection; } }
		 */

		/*try {
			java.sql.Connection conn = DriverManager.getConnection(
					"jdbc:mysql://113.128.164.84/test", "root", "admin");
			System.out.println("connection successful");
		} catch (SQLException e) { 
			// TODO Auto-generated catch block
					 e.printStackTrace();
		}
		return connection;
	}*/
	
	 Class.forName("com.mysql.jdbc.Driver");
	String url = "jdbc:mysql://localhost:3306/mysql";
	String user = "root";
	String password = "admin";
	 
	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	} catch (Exception ex) {
	    System.err.println(ex.getMessage());
	}
	 connection = DriverManager.getConnection(url, user, password);
	 System.out.println("Connection +"+ connection);
	 return connection;
}}
