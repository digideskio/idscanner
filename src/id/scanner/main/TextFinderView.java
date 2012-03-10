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
		int left=0, top=0, right=1100, bottom=800;
		Rect rect = new Rect(left, top, right, bottom);
		
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(R.color.background);
		
		canvas.drawRect(rect, paint);
	}
}
