package id.scanner.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Optimized for Galaxy Nexus display: 1196 (1280-action bar) ,720
 * @author petru
 *
 */
public class TextRectanglesView extends View {
	private static final String TAG = TextRectanglesView.class.getSimpleName();

	// General coordinates 
	private final static int wordHeight = 35;
	private final static int wordLength = 200;
	
	// Image coordinates
	private final static int imageStartX = 30;
	private final static int imageStartY = 80;
	private final static int imageEndX = 320;
	private final static int imageEndY = 450;
	
	// text coordinates
	private final static int firstNameStartX = 325;
	private final static int firstNameStartY = 200;
	private final static int firstNameEndX = firstNameStartX + wordLength;
	private final static int firstNameEndY = firstNameStartY + wordHeight;	
	
	private final static int lastNameStartX = firstNameStartX;
	private final static int lastNameStartY = firstNameEndY + 14;
	private final static int lastNameEndX = lastNameStartX + wordLength;
	private final static int lastNameEndY = lastNameStartY + wordHeight;
	
	private final static int fatherNameStartX = lastNameStartX;
	private final static int fatherNameStartY = lastNameEndY + 14;
	private final static int fatherNameEndX = fatherNameStartX + wordLength;
	private final static int fatherNameEndY = fatherNameStartY + wordHeight;
	
	private final static int seriaStartX = 615;
	private final static int seriaStartY = 115;
	private final static int seriaEndX = seriaStartX + 40;
	private final static int seriaEndY = seriaStartY + wordHeight;
	
	private final static int numberStartX = seriaEndX + 40;
	private final static int numberStartY = seriaStartY;
	private final static int numberEndX = numberStartX + 110;
	private final static int numberEndY = numberStartY + wordHeight;
	
	
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
		// this.drawRectWithHolles(canvas);
		this.drawRectangles(canvas);
	}

	private void drawRectWithHolles(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(R.color.TextRectaglesForegraund);

//		canvas.drawRect(0,100,1030,650, paint);
//		canvas.drawRect(500,0,1030,100, paint);
		
		Point screenResolution = CameraManager.screenResolution;

		int[][] rectangles = {
				{0, 0, screenResolution.x, imageStartY}, 
				{0, imageStartY, imageStartX, imageEndY},
				{imageEndX, imageStartY, screenResolution.x, firstNameStartY},
				{imageEndX, firstNameStartY, firstNameStartX, firstNameStartY+wordHeight},
				{firstNameStartX+wordLength, firstNameStartY, screenResolution.x, firstNameStartY+wordHeight},
				{imageEndX, firstNameStartY+wordHeight, screenResolution.x, imageEndY}, 
				{0, imageEndY, screenResolution.x, screenResolution.y}
		};
		
		for (int i=0; i<rectangles.length; i++) {
				canvas.drawRect(rectangles[i][0], rectangles[i][1], rectangles[i][2], rectangles[i][3], paint);
		}
	}
	
	private void drawRectangles(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		
		//Point screenResolution = CameraManager.screenResolution;
		
		int[][] rectangles = {
				{imageStartX, imageStartY, imageEndX, imageEndY},
				{firstNameStartX, firstNameStartY, firstNameEndX, firstNameEndY},
				{lastNameStartX, lastNameStartY, lastNameEndX, lastNameEndY},
				{fatherNameStartX, fatherNameStartY, fatherNameEndX, fatherNameEndY},
				{seriaStartX, seriaStartY, seriaEndX, seriaEndY},
				{numberStartX, numberStartY, numberEndX, numberEndY}
		};
		
		for (int i=0; i<rectangles.length; i++) {
			canvas.drawRect(rectangles[i][0], rectangles[i][1], rectangles[i][2], rectangles[i][3], paint);
		}
	}
}
