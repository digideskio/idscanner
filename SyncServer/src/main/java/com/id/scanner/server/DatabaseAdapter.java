package com.id.scanner.server;

import java.util.ArrayList;

public class DatabaseAdapter {
	private DerbyDB db ;
	
	
	public DatabaseAdapter() {
		db = new DerbyDB();
	}

	public void open() {
		db.createConnection();
	}

	public void close() {
		db.shutdown();
	}
	
	public boolean insert(ArrayList<String> keys ,ArrayList<String> values) {
		return db.insertData(keys, values);
	}
	
	
	
	
	
	public static void main(String[] args) {
		String columns = "idData;Nume;Prenume;Seria;Numarul;CNP;Cetatenia;Valabilitate;Poza";
		String value = "2;ABRUDAN;ROXANA VALENTINA ;HD;191582;2891007205568;2132;131007;/mnt/sdcard/Pictures/IDscanner/IMG_20120530_010918.jpg";
		DerbyDB db = new DerbyDB();
		
		db.createConnection();
		
		if (db.createTable()) {
			System.out.println("Table created!");
		}

		String[] str = columns.split(";");
		String[] str2 = value.split(";");
		
		ArrayList<String> keys = new ArrayList<String>();
		ArrayList<String> values = new ArrayList<String>();
		
		
		for (int  i=1; i<str.length; i++) {
			keys.add(str[i]);
			values.add(str2[i]);
		}
		
		
		db.insertData(keys, values);
		
//		if (db.dropTable()) 
//			System.out.println("Table droped.");
		
		db.shutdown();
	}

	public void createTable() {
		db.createTable();
	}

	public void deleteTable() {
		db.dropTable();
	}
}
