package com.id.scanner.core;


import java.util.ArrayList;


public class Profile {
	private String name;
	private int pictureSizeX, pictureSizeY;
	private int documentSizeX, documentSizeY;
	private String serverIp;
	private int serverPort;
	private String user;
	private String pass;
	
	private ArrayList<Rectangle> displayObjects;
	private ArrayList<DocumentItem> documentItems;
	
	
	public Profile() {
		displayObjects = new ArrayList<Rectangle>();
		documentItems = new ArrayList<DocumentItem>();
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
	
	//==================   getters and setters =================================
	public void setName(String name) { this.name = name.toUpperCase(); }
	public void addDisplayObject(Rectangle display) { displayObjects.add(display); }
	public void addDocumentItem(DocumentItem item ) { documentItems.add(item); }
	public String getServerIp() { return serverIp; }
	public int getServerPort() { return serverPort;}
	public String getName() { return name; }
	public int getPictureSizeX() { return pictureSizeX; }
	public int getPictureSizeY() { return pictureSizeY; }
	public int getDocumentSizeX() { return documentSizeX; }
	public int getDocumentSizeY() {	return documentSizeY; }
	public String getUser() { return user;}
	public String getPass() { return pass;}
	public ArrayList<Rectangle> getDisplayObjects() { return displayObjects; }
	public ArrayList<DocumentItem> getDocumentItems() {return documentItems; }
	public void setServerIp(String serverIp) {this.serverIp = serverIp;}
	public void setServerPort(int serverPort) {this.serverPort = serverPort;}
	public void setPass(String pass) {this.pass = pass; }
	public void setUser(String user) { this.user = user;}
	
	// Overwritten to print to logs.
	@Override
	public String toString() {
		String result = "Profile name: " + name;
		result += "\nPicture size: " + pictureSizeX + "x" + pictureSizeY;
		result += "\nDocument size: " + documentSizeX + "x" + documentSizeY;
		result += "\nServer ip: " + serverIp;
		result += "\nServer port: " + serverPort;
		result += "\nUser: " + user;
		result += "\nPass: " + pass;
		
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
			Profile profile = (Profile) o;
			if (profile.name.equals(this.name) &&
					profile.documentSizeX == this.documentSizeX &&
					profile.documentSizeY == this.documentSizeY &&
					profile.pictureSizeX == this.pictureSizeX &&
					profile.pictureSizeY == this.pictureSizeY &&
					profile.serverPort == this.serverPort &&
					profile.serverIp.equals(this.serverIp) &&
					profile.user.equals(this.user) &&
					profile.pass.equals(this.pass)) {
				
				for (int i=0; i<displayObjects.size(); i++) {
					Rectangle r1 = profile.displayObjects.get(i);
					Rectangle r2 = this.displayObjects.get(i);
					
					if (! r1.equals(r2)) {
						return false;
					}
				}
				
				for (int i=0; i<documentItems.size(); i++) {
					DocumentItem d1 = profile.getDocumentItems().get(i);
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




