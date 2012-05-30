package com.id.scanner.server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DerbyDB {
	private static String dbURL = "jdbc:derby:/home/petru/test/derby/IDdata;create=true;";

	static final String TABLE_NAME = "IDdata";
	
	static final String INDEX = "idData";
	
	private static final String KEY_FNAME = "Nume";
	private static final String KEY_LNAME = "Prenume";
	private static final String KEY_S = "Seria";
	private static final String KEY_NR = "Numarul";
	private static final String KEY_CNP = "CNP";
	private static final String KEY_NAT = "Cetatenia";
	private static final String KEY_VALID = "Valabilitate";
	private static final String KEY_PICTURE = "Poza";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " integer primary key, " +
			KEY_FNAME + " varchar(25) not null, " +
			KEY_LNAME + " varchar(25) not null, " +
			KEY_S + " varchar(25) not null, " +
			KEY_NR + " varchar(25) not null, " +
			KEY_CNP + " varchar(25) not null, " +
			KEY_NAT + " varchar(25) not null, " +
			KEY_VALID + " varchar(25)  not null, " +
			KEY_PICTURE + " varchar(100) )";
	
	private static final String TABLE_DROP = "drop table " + TABLE_NAME;
	
	// jdbc Connection
	private Connection conn = null;
	private Statement stmt = null;

	/**
	 * Create the data table.
	 * @return
	 */
	public boolean createTable() {
		System.out.println(TABLE_CREATE);
		boolean result = false;
		try {
			stmt = conn.createStatement();
			result = stmt.execute(TABLE_CREATE);
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Drop the data table.
	 * @return
	 */
	public boolean dropTable() {
		System.out.println(TABLE_DROP);
		boolean result = false;
		try {
			stmt = conn.createStatement();
			result = stmt.execute(TABLE_DROP);
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
		
	}

	public void createConnection() {
		try {
			conn = DriverManager.getConnection(dbURL);
		} catch (Exception except) {
			except.printStackTrace();
		}
	}

//	private static void selectRestaurants() {
//		try {
//			stmt = conn.createStatement();
//			ResultSet results = stmt.executeQuery("select * from " + tableName);
//			ResultSetMetaData rsmd = results.getMetaData();
//			int numberCols = rsmd.getColumnCount();
//			for (int i = 1; i <= numberCols; i++) {
//				// print Column Names
//				System.out.print(rsmd.getColumnLabel(i) + "\t\t");
//			}
//
//			System.out
//					.println("\n-------------------------------------------------");
//
//			while (results.next()) {
//				int id = results.getInt(1);
//				String restName = results.getString(2);
//				String cityName = results.getString(3);
//				System.out.println(id + "\t\t" + restName + "\t\t" + cityName);
//			}
//			results.close();
//			stmt.close();
//		} catch (SQLException sqlExcept) {
//			sqlExcept.printStackTrace();
//		}
//	}

	public void shutdown() {
		try {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				DriverManager.getConnection(dbURL + ";shutdown=true");
				conn.close();
			}
		} catch (SQLException sqlExcept) {

		}

	}

	public boolean insertData(ArrayList<String> keys, ArrayList<String> values) {
		try {
			stmt = conn.createStatement();
			
			String insertQuery = "insert into " + TABLE_NAME +" (";
			
			for (String s: keys){
				insertQuery += s + " , ";
			}
			
			insertQuery = insertQuery.substring(0, insertQuery.length()-2);
			insertQuery += ") values (";
			
			insertQuery += values.get(0);
					
			for (int i=1; i<values.size(); i++){
				insertQuery += " , \'" + values.get(i) + "\'";
			}
			
			insertQuery += ")";
			
//			System.out.println(insertQuery);
			
			boolean result = stmt.execute(insertQuery);
			stmt.close();
			
			return result;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
}
