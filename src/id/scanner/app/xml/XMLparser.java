package id.scanner.app.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import android.util.Log;

public class XMLparser {
	private static final String TAG = XMLparser.class.getSimpleName();
	
	private String pathToXml = "/path/to/data.xml";
	private File xmlFile ;
	
	public XMLparser() {
		xmlFile = new File(pathToXml);
	}
	
	public XMLparser(String path) {
		xmlFile = new File(path);
	}


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
}
