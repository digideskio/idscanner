package id.scanner.app.core;

import android.graphics.Point;
import id.scanner.app.MainActivity;
import id.scanner.app.xml.Profile;
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

	public Point getPreviewSize() {
		int x = profile.getPreviewSizeX();
		int y = profile.getPreviewSizeY();
		return new Point(x,y);
	}

	public Point getPictureSize() {
		int x = profile.getPictureSizeX();
		int y = profile.getPictureSizeY();
		return new Point(x,y);
	}
}
