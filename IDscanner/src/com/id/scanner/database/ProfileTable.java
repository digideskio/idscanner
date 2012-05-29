package com.id.scanner.database;

import java.util.ArrayList;

import com.id.scanner.core.Profile;

import android.content.ContentValues;

public class ProfileTable implements TableInterface{
	static final String TABLE_NAME = "Profile";

	static final String INDEX = "name";
	private static final String KEY_PICT_X = "pictureSizeX";
	private static final String KEY_PICT_Y = "pictureSizeY";
	private static final String KEY_DOC_X = "documentSizeX";
	private static final String KEY_DOC_Y = "documentSizeY";
	static final String KEY_SYNC_INDEX = "SyncIndex";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " text primary key, " +
			KEY_PICT_X + " integer not null, " +
			KEY_PICT_Y + " integer not null, " +
			KEY_DOC_X + " integer not null, " +
			KEY_DOC_Y + " integer not null, " +
			KEY_SYNC_INDEX + " integer not null ); ";
	
	private static final String TABLE_DROP = "drop table if exists " + TABLE_NAME;
	
	
	public String createTable() {
		return TABLE_CREATE;
	}
	
	public String dropTable() {
		return TABLE_DROP;
	}
	/**
	 * Inserts a row in the table.
	 * @param p
	 */
	public ArrayList<ContentValues> insert(Profile p) {
		ArrayList<ContentValues> result = new ArrayList<ContentValues>();
		ContentValues values = new ContentValues();
		
		values.put(INDEX, p.getName());
		values.put(KEY_PICT_X, p.getPictureSizeX());
		values.put(KEY_PICT_Y, p.getPictureSizeY());
		values.put(KEY_DOC_X, p.getDocumentSizeX());
		values.put(KEY_DOC_Y, p.getDocumentSizeY());
		values.put(KEY_SYNC_INDEX, 0);

		result.add(values);
		return result;
	}
	
	public String getTableName() {
		return TABLE_NAME;
	}
	
	public static String getTableIndex() {
		return INDEX;
	}
}
