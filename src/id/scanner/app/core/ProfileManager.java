package id.scanner.app.core;

import java.util.ArrayList;

import android.graphics.Point;
import id.scanner.app.MainActivity;
import id.scanner.app.xml.DocumentItem;
import id.scanner.app.xml.Rectangle;
import id.scanner.app.xml.XMLparser;

public class ProfileManager {
	private static Profile profile = null;
	
	private static ProfileManager instance = null;
	
	private ProfileManager() {	
    	XMLparser parser = new XMLparser(MainActivity.getApplicationResources());
    	profile = parser.parseXmlResource();
	}
	
	public static ProfileManager getInstance() {
		if (instance == null ) {
			instance = new ProfileManager();
		}
		return instance;
	}

	public Profile getCurrentProfile() {
		return profile;
	}

	public Point getPictureSize() {
		int x = profile.getPictureSizeX();
		int y = profile.getPictureSizeY();
		return new Point(x,y);
	}

		// TODO Auto-generated method stub
	public int[][] getDispalys() {
		ArrayList<Rectangle> rectangles = profile.getDisplayObjects();
		int size = rectangles.size();
		int[][] result = new int[size ][4];
		
		for (int i=0; i<size; i++) {
			result[i]=rectangles.get(i).getArray();
		}
		return result;
	}
	
	/**
	 * 
	 * @param name	The name of the document item.
	 * @return		x, y, w, h
	 */
	public int[] getDocumentItemCoordinates(String name) {
		int [] result = null;
		ArrayList<DocumentItem> items = profile.getDocumentItems();
		
		for (DocumentItem i: items) {
			if (i.getName().equalsIgnoreCase(name)) {
				result = new int[4];
				
				result[0] = i.getX();
				result[1] = i.getY();
				result[2] = i.getW();
				result[3] = i.getH();
				break;
			}
			
		}
		return result;
	}
	
	public int[] getDocumentSize() {
		int[] result = new int[2];
		
		result[0] = profile.getDocumentSizeX();
		result[1] = profile.getDocumentSizeY();
		
		return result;
	}
}







