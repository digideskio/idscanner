package id.scanner.app.xml;


public class Rectangle {
	int a=0, b=0, c=0, d=0;


	public Rectangle(int a, int b, int c, int d) {
		this.a=a;
		this.b=b;
		this.c=c;
		this.d=d;
	}
	
	// overwriten for printing to logs
	@Override
	public String toString() {
		String result = "Rectangle Coordinates: (" + a + ", " + b + ", " + c + ", " + d + ")";
		return result;
	}
	
	// overwriten for testing purposes
	@Override
	public boolean equals(Object o) {
		if (o instanceof Rectangle) {
			Rectangle rect = (Rectangle) o;
			if (rect.a == this.a &&
					rect.b == this.b &&
					rect.c == this.c &&
					rect.d == this.d) {
				return true;
			}
		}
		return false;
	}
}
