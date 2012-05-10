package id.scanner.app;

import java.io.IOException;
import java.util.List;

import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;

public class CameraManager {
	private static final String TAG = CameraManager.class.getSimpleName();
	private Camera mCamera;
	private boolean previewing = false;
	
	public CameraManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	public void openCamera(SurfaceHolder holder) throws IOException {
		if (mCamera == null) {
			mCamera = Camera.open();
			
			this.setDesiredCameraParameters();
			
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
	
	private void setDesiredCameraParameters() {
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
		parameters.setPictureSize(1280, 960);
		mCamera.setParameters(parameters);
	}
}
