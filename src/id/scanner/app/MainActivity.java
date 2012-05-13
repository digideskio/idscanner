package id.scanner.app;

import android.app.Activity;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private static final int ABOUT_ID = 0;
	
	
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
    
    public void onClick(View v) {
    	Camera camera = CameraManager.getCamera();
    	PictureManager pictureManager = new PictureManager(getApplication());
    	
    	camera.takePicture(null, null, pictureManager);
    	
    	camera.startPreview();
    	
		Toast.makeText(this, "IDscanner 2012 Petru Isfan", Toast.LENGTH_SHORT).show();
	}
    
    /////////////////////   The menu   ////////////////////////////////////////
    //// Does not work!!!
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, ABOUT_ID, 0, "About").setIcon(android.R.drawable.ic_menu_preferences);

    	return true;
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    	case ABOUT_ID: 
    		Toast.makeText(this, "IDscanner 2012 Petru Isfan", Toast.LENGTH_SHORT).show();
			return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
}