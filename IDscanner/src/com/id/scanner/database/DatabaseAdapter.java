package com.id.scanner.database;

import java.util.ArrayList;

import com.id.scanner.core.IDdata;
import com.id.scanner.core.Profile;
import com.id.scanner.core.ProfileManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper{
	private static final String TAG = DatabaseAdapter.class.getSimpleName();
	
	private static final String DATABASE_NAME = "IDscanner";
	private static final int DATABASE_VERSION = 2;
	
	ArrayList<TableInterface> profileTables = new ArrayList<TableInterface>();
	private DataTable dataTable;

	private SQLiteDatabase database;

	
	public DatabaseAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	/**
	 * Opens (and create/update) the IDscanner database.
	 */
	public void open() {
		profileTables.add( new ProfileTable());
		profileTables.add( new ItemTable());
		profileTables.add( new RectangleTable());
		
		dataTable = new DataTable();

		database = getWritableDatabase();
	}
	
	/**
	 * Insert the content of profile to the database.
	 * Iterate trough the list of tables that extend "TableInterface" and call
	 * the insert method on the profile.
	 * @param p	The profile to insert.
	 */
	public boolean insertProfile(ArrayList<Profile> profiles){
		ArrayList<ContentValues> values;
		
		for (Profile p: profiles) {
			try {
				for (TableInterface table: profileTables) {
					values = table.insert(p);
					
					for (ContentValues v: values) {
						database.insert(table.getTableName(), null, v);
					}
				}
			
			} catch (Exception e) {
				Log.d(TAG,"Exceeption raised when inseting profile " + p.getName() +" in database: " +e);
				return false;
			}
			Log.d(TAG, "Successfully inserted profile " + p.getName() + " in database.");
		}
		return true;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating database. ");
		
		for (TableInterface table: profileTables) {
			Log.d(TAG, table.createTable());
			db.execSQL(table.createTable());
		}
		
		Log.d(TAG, dataTable.createTable());
		db.execSQL(dataTable.createTable());
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading (dropping) database. ");
		
		for (TableInterface table: profileTables) {
			Log.d(TAG, table.dropTable());
			db.execSQL(table.dropTable());
		}
		
		Log.d(TAG, dataTable.dropTable());
		db.execSQL(dataTable.dropTable());
		
		onCreate(db);
	}

	/**
	 * @return	A list with the profile names from the database.
	 */
	public ArrayList<String> getProfileList() {
		ArrayList<String> result = new ArrayList<String>();
		
		Cursor cursor = database.query(ProfileTable.TABLE_NAME,	// table name 
				new String[] {ProfileTable.INDEX} ,					// table columns
	            null,												// select
	            null, 												// select args
	            null, 												// group by
	            null, 												// having
	            null);												// order by
        if (cursor != null) {
            cursor.moveToFirst();
            
            String name;
            while ( ! cursor.isAfterLast()) {
            	name = cursor.getString(0);
            	result.add(name);
            	cursor.moveToNext();
            }
        }
        
        return result;
	}

	/**
	 * Insert the data that was read from the image into the database
	 * @param data
	 */
	public void insertData(IDdata data) {
		ContentValues values = dataTable.insert(data);
		database.insert(dataTable.getTableName(), null, values);
		
		Log.d(TAG, "Inserted data: " + data);
	}
	
	/**
	 * @return the name of the data table of the current profile.
	 */
	public String getProfileTableName() {
		return DataTable.TABLE_NAME;
	}
	
	/**
	 * 
	 * @param lastIndex
	 */
	public Cursor getDataTable(int lastIndex) {
		Cursor cursor = database.query(DataTable.TABLE_NAME, 
				null,			// null = all columns 
				DataTable.INDEX + ">" + lastIndex, 
				null, 
				null, 
				null, 
				null);
		
		return cursor;
	}


	public int getSyncIndex(String profileName) {
		int result = 0;
		
		Cursor cursor = database.query(ProfileTable.TABLE_NAME, 
				new String[] {ProfileTable.KEY_SYNC_INDEX},			 
				ProfileTable.INDEX + "=\"" + profileName + "\"", 
				null, 
				null, 
				null, 
				null);
		if (cursor.moveToFirst()) {
			try {
				result = cursor.getInt(0);
			} catch (Exception e) {
				Log.e(TAG, "Exception trying to get sync index for profile " + profileName + " from database");
			}
		}
		
		return result;
	}


	public void updateSynchronizationIndex(Integer syncValue) {
		String profileName = ProfileManager.getInstance().getProfileName();
		
		ContentValues value = new ContentValues();
		value.put(ProfileTable.KEY_SYNC_INDEX, syncValue);
		
		int rows = database.update(ProfileTable.TABLE_NAME,
				value,
				ProfileTable.INDEX + "=\"" + profileName + "\"",
				null);
		
		if (rows != 1) {
			Log.w(TAG, "Updating the sync value in the profile table affected more than 1 row!!!");
		}
	}
}











