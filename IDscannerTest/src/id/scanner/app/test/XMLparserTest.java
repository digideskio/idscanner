package id.scanner.app.test;

import java.util.ArrayList;

import com.id.scanner.core.Profile;
import com.id.scanner.xml.DocumentItem;
import com.id.scanner.xml.Rectangle;
import com.id.scanner.xml.XMLparser;

import junit.framework.TestCase;

public class XMLparserTest extends TestCase {
	//private String pathToXml = "/data/workspace/android/main/IDscanner/res/raw/profile.xml";
	private String pathToXml = "/mnt/sdcard/Pictures/IDscanner/data.xml";

	public void testParseXml() {
		XMLparser parser = new XMLparser(pathToXml);
		ArrayList<Profile> actual = parser.parseXml();
		
		Profile expected = new Profile();
		
		expected.setName("CI-ROU");
		expected.setDocumentSize("105x74");
		expected.setPictureSize("2592x1458");
		
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
		picture.setValues(3, 8, 29, 34);
		expected.addDocumentItem(picture);
		
		assertEquals(expected, actual.get(0));
		
		expected.setName("PASSPORT-ROU");
		assertEquals(expected, actual.get(1));
	}
}
