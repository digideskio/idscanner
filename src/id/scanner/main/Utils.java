package id.scanner.main;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;

public class Utils {
	private static final String DIR_NAME = "IDscanner";
	private static final String TAG = Utils.class.getSimpleName();

	
	/** A safe way to get an instance of the Camera object. */
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

		String focusMode = findSettableValue(parameters.getSupportedFocusModes(),
				Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);   // evrika!!
		if (focusMode != null) {
			parameters.setFocusMode(focusMode);
		}

		camera.setParameters(parameters);
	}
	
	private static String findSettableValue(Collection<String> supportedValues,
			String... desiredValues) {
		Log.i(TAG, "Supported values: " + supportedValues);
		String result = null;
		if (supportedValues != null) {
			for (String desiredValue : desiredValues) {
				if (supportedValues.contains(desiredValue)) {
					result = desiredValue;
					break;
				}
			}
		}
		Log.i(TAG, "Settable value: " + result);
		return result;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/** Create a File for saving an image or video */
	public static File getOutputMediaFile(){
	    File mediaStorageDir = new File(
	    		Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_NAME);

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d(TAG, "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    File mediaFile = new File(mediaStorageDir.getPath() 
	    		+ File.separator +"IMG_"+ timeStamp + ".jpg");

	    return mediaFile;
	}
}
