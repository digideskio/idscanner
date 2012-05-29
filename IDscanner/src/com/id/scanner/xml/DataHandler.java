package com.id.scanner.xml;


import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.id.scanner.core.Profile;

import android.util.Log;

public class DataHandler extends DefaultHandler {
	private static final String TAG = DataHandler.class.getSimpleName();
	
	private boolean _inProfile = false;
	private boolean _inName = false;
	private boolean _inPictureSize = false;
	private boolean _inDocumentSize = false;
	private boolean _inDisplay= false;
	private boolean _inData= false;
	private boolean _inItem = false;
	private boolean _inType= false;
	private boolean _inItemName = false;
	
	private ArrayList<Profile> profiles = new ArrayList<Profile>();
	private Profile profile;
	private DocumentItem documentItem;

	
	public ArrayList<Profile> getProfileData() {
		return profiles;
	}
	
	@Override
	public void startDocument() throws SAXException {
	}
	
	@Override
	public void endDocument() throws SAXException {
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes atts) throws SAXException {
		
		if (localName.equalsIgnoreCase("PROFILE")) {
			profile= new Profile();
			_inProfile=true;
		} else if (_inProfile) {
			if (localName.equalsIgnoreCase("NAME")) {
				_inName=true;
			} 
			else if (localName.equalsIgnoreCase("PICTURESIZE")) {
				_inPictureSize = true;
			} 
			else if (localName.equalsIgnoreCase("DOCUMENTSIZE")) {
				_inDocumentSize = true;
			} 
			else if (localName.equalsIgnoreCase("DISPLAY")) {
				_inDisplay = true;
			} 
			else if (_inDisplay){
				if (localName.equalsIgnoreCase("RECTANGLE")) {
					try {
						int a = Integer.valueOf(atts.getValue("a"));
						int b = Integer.valueOf(atts.getValue("b"));
						int c = Integer.valueOf(atts.getValue("c"));
						int d = Integer.valueOf(atts.getValue("d"));

						Rectangle rect = new Rectangle(a,b,c,d);
						profile.addDisplayObject(rect);

					} catch (Exception e) {
						Log.d(TAG, "Rectangle size incorecly specified in xml.");
					}
				}
			} 
			else if (localName.equalsIgnoreCase("DATA")) {
				_inData = true;
			} else if (_inData) {
				if (localName.equalsIgnoreCase("ITEM")) {
					_inItem = true;
					this.documentItem = new DocumentItem();
				}
				else if (_inItem) {
					if (localName.equalsIgnoreCase("ITEMNAME")) {
						_inItemName = true;
					}
					else if (localName.equalsIgnoreCase("TYPE")) {
						_inType = true;
					} 
					else if (localName.equalsIgnoreCase("POSITION")) {
						try {
							int x = Integer.valueOf(atts.getValue("x"));
							int y = Integer.valueOf(atts.getValue("y"));
							int w = Integer.valueOf(atts.getValue("w"));
							int h = Integer.valueOf(atts.getValue("h"));

							this.documentItem.setValues(x, y, w, h);
						} catch (Exception e) {
							Log.d(TAG, "Rectangle size incorecly specified in xml.");
						}
					} 
				}
			}
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		if (localName.equalsIgnoreCase("PROFILE")) {
			_inProfile = false;
			profiles.add(profile);
			
		} else if (_inProfile) {
			if (localName.equalsIgnoreCase("NAME")) {
				_inName = false;
			} 
			else if (localName.equalsIgnoreCase("PICTURESIZE")) {
				_inPictureSize = false;
			} 
			else if (localName.equalsIgnoreCase("DOCUMENTSIZE")) {
				_inDocumentSize = false;
			} 
			else if (localName.equalsIgnoreCase("DISPLAY")) {
				_inDisplay = false;
			} 
			else if (_inDisplay){
				if (localName.equalsIgnoreCase("RECTANGLE")) {
					// do nothing
				}
			} 
			else if (localName.equalsIgnoreCase("DATA")) {
				_inData = false;
			} else if (_inData) {
				if (localName.equalsIgnoreCase("ITEM")) {
					_inItem = false;
					profile.addDocumentItem(this.documentItem);
					//documentItem = null;
				}
				else if (_inItem) {
					if (localName.equalsIgnoreCase("ITEMNAME")) {
						_inItemName = false;
					}
					else if (localName.equalsIgnoreCase("TYPE")) {
						_inType = false;
					} 
					else if (localName.equalsIgnoreCase("POSITION")) {
						// do nothing
					} 
				}
			}
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		String chars = new String(ch, start, length); 
	    chars = chars.trim();
	    
	    if (_inProfile) {
	    	if (_inName) {
	    		profile.setName(chars);
	    	}
	    	else if (_inPictureSize) {
	    		try {
	    			profile.setPictureSize(chars);
	    		} catch (Exception e) {
	    			Log.d(TAG, "Picture size incorectly specified in xml.");
	    		}
	    	}
	    	else if (_inDocumentSize) {
	    		try {
	    			profile.setDocumentSize(chars);
	    		} catch (Exception e) {
	    			Log.d(TAG, "Document size incorectly specified in xml.");
	    		}
	    	} else if (_inData) {
	    		if (_inItem) {
	    			if (_inItemName) {
	    				this.documentItem.setName(chars);
	    			} 
	    			else if (_inType) {
	    				this.documentItem.setType(chars);
	    			}
	    		}
	    	}
	    }
	    
	}
}



