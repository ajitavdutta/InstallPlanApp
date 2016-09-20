package com.app.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AppDao { 
	private static String dbURL = "jdbc:derby:db/.AppDB;create=true;user=Admin;password=admin";
	
	/*private static String PRIMECODE_OBJECTS_TABLE = "CREATE TABLE APP.PRIMECODE_OBJECTS("
			+ "OBJ_ID int NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
			+ "NAME varchar(10) NOT NULL,"
			+ "PC_LOC varchar(50) NOT NULL,"
			+ "IN_PROD varchar(3) NOT NULL,"
			+ "   B24_VER int NOT NULL,"
			+ "REASON_ varchar(500) DEFAULT ''"
			+ ")";*/
	
	/**
	 * Returns a connection to the DB associated with this application.
	 * 
	 * @return dbConnection
	 */
	public static Connection getDBConnection() {
		try {
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			Connection dbConnection = DriverManager.getConnection(dbURL);
			if (dbConnection != null) {
				System.out.println("Connected to database...");
			}
			return dbConnection;
		} catch (Exception except) {
			except.printStackTrace();
			return null;
		}
	}
	
	
	public static ResultSet executeStmt(Connection dbConnection, String query){
		ResultSet rs = null;
		if(dbConnection != null){
			try {
				Statement stmt = dbConnection.createStatement();
				rs = stmt.executeQuery(query);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return rs;
	}
}
