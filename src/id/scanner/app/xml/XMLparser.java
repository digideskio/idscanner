package id.scanner.app.xml;

import id.scanner.app.R;
import id.scanner.app.core.Profile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

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
	public Profile parseXml() { 
		Profile profile = null; 

		if (xmlFile.exists()) {
			try { 
				SAXParserFactory spf = SAXParserFactory.newInstance(); 
				SAXParser sp = spf.newSAXParser(); 

				XMLReader xr = sp.getXMLReader(); 

				DataHandler dataHandler = new DataHandler(); 
				xr.setContentHandler(dataHandler); 

				xr.parse(new InputSource(new FileInputStream(xmlFile ))); 

				profile = dataHandler.getProfileData(); 

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
		return profile; 
	}

	public Profile parseXmlResource() { 
		Profile profile = null; 

		try {
			
			SAXParserFactory spf = SAXParserFactory.newInstance(); 
			SAXParser sp = spf.newSAXParser(); 

			XMLReader xr = sp.getXMLReader(); 

			DataHandler dataHandler = new DataHandler(); 
			xr.setContentHandler(dataHandler); 

			InputStream input = resources.openRawResource(R.raw.profile);
			xr.parse(new InputSource(input)); 

			profile = dataHandler.getProfileData(); 

		} catch(ParserConfigurationException pce) { 
			Log.e(TAG, "sax parse error", pce); 
		} catch(SAXException se) { 
			Log.e("SAX XML", "sax error", se); 
		} catch(IOException ioe) { 
			Log.e("SAX XML", "sax parse io error", ioe); 
		} 
		return profile; 
	}
}
