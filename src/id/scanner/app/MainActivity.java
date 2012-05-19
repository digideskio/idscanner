package id.scanner.app;

import java.util.ArrayList;

import id.scanner.app.core.ProfileManager;
import id.scanner.app.database.DatabaseAdapter;
import id.scanner.app.xml.Profile;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	// not sure using this is a good idea.
	private static ContextWrapper application;
	private ProgressDialog progressDialog;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application = getApplication();
        
        Log.d(TAG, "onCreate()");
        
		Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        setContentView(R.layout.main);
        
        initializeApplication();
    }
    
    private void initializeApplication() {
    	//
    	// Read the xml content each time the aplication starts.  Could this be optimized?
    	//
    	ProfileManager pm = ProfileManager.getInstance();
    	Profile profile = pm.getCurrentProfile();

    	Log.d(TAG, profile.toString());

    	if (profile != null) {
    		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
    		db.open();
    		
    		ArrayList<String> profiles = db.getProfileList();
    		
    		if ( ! profiles.contains(profile.getName().toUpperCase())) {
        		db.insertProfile(profile);
    		} else {
    			Log.d(TAG, "Database contains xml profile.");
    		}
    		db.close();
    	}
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
    	PictureManager pictureManager = new PictureManager(this);
    	
    	camera.takePicture(null, null, pictureManager);
    	
    	camera.startPreview();
    	
    	progressDialog = ProgressDialog.show(this, "Processing Image", "Please wait", true, true);
	}

	public void showResults(String text, int c) {
		Toast toast = new Toast(this);
		
		if (text != null && c > 70 ) {
			progressDialog.dismiss();
			toast.cancel();
			
			LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);
			resultView.setVisibility(View.VISIBLE);

			TextView result = (TextView) findViewById(R.id.result_text);
			result.setText(text);

			TextView confidence = new TextView(getApplication());
			confidence.setText("Confidence: " + c);

			resultView.addView(confidence);
			
			return; // done here.
		} else if ( c == -1 ) {		// hack for not finding any documents
			toast = Toast.makeText(this, "No document was identified in the picture", Toast.LENGTH_SHORT);
		} else {					// tesseract could not interpret image
			toast = Toast.makeText(this, "No text found", Toast.LENGTH_SHORT);
		}
		// need to take another picture.
		toast.show();

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		onClickCamera(null);	// do we really need any view?
	}
    
	public void onClickOk(View v) {
		LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);
		resultView.setVisibility(View.GONE);
	}

	public static Resources getApplicationResources() {
		return application.getResources();
	}
    
}
