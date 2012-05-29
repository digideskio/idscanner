package com.id.scanner.database;


import java.util.ArrayList;

import com.id.scanner.core.Profile;
import com.id.scanner.xml.DocumentItem;

import android.content.ContentValues;

public class ItemTable implements TableInterface{
	private static final String TABLE_NAME = "Item";

	private static final String INDEX = "idItem";
	private static final String KEY_NAME = "name";
	private static final String KEY_TYPE = "type";
	private static final String KEY_X = "x";
	private static final String KEY_Y = "y";
	private static final String KEY_W = "w";
	private static final String KEY_H = "h";
	private static final String KEY_PROFILE = "profile";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " integer primary key autoincrement, " +
			KEY_NAME + " text not null, " +
			KEY_TYPE + " text not null, " +
			KEY_X + " integer not null, " +
			KEY_Y + " integer not null, " +
			KEY_W + " integer not null, " +
			KEY_H + " integer not null, " +
			KEY_PROFILE + " text not null, " +
			"foreign key( " + KEY_PROFILE+ " ) references " + ProfileTable.TABLE_NAME + "( " + ProfileTable.INDEX+ " ));";
	
	private static final String TABLE_DROP = "drop table if exists " + TABLE_NAME;
	

	public String createTable() {
		return TABLE_CREATE;
	}
	
	public String dropTable() {
		return TABLE_DROP;
	}
	
	/**
	 * Inserts in the table all the document items.
	 * @param documentItems
	 * @param string 
	 */
	public ArrayList<ContentValues> insert(Profile p) {
		ArrayList<ContentValues> result = new ArrayList<ContentValues>();
		ContentValues values;

		ArrayList<DocumentItem> documentItems = p.getDocumentItems();
		
		for (DocumentItem item: documentItems) {
			values = new ContentValues();
			
			values.put(KEY_X, item.getX());
			values.put(KEY_Y, item.getY());
			values.put(KEY_W, item.getW());
			values.put(KEY_H, item.getH());
			values.put(KEY_NAME, item.getName());
			values.put(KEY_TYPE, item.getType());
			values.put(KEY_PROFILE, p.getName());
			
			result.add(values);
		}
		return result;
	}

	public String getTableName() {
		return TABLE_NAME;
	}
}







