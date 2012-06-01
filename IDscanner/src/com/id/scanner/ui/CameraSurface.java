package com.id.scanner.ui;

import java.io.IOException;

import com.id.scanner.camera.CameraManager;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = CameraSurface.class.getSimpleName();
	private CameraManager mCameraManager;
	private SurfaceHolder mHolder;
	
	
	public CameraSurface(Context context) {
		super(context);
		initialize(context);
	}
	
	public CameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		initialize(context);
	}

	public CameraSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initialize(context);
	}

	
	private void initialize(Context context){
		mHolder = getHolder();
		mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        
        mCameraManager = new CameraManager(context);
        
		Log.d(TAG, "Camera Surface initialized");
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated()");
		try {
			mCameraManager.openCamera(holder);
			mCameraManager.startPreview();
		} catch (IOException e) {
			Log.d(TAG, "Unable to init camera");
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		Log.d(TAG, "surfaceChanged()");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "surfaceDestroyed()");
		mCameraManager.stopPreview();
		mCameraManager.closeCamera();
	}
}
