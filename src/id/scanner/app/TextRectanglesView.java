package id.scanner.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

public class TextRectanglesView extends View {

	
	public TextRectanglesView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public TextRectanglesView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}
	
	public TextRectanglesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(R.color.TextRectaglesForegraund);
		
		Point screenResolution = CameraManager.screenResolution;
//		canvas.drawRect(0,100,1030,650, paint);
//		canvas.drawRect(500,0,1030,100, paint);
		canvas.drawRect(10,10,screenResolution.x - 10, screenResolution.y - 10, paint);
	}
}
