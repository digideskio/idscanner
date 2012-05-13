package id.scanner.app;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class CameraButton extends ImageView {
	private Context context;

	public CameraButton(Context context) {
		super(context);
		this.context = context;
	}

	public CameraButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}
	
	public CameraButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context = context;
	}
}
