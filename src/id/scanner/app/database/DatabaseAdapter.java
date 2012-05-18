package id.scanner.app.database;

import java.util.ArrayList;

import id.scanner.app.xml.Profile;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseAdapter extends SQLiteOpenHelper{
	private static final String TAG = DatabaseAdapter.class.getSimpleName();
	
	private static final String DATABASE_NAME = "IDscanner";
	private static final int DATABASE_VERSION = 1;
	
	ArrayList<TableInterface> tables = new ArrayList<TableInterface>();
	private SQLiteDatabase database;

	
	public DatabaseAdapter(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	
	/**
	 * Opens (and create/update) the IDscanner database.
	 */
	public void open() {
		tables.add( new ProfileTable());
		tables.add( new ItemTable());
		tables.add( new RectangleTable());
		
		database = getWritableDatabase();
	}
	
	/**
	 * Insert the content of profile to the database.
	 * @param p
	 */
	public boolean insertProfile(Profile p){
		try {
			ArrayList<ContentValues> values;
			
			for (TableInterface table: tables) {
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
		return true;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.d(TAG, "Creating database. ");
		
		for (TableInterface table: tables) {
			Log.d(TAG, table.createTable());
			db.execSQL(table.createTable());
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.d(TAG, "Upgrading (dropping) database. ");
		
		for (TableInterface table: tables) {
			Log.d(TAG, table.dropTable());
			db.execSQL(table.dropTable());
		}
		onCreate(db);
	}


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
	
}











