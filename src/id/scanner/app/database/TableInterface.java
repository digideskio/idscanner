package id.scanner.app.database;

import id.scanner.app.core.Profile;

import java.util.ArrayList;

import android.content.ContentValues;

public interface TableInterface {
	public String createTable();
	
	public String dropTable();
	
	public ArrayList<ContentValues> insert(Profile p);
	
	public String getTableName();
}
