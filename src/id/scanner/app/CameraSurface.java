package id.scanner.app;

import java.io.IOException;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback{
	private static final String TAG = CameraSurface.class.getSimpleName();
	private CameraManager mCameraManager = new CameraManager();
	private SurfaceHolder mHolder;
	
	{
		mHolder = getHolder();
		mHolder.addCallback(this);
        // deprecated setting, but required on Android versions prior to 3.0
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}
	
	
	public CameraSurface(Context context) {
		super(context);
		Log.d(TAG, "Constructor 1");
	}
	
	public CameraSurface(Context context, AttributeSet attrs) {
		super(context, attrs);
		Log.d(TAG, "Constructor 2");
	}

	public CameraSurface(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		Log.d(TAG, "Constructor 3");
	}

	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d(TAG, "surfaceCreated()");
		try {
			mCameraManager.openCamera(holder);
			mCameraManager.startPreview();
		} catch (IOException e) {
			//e.printStackTrace();
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
