package id.scanner.main;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class TextFinderView extends View{

	public TextFinderView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(R.color.background);
		
		canvas.drawRect(0,0,1100,200, paint);
		canvas.drawRect(0,200,300,800, paint);
		canvas.drawRect(300,300,800,800, paint);
		canvas.drawRect(800,200,1100,800, paint);
	}
}
