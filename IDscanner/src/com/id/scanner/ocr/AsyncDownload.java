package com.id.scanner.ocr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import android.os.AsyncTask;
import android.util.Log;

public class AsyncDownload extends AsyncTask<String, Integer, Boolean>{
	private static final String TAG = AsyncDownload.class.getSimpleName();
	
	private URL fileUrl ;
	private String link = "http://tesseract-ocr.googlecode.com/files/ron.traineddata.gz";
	private String trainingFile = "/ron.traineddata";
	
	
	public AsyncDownload() {
		try {
			fileUrl = new URL(link);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected Boolean doInBackground(String... params) {
		int count = params.length;
		if (count == 1) {
			String downloadFolder = params[0];
			String downloadFile = downloadFolder + trainingFile;
			
			Log.d(TAG, "Starting traineddata file download");
			
			boolean successfulDownload = this.download(fileUrl, downloadFile);
			if (successfulDownload) {
				this.gunzip(downloadFile);
			}
		}
		return null;
	}

	private boolean download(URL url, String downloadFile) {
		HttpURLConnection urlConnection;
		try {
			urlConnection = (HttpURLConnection) url.openConnection();

			urlConnection.setAllowUserInteraction(false);
			urlConnection.setInstanceFollowRedirects(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			
			if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				Log.e(TAG, "Did not get HTTP_OK response.");
				Log.e(TAG, "Response code: " + urlConnection.getResponseCode());
				Log.e(TAG, "Response message: " + urlConnection.getResponseMessage().toString());
				return false;
			}

			InputStream inputStream = urlConnection.getInputStream();
			File tempFile = new File(downloadFile + ".gz");

			final int BUFFER = 8192;
			FileOutputStream fileOutputStream = new FileOutputStream(tempFile);

			byte[] buffer = new byte[BUFFER];
			int bufferLength = 0;
			while ((bufferLength = inputStream.read(buffer, 0, BUFFER)) > 0) {
				fileOutputStream.write(buffer, 0, bufferLength);
			}
			
			fileOutputStream.close();
			if (urlConnection != null) {
				urlConnection.disconnect();
			}

			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return false;
	}

	private void gunzip(String downloadFile) {
		try {
			GZIPInputStream gzipInputStream = new GZIPInputStream(
					new BufferedInputStream(
							new FileInputStream(downloadFile + ".gz")));

			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(
					new FileOutputStream( downloadFile ));

			final int BUFFER = 8192;
			byte[] data = new byte[BUFFER];
			int len;

			while ((len = gzipInputStream.read(data, 0, BUFFER)) > 0) {
				bufferedOutputStream.write(data, 0, len);
			}
			
			gzipInputStream.close();
			bufferedOutputStream.flush();
			bufferedOutputStream.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		File zippedFile = new File( downloadFile + ".gz");
		if (zippedFile .exists()) {
			zippedFile.delete();
		}
	}
}
