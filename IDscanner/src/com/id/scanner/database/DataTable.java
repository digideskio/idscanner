package com.id.scanner.database;

import com.id.scanner.core.IDdata;

import android.content.ContentValues;

public class DataTable {
	private static final String TABLE_NAME = "IDdata";

	private static final String INDEX = "_idData";
	private static final String KEY_FNAME = "Nume";
	private static final String KEY_LNAME = "Prenume";
	private static final String KEY_S = "Seria";
	private static final String KEY_NR = "Numarul";
	private static final String KEY_CNP = "CNP";
	private static final String KEY_NAT = "Cetatenia";
	private static final String KEY_VALID = "Valabilitate";
	private static final String KEY_PICTURE = "Poza";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " integer primary key autoincrement, " +
			KEY_FNAME + " text not null, " +
			KEY_LNAME + " text not null, " +
			KEY_S + " text not null, " +
			KEY_NR + " integer not null, " +
			KEY_CNP + " integer not null, " +
			KEY_NAT + " text not null, " +
			KEY_VALID + " integer not null, " +
			KEY_PICTURE + " text );";
	
	private static final String TABLE_DROP = "drop table if exists " + TABLE_NAME;
	

	public String createTable() {
		return TABLE_CREATE;
	}
	
	public String dropTable() {
		return TABLE_DROP;
	}
	
	/**
	 * Inserts in the table all the document items.
	 * @param data 
	 * @param documentItems
	 * @param string 
	 */
	public ContentValues insert(IDdata data) {
		ContentValues values = new ContentValues();
		
		values.put(KEY_FNAME, data.getNume());
		values.put(KEY_LNAME, data.getPrenume());
		values.put(KEY_S, data.getSeria());
		values.put(KEY_NR, data.getNumarul());
		values.put(KEY_CNP, data.getCNP());
		values.put(KEY_NAT, data.getCetatenia());
		values.put(KEY_VALID, data.getValabilitate());
		values.put(KEY_PICTURE, data.getPictureLocation());
		
		return values;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

}







