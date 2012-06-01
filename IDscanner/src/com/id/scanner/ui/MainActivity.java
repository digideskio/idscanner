package com.id.scanner.ui;

import java.io.File;
import java.util.ArrayList;

import com.id.scanner.camera.CameraManager;
import com.id.scanner.camera.PictureManager;
import com.id.scanner.core.IDdata;
import com.id.scanner.core.Profile;
import com.id.scanner.core.ProfileManager;
import com.id.scanner.database.DatabaseAdapter;
import com.id.scanner.synchronize.ServerSynchronization;
import com.id.scanner.xml.XMLparser;

import id.scanner.app.R;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private TransparentProgressDialog progressDialog;
	private boolean runningProgressDialog = false;
	private IDdata data;
	
	private static int counter = 0;
	private static Context context;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate()");

        initializeProfile();
        context = this.getApplicationContext();
        
		Window window = getWindow();
	    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	    
        setContentView(R.layout.main);
    }

    private void initializeProfile() {
    	DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
    	db.open();

    	ArrayList<String> dbProfiles = db.getProfileList();
    	
    	XMLparser parser = new XMLparser(this.getResources());
    	ArrayList<Profile> xmlProfiles = parser.parseXmlResource();
    	//
    	// If we have no profiles, populate the database.
    	//
    	if (dbProfiles.size() == 0) {
    		db.insertProfile(xmlProfiles);
    	}
    	
    	Profile profile = xmlProfiles.get(0);
    	ProfileManager.getInstance().setCurrentProfile(profile);

    	Log.d(TAG, profile.toString());
    	
    	db.close();
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
 
    /**
     * Called whenever the camera button is clicked. 
     * @param v
     */
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

    /**
     * Called by CameraManager when ocr process has finished.
     * @param text
     * @param c
     * @param pictureFile
     */
	public void showResults(String text, int c, String pictureFile) {
		Toast toast = new Toast(this);
		
		if (text != null && c > 65 ) {
			progressDialog.dismiss();
			runningProgressDialog = false;
			toast.cancel();
			
			data = new IDdata(this);
			
			if (data.setRawText(text)) {
				data.setPictureFile(pictureFile);
				
				ArrayList<String> results = data.getGUIlist(); 
				
				ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
				scrollView.setVisibility(View.VISIBLE);
	
				LinearLayout resultView = (LinearLayout) findViewById(R.id.result_view);
	
				// comment this for final version
				TextView tesseractText= new TextView(getApplication());
				tesseractText.setText(text);
				resultView.addView(tesseractText);
				
				
				for (int i=0; i<results.size()-1; i=i+3 ) {
					resultView.addView(addLinearLayout(results.get(i), results.get(i+1), results.get(i+2), i/3));
				}
	
				// comment this for final version
				TextView confidence = new TextView(getApplication());
				confidence.setText("Confidence: " + c);
				resultView.addView(confidence);
				//
				// Add the image!
				//
				LinearLayout imageView = (LinearLayout) findViewById(R.id.images_view);
				imageView.addView( this.getImageView() );
				
				return;		// success !!!
			} else {
				toast = Toast.makeText(this, "Data could not be interpreted.", Toast.LENGTH_SHORT);
				toast.show();
			}
		} else if ( c == -1 ) {		// hack for not finding any documents
			toast = Toast.makeText(this, "No document was identified in the picture", Toast.LENGTH_SHORT);
			toast.show();
		} else {					// tesseract could not interpret image
			toast = Toast.makeText(this, "No text found", Toast.LENGTH_SHORT);
			toast.show();
		}
		// need to take another picture.
		
		if (counter < 15) {
			counter++;
			Log.d(TAG, "Rescanning.");
			onClickCamera(null);	
		} else {
			counter = 0;
			progressDialog.dismiss();
			toast.cancel();
			runningProgressDialog = false;
		}
	}
    
	/**
	 * 
	 * @param label
	 * @param text
	 * @param valid 
	 * @return		A linear layout containing a text view for "label", and
	 * 				an EditText for "text".
	 */
	private View addLinearLayout(String label, String text, String valid, int id) {
		LinearLayout result = new LinearLayout(getApplicationContext());
		result.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		result.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView type = new TextView(getApplication());
		type.setText(label + ":");
		
		EditText value = new EditText(getApplication());
		value.setText(text);
		
		if ( ! valid.equals("0")) {
			value.setTextColor(Color.RED);
		}
		value.setId(id);
		
		result.addView(type);
		result.addView(value);
		
		return result;
	}

	/**
	 * @return	an image view containing the scanned image.
	 */
	private View getImageView() {
		ImageView view = new ImageView(this);

		File imgFile = new  File(data.getPictureLocation());
	    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		
		view.setImageBitmap(myBitmap);
		
		return view;
	}
	
	/**
	 * Used for synchronizing the local database with a remote database.
	 * @param v
	 */
	public void onClickSync(View v) {
		ServerSynchronization serverSync = new ServerSynchronization(this);
		serverSync.start();
	}
	
	/**
	 * OK button for inserting the results into the database.
	 * @param v
	 */
	public void onClickOk(View v) {
		//
		// Create a data object with the values from the GUI
		// Use the old data object in case something goes wrong.
		//
		for (int i=0;i<IDdata.NR_OF_FIELDS;i++) {
			String value = ((EditText)findViewById(i)).getText().toString();
			data.setField(value, i);
		}
		
		DatabaseAdapter db = new DatabaseAdapter(getApplicationContext());
		db.open();
		db.insertData(data);
		db.close();
		
		Toast.makeText(this, "Inserted ID data into database.", Toast.LENGTH_SHORT).show();
		
		this.onClickCancel(v);
	}
	
	/**
	 * Set the visibility to "GONE" for the scroll view.
	 * Remove what was added so that on the next opening, everything is new.
	 * @param v
	 */
	public void onClickCancel(View v) {
		ScrollView scrollView = (ScrollView) findViewById(R.id.scroll_view);
		scrollView.setVisibility(View.GONE);

		((LinearLayout) findViewById(R.id.result_view)).removeAllViews();
		((LinearLayout) findViewById(R.id.images_view)).removeAllViews();
	}

	public static Context getMainApplicationContext() {
		return context;
	}

}
