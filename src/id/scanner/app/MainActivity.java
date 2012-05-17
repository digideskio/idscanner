package id.scanner.app;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        
		Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    //requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	Log.d(TAG, "onResume()");
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    	Log.d(TAG, "onPause()");
    }
    
    public void onClickCamera(View v) {
    	Camera camera = CameraManager.getCamera();
    	PictureManager pictureManager = new PictureManager(getApplication(), this);
    	
    	camera.takePicture(null, null, pictureManager);
    	
    	camera.startPreview();
	}

	public void showResults(String text, int c) {
		LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);
		resultView.setVisibility(View.VISIBLE);
		
		TextView result = (TextView) findViewById(R.id.result_text);
		result.setText(text);
		
		TextView confidence = (TextView) findViewById(R.id.confidence_text);
		confidence.setText("Confidence: " + c);
		
	}
    
	public void onClickOk(View v) {
		LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);
		resultView.setVisibility(View.GONE);
	}
    
}
