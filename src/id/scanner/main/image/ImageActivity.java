package id.scanner.main.image;

import id.scanner.main.R;
import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class ImageActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.image_view);
		
		Bundle bundle = getIntent().getExtras();
		String uri = "file:/" + bundle.getString("IMAGE_FILE");
		
		ImageView imgV = (ImageView) findViewById(R.id.displayImage);
		imgV.setImageURI(Uri.parse(uri));
	}
}
