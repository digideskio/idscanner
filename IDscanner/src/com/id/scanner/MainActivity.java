package com.id.scanner;

import java.util.ArrayList;

import com.id.scanner.core.IDdata;
import com.id.scanner.core.Profile;
import com.id.scanner.core.ProfileManager;
import com.id.scanner.database.DatabaseAdapter;

import id.scanner.app.R;
import android.app.Activity;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	// not sure using this is a good idea.
	private static ContextWrapper application;
	private TransparentProgressDialog progressDialog;
	private boolean runningProgressDialog = false;
	private IDdata data;
	
	private static int counter = 0;
	
	
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
    	// Read the xml content each time the application starts.  Could this be optimized?
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
    	
    	if ( ! runningProgressDialog) {
    		progressDialog = TransparentProgressDialog.show(this, "Processing Image", "Please wait", true, true);
    		runningProgressDialog = true;
    	}
	}

	public void showResults(String text, int c, String pictureFile) {
		Toast toast = new Toast(this);
		
		if (text != null && c > 70 ) {
			progressDialog.dismiss();
			runningProgressDialog = false;
			toast.cancel();
			
			data = new IDdata();
			if (!data.setRawText(text)) {
				return;
			}
			data.setPictureFile(pictureFile);
			
			ArrayList<String> results = data.getGUIlist(); 

			
			ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
			scrollView.setVisibility(View.VISIBLE);

			LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);

			
			TextView tesseractText= new TextView(getApplication());
			tesseractText.setText(text);
			resultView.addView(tesseractText);

			
			
			for (int i=0; i<results.size()-1; i=i+2 ) {
				resultView.addView(addLinearLayout(results.get(i), results.get(i+1)));
			}

			TextView confidence = new TextView(getApplication());
			confidence.setText("Confidence: " + c);
			resultView.addView(confidence);
			
			return;
		} else if ( c == -1 ) {		// hack for not finding any documents
			toast = Toast.makeText(this, "No document was identified in the picture", Toast.LENGTH_SHORT);
		} else {					// tesseract could not interpret image
			toast = Toast.makeText(this, "No text found", Toast.LENGTH_SHORT);
		}
		// need to take another picture.
		toast.show();

//		try {
//			Thread.sleep(500);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		
		if (counter < 15) {
			counter++;
			Log.d(TAG, "Rescanning.");
			onClickCamera(null);	
		} else {
			counter = 0;
			progressDialog.dismiss();
			runningProgressDialog = false;
		}
	}
    
	private View addLinearLayout(String label, String text) {
		LinearLayout result = new LinearLayout(getApplicationContext());
		result.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		result.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView type = new TextView(getApplication());
		type.setText(label);
		
		EditText value = new EditText(getApplication());
		value.setText(text);
		
		result.addView(type);
		result.addView(value);
		
		return result;
	}

	public void onClickSync(View v) {
		Toast.makeText(this, "Synchronization finished.", Toast.LENGTH_SHORT).show();
	}
	
	public void onClickOk(View v) {
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
		db.open();
		db.insertData(data);
		db.close();
		
		Toast.makeText(this, "Inserted ID data into database.", Toast.LENGTH_SHORT).show();
		
		ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
		scrollView.setVisibility(View.GONE);
		
		((LinearLayout) findViewById(R.id.result_view)).removeAllViews();
	}
	
	public void onClickCancel(View v) {
		ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
		scrollView.setVisibility(View.GONE);
		
		((LinearLayout) findViewById(R.id.result_view)).removeAllViews();
	}

	public static Resources getApplicationResources() {
		return application.getResources();
	}
    
}
