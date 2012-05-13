package id.scanner.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

public class PictureManager implements PictureCallback{
	private static final String TAG = PictureManager.class.getSimpleName();
	private static final String whitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
	private static final String DIR_NAME = "IDscanner";

	private Context mContext;
	
	
	public PictureManager(Context application) {
		mContext = application;
	}
	
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
		
		int width = bit.getWidth();
		int height = bit.getHeight();
		
		Log.d(TAG, "Image width: " + width + " ,height: "+ height);
		
		//Bitmap textImage = Bitmap.createBitmap(bit, 0, 0 , 600, 150);
		
		File pictureFile = this.getOutputMediaFile();
		if (pictureFile == null) {
			Log.d(TAG,"Error creating media file, check storage permission");
			return;
		}

		try {
			FileOutputStream fos = new FileOutputStream(pictureFile);
			fos.write(data);
			//textImage.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			//bit.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
			
//			TessBaseAPI tesseract = new TessBaseAPI();
//			tesseract.init("/mnt/sdcard/tesseract/", "eng");
//			//tesseract.setPageSegMode(TessBaseAPI.PSM_SINGLE_LINE);
//			tesseract.setImage(textImage);
//			tesseract.setVariable("tessedit_char_whitelist", whitelist);
//			
//			String text = tesseract.getUTF8Text();
//			int confidence = tesseract.meanConfidence();
//			tesseract.end();
//
//			// Show what was interpreted.
//			if (text != null && text.length()>0) {
//				Toast.makeText(mContext, text + "\n\nConfidence: " + confidence, Toast.LENGTH_LONG).show();
//			} else {
//				Toast.makeText(mContext, "Y U No see text?", Toast.LENGTH_SHORT).show();
//			}
//			Log.d(TAG, "Teseract returned:" + text);
		} catch (FileNotFoundException e) {
			Log.d(TAG, "File not found: " + e.getMessage());
		} catch (IOException e) {
			Log.d(TAG, "Error accessing file: " + e.getMessage());
		}

	}

	/**
	 * Create a File for saving an image or video
	 * @return	A file with timestamp and jpg extension.
	 */
	public File getOutputMediaFile(){
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
