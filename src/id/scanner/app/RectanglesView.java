package id.scanner.app;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;


/**
 * Optimized for Galaxy Nexus display: 1196 (1280-action bar) ,720
 * @author petru
 *
 */
public class RectanglesView extends View {
	private static final String TAG = RectanglesView.class.getSimpleName();


	public RectanglesView(Context context) {
		super(context);
	}
	
	public RectanglesView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	public RectanglesView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	
	@Override
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(Color.WHITE);
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(3);
		
		int[][] rectangles = {{152, 45, 1044, 675}};
		
		for (int i=0; i<rectangles.length; i++) {
			canvas.drawRect(rectangles[i][0], rectangles[i][1], rectangles[i][2], rectangles[i][3], paint);
		}
		
		Log.d(TAG, "Rectangles view created");
	}

}
