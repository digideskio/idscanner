package id.scanner.app.xml;

public class DocumentItem {
	String name;
	DataType type;
	int x=0, y=0, w=0, h=0;
	
	public enum DataType {
		TEXT,
		PICTURE
	};
	
	
	public void setValues(int x, int y, int w, int h) {
		this.x=x;
		this.y=y;
		this.w=w;
		this.h=h;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setType(String type) {
		this.type = DataType.valueOf(type.toUpperCase());
	}
	
	public String getName() {
		return name;
	}

	public String getType() {
		return type.toString();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getW() {
		return w;
	}

	public int getH() {
		return h;
	}

	// overwriten for printing to logs.
	@Override
	public String toString() {
		String result = "Document Item Name: " + name;
		result+="\nDocument Item Type: " + type;
		result+="\nStart Point: " + x + " " + y;
		result+="\nWidth: " + w + " Height: " + h;
		return result;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof DocumentItem) {
			DocumentItem doc = (DocumentItem) o;
			
			if (doc.name.equals(this.name) &&
					doc.x == this.x &&
					doc.y == this.y &&
					doc.w == this.w &&
					doc.h == this.h &&
					doc.type.equals(this.type)) {
				return true;
			}
		}
		return false;
	}
}
