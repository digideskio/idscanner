package id.scanner.app.database;

import id.scanner.app.xml.Profile;
import id.scanner.app.xml.Rectangle;

import java.util.ArrayList;

import android.content.ContentValues;

public class RectangleTable implements TableInterface {
	private static final String TABLE_NAME = "Rectangle";

	private static final String INDEX = "_idRect";
	private static final String KEY_A = "a";
	private static final String KEY_B = "b";
	private static final String KEY_C = "c";
	private static final String KEY_D = "d";
	private static final String KEY_PROFILE = "profile";
	
	private static final String TABLE_CREATE = "create table " + TABLE_NAME +
			" ( "+ INDEX + " integer primary key autoincrement, " +
			KEY_A + " integer not null, " +
			KEY_B + " integer not null, " +
			KEY_C + " integer, " +
			KEY_D + " integer, " +
			KEY_PROFILE + " text not null, " +
			"foreign key( " + KEY_PROFILE+ " ) references " + ProfileTable.TABLE_NAME + "( " + ProfileTable.INDEX + " ));";
	
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
		ContentValues values;

		ArrayList<Rectangle> displayObjects = p.getDisplayObjects();
		
		for (Rectangle rect: displayObjects) {
			values = new ContentValues();
			
			values.put(KEY_A, rect.getA());
			values.put(KEY_B, rect.getB());
			values.put(KEY_C, rect.getC());
			values.put(KEY_D, rect.getD());
			values.put(KEY_PROFILE, p.getName());

			result.add(values);
		}
		return result;
	}
	
	@Override
	public String getTableName() {
		return TABLE_NAME;
	}
}




