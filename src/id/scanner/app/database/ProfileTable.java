package id.scanner.app.database;

import java.util.ArrayList;

import id.scanner.app.xml.Profile;
import android.content.ContentValues;

public class ProfileTable implements TableInterface{
	static final String TABLE_NAME = "Profile";

	static final String INDEX = "name";
	private static final String KEY_PREV_X = "previewSizeX";
	private static final String KEY_PREV_Y = "previewSizeY";
	private static final String KEY_PICT_X = "pictureSizeX";
	private static final String KEY_PICT_Y = "pictureSizeY";
	private static final String KEY_DOC_X = "documentSizeX";
	private static final String KEY_DOC_Y = "documentSizeY";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " text primary key, " +
			KEY_PREV_X + " integer not null, " +
			KEY_PREV_Y + " integer not null, " +
			KEY_PICT_X + " integer not null, " +
			KEY_PICT_Y + " integer not null, " +
			KEY_DOC_X + " integer not null, " +
			KEY_DOC_Y + " integer not null); ";
	
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
		values.put(KEY_PREV_X, p.getPreviewSizeX());
		values.put(KEY_PREV_Y, p.getPreviewSizeY());
		values.put(KEY_PICT_X, p.getPictureSizeX());
		values.put(KEY_PICT_Y, p.getPictureSizeY());
		values.put(KEY_DOC_X, p.getDocumentSizeX());
		values.put(KEY_DOC_Y, p.getDocumentSizeY());

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
