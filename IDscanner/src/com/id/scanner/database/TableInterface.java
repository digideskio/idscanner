package com.id.scanner.database;


import java.util.ArrayList;

import com.id.scanner.core.Profile;

import android.content.ContentValues;

public interface TableInterface {
	public String createTable();
	
	public String dropTable();
	
	public ArrayList<ContentValues> insert(Profile p);
	
	public String getTableName();
}
