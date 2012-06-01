package com.id.scanner.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

public class FileUtils {
	private static final String TAG = FileUtils.class.getSimpleName();
	private static final String DIR_NAME = "IDscanner";
	private static final String PROBLEMS_DIR_DOC = "NoDocument/";
	private static final String PROBLEMS_DIR_TESS = "NoText/";

	/**
	 * Write a bitmap to disk.
	 * @param image	the bitmap to write.
	 * @param name	the name of the file.
	 */
	public static void writeToDisk(Bitmap image, String name) {
		String storageDir = getMediaStorageDir();
		File pictureFile = new File(storageDir + name + ".jpg");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(pictureFile);
			
			image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Write a bitmap to disk with a unique filename.
	 * @param image	the bitmap to write.
	 * @param name	the name of the file.
	 */
	public static String writeImageWithTimestamp(Bitmap image) {
		File pictureFile = getUniqueImageFile("");
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(pictureFile);
			
			image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
			
			return pictureFile.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	/**
	 * Used for writing images that can't identify a document.
	 * @param image
	 * @return
	 */
	public static void writeNoDocumentImage(Bitmap image) {
		File pictureFile = getUniqueImageFile(PROBLEMS_DIR_DOC);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(pictureFile);
			
			image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Used for writing images that can't identify a document.
	 * @param image
	 * @return
	 */
	public static void writeNoTextImage(Bitmap image) {
		File pictureFile = getUniqueImageFile(PROBLEMS_DIR_TESS);
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(pictureFile);
			
			image.compress(Bitmap.CompressFormat.JPEG, 100, fos);
			
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Create a File for saving an image
	 * @return	A file with timestamp and jpg extension.
	 */
	public static File getUniqueImageFile(String dir){
		String storageDir = getMediaStorageDir();
	    //
	    // Create a media file name
	    //
	    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
	    
	    File dirFile = new File(storageDir + dir);
	    if (! dirFile.exists()){
	        if (! dirFile.mkdirs()){
	            Log.d(TAG, "failed to create directory");
	            return null;
	        }
	    }
	    
	    File mediaFile = new File(storageDir + dir +"IMG_"+ timeStamp + ".jpg");

	    return mediaFile;
	}
	
	
	private static String getMediaStorageDir() {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIR_NAME);
	    //
	    // Create the storage directory if it does not exist
	    //
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d(TAG, "failed to create directory");
	            return null;
	        }
	    }
	    String result = mediaStorageDir.getPath();
	    if ( ! result.endsWith("/")) {
	    	result += "/";
	    }
	    return result;
	}
}
