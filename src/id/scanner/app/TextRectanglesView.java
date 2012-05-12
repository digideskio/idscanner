package id.scanner.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class TextRectanglesView extends View {
	private static final String TAG = TextRectanglesView.class.getSimpleName();
	// ID size
	private final static int idWidth = 105;
	private final static int idHeight= 74;
	private final static double idProportion= 1.41891; //idWidth/idHeight

	// General coordinates 
	private final static int dataStart = 33;
	private final static int textHeight = 4;
	private final static int nameLength = 20;
	
	// Image coordinates
	private final static int imageStartX = 3;
	private final static int imageStartY = 8;
	private final static int imageEndX = imageStartX + 29;
	private final static int imageEndY = imageStartY + 37;
	
	// text coordinates
	private final static int name = 20;
	
	
	
	public TextRectanglesView(Context context) {
		super(context);
	}
	
	public TextRectanglesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public TextRectanglesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		this.drawRectWithHolles(canvas);
	}

	
	/**
	 * 
	 * ________________________________
	 * |______
	 * ||    |
	 * ||    |
	 * ||    |
	 * ||____|
	 * |
	 * |
	 * |_______________________________
	 * 
	 * @param canvas
	 */
	private void drawRectWithHolles(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(R.color.TextRectaglesForegraund);

//		canvas.drawRect(0,100,1030,650, paint);
//		canvas.drawRect(500,0,1030,100, paint);
		
		Point screenResolution = CameraManager.screenResolution;

		int height = screenResolution.y;
		int width = (int) (screenResolution.y * idProportion);
		
		if (width <= screenResolution.x ) {
			// Use this as unit from here on
			float pixel = height/idHeight;

			int margin = (screenResolution.x - width) / 2;
			//int margin = 0;
			
			// 
			canvas.drawRect(margin, 0, width+margin, imageStartY*pixel, paint);
			canvas.drawRect(margin, imageStartY*pixel, margin+imageStartX*pixel, imageEndY*pixel, paint);
			canvas.drawRect(margin+imageEndX*pixel, imageStartY*pixel, width+margin, imageEndY*pixel, paint);
			canvas.drawRect(margin, imageEndY*pixel, width+margin, height,paint);

			Log.d(TAG, "Screen Resolution: " + screenResolution.x + ", " + screenResolution.y);
			Log.d(TAG,"Pixel:" + pixel);
			Log.d(TAG, "imageStartY*pixel" + imageStartY*pixel);
		} else {
			//canvas.drawRect(10,10,screenResolution.x - 10, screenResolution.y - 10, paint);
			canvas.drawText("   ERROR   ", 0, 0, paint);
		}
	}
}
