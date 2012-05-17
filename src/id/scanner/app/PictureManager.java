package id.scanner.app;

import id.scanner.app.ocr.Tesseract;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;
import android.widget.Toast;

public class PictureManager implements PictureCallback{
	private static final String TAG = PictureManager.class.getSimpleName();

	private Context mContext;
	private MainActivity activity;
	
	
	public PictureManager(Context application) {
		mContext = application;
	}
	
	public PictureManager(Context application, MainActivity mainActivity) {
		mContext = application;
		this.activity = mainActivity;
	}
	
	
	@Override
	public void onPictureTaken(byte[] data, Camera camera) {
		Bitmap bit = BitmapFactory.decodeByteArray(data, 0, data.length);
		
		int width = bit.getWidth();
		int height = bit.getHeight();
		
		Log.d(TAG, "Image width: " + width + " ,height: "+ height);
		
		Bitmap image = Bitmap.createBitmap(bit, 0, 0 , width, height);
		
		File pictureFile = Util.getUniqueImageFile();
		if (pictureFile == null) {
			Log.d(TAG,"Error creating media file, check storage permission");
			return;
		}

		ImageProcessor processor = new ImageProcessor(image);
		Bitmap ocrZone = processor.getOcrZone();
			
		if (ocrZone != null) {
			Tesseract tess = new Tesseract(ocrZone);
			tess.runOcr();
			
			String text = tess.getText();
			int confidence = tess.getConfidence();
			
			
			
			// Show what was interpreted.
			if (text != null && text.length()>0) {
				activity.showResults(text, confidence);
			} else {
				Toast.makeText(mContext, "No text detected!", Toast.LENGTH_SHORT).show();
			}
			Log.d(TAG, "Teseract returned:" + text);
		} else {
			Toast.makeText(mContext, "No document was identified in the picture", Toast.LENGTH_SHORT).show();
		}
	}
}
