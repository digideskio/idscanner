package com.id.scanner.ocr;

import com.googlecode.tesseract.android.TessBaseAPI;

import android.graphics.Bitmap;

public class Tesseract {
	private static final String whitelist = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789<";
	private Bitmap mBitmap;
	
	private String text;
	private int confidence;
	
	
	public Tesseract(Bitmap ocrZone) {
		this.mBitmap = ocrZone;
	}


	public void runOcr() {
		TessBaseAPI tesseract = new TessBaseAPI();
		tesseract.init("/mnt/sdcard/tesseract/", "eng");
		//tesseract.setPageSegMode(TessBaseAPI.PSM_SINGLE_LINE);
		tesseract.setImage(mBitmap);
		tesseract.setVariable("tessedit_char_whitelist", whitelist);
		
		text = tesseract.getUTF8Text();
		confidence = tesseract.meanConfidence();
		tesseract.end();
	}
	
	public String getText() {
		return text;
	}
	
	public int getConfidence() {
		return confidence;
	}
	
}
