package id.scanner.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.hardware.Camera;
import android.os.Environment;
import android.util.Log;

public class Utils {
	private static final String DIR_NAME = "IDscanner";
	private static final String TAG = Utils.class.getSimpleName();

	
	/** 
	 * A safe way to get an instance of the Camera object. 
	*/
	public static Camera getCameraInstance() {
		Camera c = null;
		try {
			c = Camera.open(); // attempt to get a Camera instance
			setDesiredCameraParameters(c);
		} catch (Exception e) {
			Log.d(TAG, "Error getting camera instance: " + e.getMessage());
		}
		return c;
	}

	private static void setDesiredCameraParameters(Camera camera) {
		Camera.Parameters parameters = camera.getParameters();

		if (parameters == null) {
			Log.w(TAG, "Device error: no camera parameters are available. Proceeding without configuration.");
			return;
		}
		//
		// Lets see if we can set focus mode continuous picture
		//
		String focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE;
		List<String> focusModes = parameters.getSupportedFocusModes();
		
		if (focusMode != null && focusModes.contains(focusMode)) {
			parameters.setFocusMode(focusMode);
		}
		
		camera.setParameters(parameters);
	}
	
	/**
	 * Create a File for saving an image or video
	 * @return	A file with timestamp and jpg extension.
	 */
	public static File getOutputMediaFile(){
	    File mediaStorageDir = new File(
	    		Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_NAME);
	    //
	    // Create the storage directory if it does not exist
	    //
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d(TAG, "failed to create directory");
	            return null;
	        }
	    }
	    //
	    // Create a media file name
	    //
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() 
	    		+ File.separator +"IMG_"+ timeStamp + ".jpg");

	    return mediaFile;
	}
}
