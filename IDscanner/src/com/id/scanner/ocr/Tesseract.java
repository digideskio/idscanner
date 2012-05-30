package com.id.scanner.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;
import android.util.Log;

public class Tesseract {
	private static final String TAG = Tesseract.class.getSimpleName();
	
	private static final String whitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<";
	
	private Bitmap mBitmap;
	private String text = null;
	private int confidence = 0;
	
	
	public Tesseract(Bitmap ocrZone) {
		this.mBitmap = ocrZone;
	}


	public void runOcr() {
		try {
		TessBaseAPI tesseract = new TessBaseAPI();
		tesseract.init("/mnt/sdcard/tesseract/", "ron");
		//tesseract.setPageSegMode(TessBaseAPI.PSM_SINGLE_LINE);
		tesseract.setImage(mBitmap);
		tesseract.setVariable("tessedit_char_whitelist", whitelist);
		
		text = tesseract.getUTF8Text();
		confidence = tesseract.meanConfidence();
		tesseract.end();
		} catch (Exception e) {
			Log.d(TAG, "Tesseract failed: " +e.getMessage() );
		}
	}
	
	public String getText() {
		return text;
	}
	
	public int getConfidence() {
		return confidence;
	}
}
