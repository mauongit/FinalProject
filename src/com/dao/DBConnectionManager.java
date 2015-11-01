package com.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionManager {
	 
    private static Connection connection;
    public DBConnectionManager(){}
     
    public DBConnectionManager(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException{
    	System.out.println("DBConnectionManager1: "+dbURL+user+pwd);
    	Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(dbURL, user, pwd);
        System.out.println("DBConnectionManager2: "+connection);
    }
     
    public Connection getConnection(){
        return connection;
    }
}