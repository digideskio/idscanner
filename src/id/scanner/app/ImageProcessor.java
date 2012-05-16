package id.scanner.app;

import android.graphics.Bitmap;
import android.util.Log;

public class ImageProcessor {
	private static final String TAG = ImageProcessor.class.getSimpleName();
	
	private static final int BLACK = 40;
	private Bitmap mImage;

	
	public ImageProcessor(Bitmap image) {
		this.mImage = image;
	}

	
	/**
	 * @return	A bitmap representing the OCR zone of the document.
	 */
	public Bitmap getOcrZone() {
		int width, height;

		width = mImage.getWidth();
		height = mImage.getHeight();

		int i, top=0, left=0 , right=0, bottom=0;

		int middle = width/2;
		for (i=5; i<height/2; i=i+2) {
			int gray = getGrayValue(mImage.getPixel(middle, i));
			
			if (gray>BLACK) {
				break;
			}
		}
		top = i;

		middle = height/2;
		for (i=5; i<width/2; i=i+2) {
			int gray = getGrayValue(mImage.getPixel(i, middle));

			if (gray>BLACK) {
				break;
			}
		}
		left = i;

		middle = height/2;
		for (i=width-1; i>width/2; i=i-2) {
			int gray = getGrayValue(mImage.getPixel(i, middle));

			if (gray>BLACK) {
				break;
			}
		}
		right = i;

		middle = width/2;
		for (i=height-1; i>height/2; i=i-2) {
			int gray = getGrayValue(mImage.getPixel(middle, i));

			if (gray>BLACK) {
				break;
			}
		}
		bottom = i;

		if (left>0 && top>0 && right<width && bottom<height &&  left<right && top<bottom) {
			Bitmap document = Bitmap.createBitmap(mImage, left, top, (right - left), (bottom - top));
			return extractName(document);
		}  else {
			Log.d(TAG, "Problems encountered while trying to determine document margins.");
		}
		return null;
	}
	
	private int idWidth=105;
	private int ocrZoneWidth=95, ocrZoneHeight=13;
	private int ocrZoneStartX=6, ocrZoneStartY=50;
	
	private Bitmap extractName(Bitmap image) {
		// this is float for precision when calculating pixel.
		float width = image.getWidth();
		int height = image.getHeight();

		Log.d(TAG, "Ocr zone width: " + width + " height: " + height);
		
		float pixel = width/idWidth;
		
		int left = (int) (ocrZoneStartX * pixel);
		int top = (int) (ocrZoneStartY * pixel);
		int w = (int) (ocrZoneWidth * pixel); 
		int h = (int) (ocrZoneHeight * pixel);

		Log.d(TAG, "Pixel: " + pixel);
		Log.d(TAG, "left: " + left + " top: " + top);
		Log.d(TAG, "width: " + w + "height" + h);
		
		if (left>0 && top>0 && (w+left)<width && (h+top)<height ) {
			Bitmap result = Bitmap.createBitmap(image, left, top, w, h);
			Util.writeToDisk(result, "ocrZone");
			
			return result;
		} else {
			Log.d(TAG, "Problems encountered while trying to determine the image that will be processed.");
		}
		return null;
	}
	
	/**
	 * 
	 * @param pixel	pixel value in ARGB
	 * @return		grayscale value: 0-255
	 */
	private int getGrayValue(int pixel) {
		int r= (pixel >> 16) & 0xff;
		int g= (pixel >> 8) & 0xff;
		int b = (pixel) & 0xff;
		
		// http://www.johndcook.com/blog/2009/08/24/algorithms-convert-color-grayscale/
		// lightness
		// int gray = (max(r,g,b) + min(r,g,b)) / 2;
		
		// luminosity
		// int gray = (int) (0.21*r + 0.71*g + 0.07*b);
		
		// average
		int gray = (r+g+b)/3;
		
		return gray;
	}
}
