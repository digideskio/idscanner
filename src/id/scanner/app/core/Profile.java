package id.scanner.app.core;

import id.scanner.app.xml.DocumentItem;
import id.scanner.app.xml.Rectangle;

import java.util.ArrayList;

public class Profile {
	private String name;
	private int pictureSizeX, pictureSizeY;
	private int documentSizeX, documentSizeY;
	
	private ArrayList<Rectangle> displayObjects;
	private ArrayList<DocumentItem> documentItems;
	
	
	public Profile() {
		displayObjects = new ArrayList<Rectangle>();
		documentItems = new ArrayList<DocumentItem>();
	}

	
	public void setName(String name) {
		this.name = name.toUpperCase();
	}
	
	public void setPictureSize(String size) {
		String[] aux = size.split("x");
		
		if (aux.length == 2) {
			this.pictureSizeX = Integer.valueOf(aux[0]);
			this.pictureSizeY = Integer.valueOf(aux[1]);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public void setDocumentSize(String size) {
		String[] aux = size.split("x");
		
		if (aux.length == 2) {
			this.documentSizeX = Integer.valueOf(aux[0]);
			this.documentSizeY = Integer.valueOf(aux[1]);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	public void addDisplayObject(Rectangle display) {
		displayObjects.add(display);
	}
	
	public void addDocumentItem(DocumentItem item ) {
		documentItems.add(item);
	}

	public String getName() {
		return name;
	}

	public int getPictureSizeX() {
		return pictureSizeX;
	}

	public int getPictureSizeY() {
		return pictureSizeY;
	}

	public int getDocumentSizeX() {
		return documentSizeX;
	}

	public int getDocumentSizeY() {
		return documentSizeY;
	}

	public ArrayList<Rectangle> getDisplayObjects() {
		return displayObjects;
	}

	public ArrayList<DocumentItem> getDocumentItems() {
		return documentItems;
	}
	
	// Overwritten to print to logs.
	@Override
	public String toString() {
		String result = "Profile name: " + name;
		result+="\nPicture size: " + pictureSizeX + "x" + pictureSizeY;
		result+="\nDocument size: " + documentSizeX + "x" + documentSizeY;
		
		for (Rectangle d: displayObjects) {
			result+="\n" + d;
		}
		
		for (DocumentItem i: documentItems) {
			result+="\n" + i;
		}
		return result;
	}
	
	// Overwritten for testing purpose.
	@Override
	public boolean equals(Object o) {
		if (o instanceof Profile) {
			Profile prof = (Profile) o;
			if (prof.name.equals(this.name) &&
					prof.documentSizeX == this.documentSizeX &&
					prof.documentSizeY == this.documentSizeY &&
					prof.pictureSizeX == this.pictureSizeX &&
					prof.pictureSizeY == this.pictureSizeY) {
				
				for (int i=0; i<displayObjects.size(); i++) {
					Rectangle r1 = prof.displayObjects.get(i);
					Rectangle r2 = this.displayObjects.get(i);
					
					if (! r1.equals(r2)) {
						return false;
					}
				}
				
				for (int i=0; i<documentItems.size(); i++) {
					DocumentItem d1 = prof.getDocumentItems().get(i);
					DocumentItem d2 = this.getDocumentItems().get(i);
					
					if (! d1.equals(d2)) {
						return false;
					}
				}
				
				return true;
			}
		}
		return false;
	}
}




