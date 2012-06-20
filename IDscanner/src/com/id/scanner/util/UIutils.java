package com.id.scanner.util;

import java.io.File;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UIutils {

	/**
	 * @param label
	 * @param text
	 * @param valid 
	 * @return		A linear layout containing a text view for "label", and
	 * 				an EditText for "text".
	 */
	public static View createResultLinearLayout(Context context, String label, String text, String valid, int id) {
		LinearLayout result = new LinearLayout(context);
		result.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		result.setOrientation(LinearLayout.HORIZONTAL);
		
		TextView type = new TextView(context);
		type.setText(label + ":");
		
		EditText value = new EditText(context);
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
	public static View getImageView(Context context, String picLocation) {
		ImageView view = new ImageView(context);

		File imgFile = new  File(picLocation);
	    Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
		
		view.setImageBitmap(myBitmap);
		
		return view;
	}
	
	public static TextView makeTextView(Context context, String text) {
		TextView result = new TextView(context);
		result.setText(text);
		
		return result;
	}
}
