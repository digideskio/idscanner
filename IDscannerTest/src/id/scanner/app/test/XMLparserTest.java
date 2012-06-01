package id.scanner.app.test;

import java.util.ArrayList;

import com.id.scanner.core.DocumentItem;
import com.id.scanner.core.Profile;
import com.id.scanner.core.Rectangle;
import com.id.scanner.xml.XMLparser;

import junit.framework.TestCase;

public class XMLparserTest extends TestCase {
	private String pathToXml = "/mnt/sdcard/Pictures/IDscanner/profile.xml";

	public void testParseXml() {
		XMLparser parser = new XMLparser(pathToXml);
		ArrayList<Profile> actual = parser.parseXml();
		
		Profile expected = new Profile();
		
		expected.setName("CI-ROU");
		expected.setDocumentSize("105x74");
		expected.setPictureSize("2592x1458");
		expected.setServerIp("192.168.2.100");
		expected.setServerPort(1212);
		expected.setUser("user");
		expected.setPass("pass");
		
		Rectangle rect = new Rectangle(152, 45, 1044, 675);
		expected.addDisplayObject(rect);
		
		DocumentItem text = new DocumentItem();
		text.setName("OCRzone");
		text.setType("text");
		text.setValues(6, 50, 95, 13);
		expected.addDocumentItem(text);
		
		DocumentItem picture = new DocumentItem();
		picture.setName("Picture");
		picture.setType("picture");
		picture.setValues(4, 8, 28, 33);
		expected.addDocumentItem(picture);
		
		assertEquals(expected, actual.get(0));
		
		expected.setName("PASSPORT-ROU");
		assertEquals(expected, actual.get(1));
	}
}
