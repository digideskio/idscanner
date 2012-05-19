package id.scanner.app;

import id.scanner.app.ocr.Tesseract;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.util.Log;

public class PictureManager implements PictureCallback{
	private static final String TAG = PictureManager.class.getSimpleName();

	private MainActivity activity;

	
	public PictureManager(MainActivity mainActivity) {
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
		String text = null;
		int confidence = 0;
		
		if (ocrZone != null) {
			Tesseract tess = new Tesseract(ocrZone);
			tess.runOcr();
			
			text = tess.getText();
			confidence = tess.getConfidence();
			
			Log.d(TAG, "Teseract returned: " + text);
		} else {
			confidence = -1; 	// hack for not finding any documents.
		}
		
		activity.showResults(text, confidence);
	}
}
