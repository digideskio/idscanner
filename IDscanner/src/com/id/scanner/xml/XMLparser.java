package com.id.scanner.xml;

import id.scanner.app.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.id.scanner.core.Profile;

import android.content.res.Resources;
import android.util.Log;

/**
 * See http://www.jondev.net/articles/Android_XML_SAX_Parser_Example
 * @author petru
 *
 */
public class XMLparser {
	private static final String TAG = XMLparser.class.getSimpleName();
	
	private String pathToXml = "/mnt/sdcard/Pictures/IDscanner/data.xml";
	private File xmlFile ;

	private Resources resources;
	
	public XMLparser(Resources application) {
		xmlFile = new File(pathToXml);
		this.resources = application;
	}
	
	public XMLparser(String path) {
		xmlFile = new File(path);
	}


	// Unused?
	public ArrayList<Profile> parseXml() { 
		ArrayList<Profile> profiles = null; 

		if (xmlFile.exists()) {
			try { 
				SAXParserFactory spf = SAXParserFactory.newInstance(); 
				SAXParser sp = spf.newSAXParser(); 

				XMLReader xr = sp.getXMLReader(); 

				DataHandler dataHandler = new DataHandler(); 
				xr.setContentHandler(dataHandler); 

				xr.parse(new InputSource(new FileInputStream(xmlFile ))); 

				profiles = dataHandler.getProfileData(); 

			} catch(ParserConfigurationException pce) { 
				Log.e(TAG, "sax parse error", pce); 
			} catch(SAXException se) { 
				Log.e("SAX XML", "sax error", se); 
			} catch(IOException ioe) { 
				Log.e("SAX XML", "sax parse io error", ioe); 
			} 
		} else {
			Log.d(TAG, "XML file not found on disk.");
		}
		return profiles; 
	}

	public ArrayList<Profile> parseXmlResource() { 
		ArrayList<Profile> profiles = null; 

		try {
			
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 

			XMLReader xr = sp.getXMLReader(); 

			DataHandler dataHandler = new DataHandler(); 
			xr.setContentHandler(dataHandler); 

			InputStream input = resources.openRawResource(R.raw.profile);
			xr.parse(new InputSource(input)); 

			profiles = dataHandler.getProfileData(); 

		} catch(ParserConfigurationException pce) { 
			Log.e(TAG, "sax parse error", pce); 
		} catch(SAXException se) { 
			Log.e("SAX XML", "sax error", se); 
		} catch(IOException ioe) { 
			Log.e("SAX XML", "sax parse io error", ioe); 
		} 
		return profiles; 
	}
}
