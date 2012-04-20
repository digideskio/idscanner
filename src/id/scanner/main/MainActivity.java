package id.scanner.main;

import id.scanner.main.image.ImageActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private Camera mCamera;
	private CameraPreview mPreview;
	private File pictureFile;

	private String whitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	
	
	private PictureCallback mPicture = new PictureCallback() {
		public void onPictureTaken(byte[] data, Camera camera) {
			Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
			
			int width = bit.getWidth();
			int height = bit.getHeight();
			
			Log.d(TAG, "Image width: " + width + " ,height: "+ height);
			
			Bitmap textImage = Bitmap.createBitmap(bit, 0, 0 , 155, 39);
			
			pictureFile = Utils.getOutputMediaFile();
			if (pictureFile == null) {
				Log.d(TAG,"Error creating media file, check storage permission");
				return;
			}

			try {
				FileOutputStream fos = new FileOutputStream(pictureFile);
				//fos.write(data);
				textImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				//bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
				
				fos.close();
				
				// 
				// Call mighty teseract!
				//
				TessBaseAPI tesseract = new TessBaseAPI();
				tesseract.init("/mnt/sdcard/tesseract/", "eng");
				//tesseract.setPageSegMode(TessBaseAPI.PSM_SINGLE_LINE);
				tesseract.setImage(textImage);
				tesseract.setVariable("tessedit_char_whitelist", whitelist);
				
				String text = tesseract.getUTF8Text();
				tesseract.end();

				// Show what was interpreted.
				if (text != null && text.length()>0) {
					Toast.makeText(MainActivity.this, text, Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "Y U No see text?", Toast.LENGTH_SHORT).show();
				}
				Log.d(TAG, "Teseract returned:" + text);
				
				mCamera.startPreview();
			} catch (FileNotFoundException e) {
				Log.d(TAG, "File not found: " + e.getMessage());
			} catch (IOException e) {
				Log.d(TAG, "Error accessing file: " + e.getMessage());
			}
		}
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.main);
		
		// Create an instance of Camera
		mCamera = Utils.getCameraInstance();
	}

	@Override
	protected void onResume() {
		super.onResume();

		try {
			mCamera.reconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Create our Preview view and set it as the content of our activity.
		mPreview = new CameraPreview(this, mCamera);
		FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);

		// Add the overlay
		TextFinderView t = new TextFinderView(this, null);
		preview.addView(t);

		// Add a listener to the Capture button
		Button captureButton = (Button) findViewById(R.id.button_capture);
		captureButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// get an image from the camera
				mCamera.takePicture(null, null, mPicture);
			}
		});
	}
	
	
	@Override
	protected void onPause() {
		super.onPause();
		mCamera.unlock();
	}
	
	// release the camera immediately on pause event
	@Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "On destroy called.");
        releaseCamera();              
    }

	// release the camera for other applications
    private void releaseCamera(){
        if (mCamera != null){
            mCamera.release();        
            mCamera = null;
        }
    }
	
    
    ///////////////////////   The menu   //////////////////////////////////////
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    super.onCreateOptionsMenu(menu);
	    
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    
	    return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.item_info:
			Toast.makeText(this, getResources().getString(R.string.menu_info_text), Toast.LENGTH_SHORT).show();
			return true;
		case R.id.item_gallery:
			Intent intent = new Intent(this, ImageActivity.class);
			intent.putExtra("IMAGE_FILE", this.pictureFile.getAbsolutePath());
			startActivity(intent);
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}