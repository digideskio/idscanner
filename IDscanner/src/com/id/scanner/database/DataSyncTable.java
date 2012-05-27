package com.id.scanner.database;

import java.util.ArrayList;

import android.content.ContentValues;

import com.id.scanner.core.Profile;

public class DataSyncTable implements TableInterface {
	static final String TABLE_NAME = "IDdata";
	
	static final String INDEX = "ProfileName";
	private static final String KEY_SYN = "LastSyncIndex";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" (" + INDEX + " text primary key, " +
			KEY_SYN + " integer not null);";

	private static final String TABLE_DROP = "drop table if exists " + TABLE_NAME;
			
	
	
	@Override
	public String createTable() {
		return TABLE_CREATE;
	}

	@Override
	public String dropTable() {
		return TABLE_DROP;
	}

	@Override
	public ArrayList<ContentValues> insert(Profile p) {
		ArrayList<ContentValues> result = new ArrayList<ContentValues>();
		
		ContentValues values = new ContentValues();
		
		values.put(INDEX, p.getName());
		values.put(KEY_SYN, 0);
		
		return result;
	}

	@Override
	public String getTableName() {
		return TABLE_NAME;
	}

}
