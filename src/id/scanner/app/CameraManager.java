package id.scanner.app;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class CameraManager {
	private static final String TAG = CameraManager.class.getSimpleName();
	private Camera mCamera;
	private boolean previewing = false;
	private Context context;
	
	public static Point screenResolution = null;
	public static Point cameraResolution = null;
	
	
	public CameraManager(Context context) {
		this.context = context;
	}
	
	
	public void openCamera(SurfaceHolder holder) throws IOException {
		if (mCamera == null) {
			mCamera = Camera.open();
			
			this.setCameraParameters();
			
			if (mCamera == null) {
				throw new IOException();
			}
			
			mCamera.setPreviewDisplay(holder);
		}
	}
	
	public void closeCamera() {
		if (mCamera != null ) {
			mCamera.release();
		}
	}
	
	public void startPreview() throws IOException {
		if (mCamera != null && !previewing ) {
			mCamera.startPreview();
			previewing = true;
		} else {
			Log.d(TAG, "Requested to start preview but camera is null (or already previewing).");
		}
	}
	
	public void stopPreview() {
		if (mCamera != null && previewing) {
			mCamera.stopPreview();
			previewing = false;
		}
	}
	
	private void setCameraParameters() {
		Camera.Parameters parameters = mCamera.getParameters();

//		List<Camera.Size> sizes = parameters.getSupportedPictureSizes();
//		Log.d(TAG, "Supported camera sizes:");
//		for (Camera.Size s: sizes) {
//			Log.d(TAG, s.width + "x" + s.height);
//		}
//		Log.d(TAG,"Current Picture size:" + parameters.getPictureSize().height);
		
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
		//parameters.setPictureSize(1280, 960);
		
		
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	    Display display = manager.getDefaultDisplay();
	    
	    screenResolution = new Point();
	    display.getSize(screenResolution);
	    
		cameraResolution = findBestPreviewSizeValue(parameters, false);
		
		Log.i(TAG, "Screen resolution: " + screenResolution);
	    Log.i(TAG, "Camera resolution: " + cameraResolution);
	    
		parameters.setPreviewSize(cameraResolution.x, cameraResolution.y);
		
		mCamera.setParameters(parameters);
	}

	/**
	 * Find the supported camera size that is optimal for the screen resolution.
	 * @param parameters
	 * @param screenResolution
	 * @param b
	 * @return
	 */
	private Point findBestPreviewSizeValue(Parameters parameters, boolean b) {
		Point bestSize = null;
		int diff = Integer.MAX_VALUE;
		
		for (Camera.Size supportedPreviewSize : parameters.getSupportedPreviewSizes()) {
			int supportedWidth = supportedPreviewSize.width;
			int supportedHeight = supportedPreviewSize.height;
			
			int newDiff = Math.abs(screenResolution.x * supportedHeight - supportedWidth * screenResolution.y);
			
			if (newDiff == 0) {
				bestSize = new Point(supportedWidth, supportedHeight);
				break;
			}
			if (newDiff < diff) {
				bestSize = new Point(supportedWidth, supportedHeight);
				diff = newDiff;
			}
		}
		if (bestSize == null) {
			Camera.Size defaultSize = parameters.getPreviewSize();
			bestSize = new Point(defaultSize.width, defaultSize.height);
		}
		return bestSize;
	}
}
