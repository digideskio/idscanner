package id.scanner.app.core;

import java.util.ArrayList;

public class IDdata {

	// used for GUI.
	private String[] fields = {
		"Nume:",			// 0
		"Prenume:",			// 1
		"Seria:",			// 2
		"Numarul:",			// 3
		"Cetatenia:",		// 4
		"Data Nasterii:",	// 5
		"Sexul:",			// 6
		"Valabilitate:",	// 7
		"CNP:"				// 8
	};
	private String pictureLocation;
	
	private String nume;
	private String prenume;
	private String seria;
	private String CNP;
	private char sex;
	private int numarul;
	private int cetatenia;
	private int dataNasteri;
	private int valabilitate;
	
	
	/**
	 * Process text and do validations.
	 * @param text
	 * @return
	 */
	public boolean setRawText(String text) {
		text = text.replace(" ", "");
		String[] line = text.split("\n");
		
		if (line.length != 2) {
			return false;
		}
		
		line[0].replace("0", "O");
		line[0] = line[0].substring(5);
		
		String[] names = line[0].split("<");
		
		this.nume = names[0];
		
		this.prenume = "";
		for (int i=1; i<names.length; i++) {
			if (names[i] != null && ! names[i].equals("")) {
				this.prenume += names[i] + " ";
			}
		}
		//
		// Start processing second line. 
		//
		names = line[1].split("<");
		
		if (names.length != 2) {
			return false;
		}
		
		this.seria = "" + names[0].charAt(0); 
		if (names[0].charAt(1) == '0') {
			this.seria += "D";
		} else {
			this.seria += names[0].charAt(1);
		}
		
		this.numarul = Integer.valueOf( names[0].substring(2) );
		
		this.cetatenia = Integer.valueOf( names[1].substring(0,4) );
		
		this.dataNasteri = Integer.valueOf( names[1].substring(4,10) );
		
		this.sex = names[1].charAt(11);
		
		this.valabilitate = Integer.valueOf(names[1].substring(12,18));
		
		this.CNP = (sex == 'M'?"1" : "2" + this.dataNasteri + names[1].substring(20,26));
		
		return true;
	}
	
	public ArrayList<String> getGUIlist() {
		ArrayList<String> result = new ArrayList<String>();
		
		result.add(fields[0]);
		result.add(nume);
		
		result.add(fields[1]);
		result.add(prenume);
		
		result.add(fields[2]);
		result.add(seria);
		
		result.add(fields[3]);
		result.add(numarul + "");
		
		result.add(fields[4]);
		result.add(cetatenia+"");
		
		result.add(fields[5]);
		result.add(dataNasteri+"");
		
		result.add(fields[6]);
		result.add(sex+"");
		
		result.add(fields[7]);
		result.add(valabilitate+"");
		
		result.add(fields[8]);
		result.add(CNP);
		return result;
	}
	

	public String getPictureLocation() {
		return pictureLocation;
	}

	public String getNume() {
		return nume;
	}

	public String getPrenume() {
		return prenume;
	}

	public String getSeria() {
		return seria;
	}

	public String getCNP() {
		return CNP;
	}

	public char getSex() {
		return sex;
	}

	public int getNumarul() {
		return numarul;
	}

	public int getCetatenia() {
		return cetatenia;
	}

	public int getDataNasteri() {
		return dataNasteri;
	}

	public int getValabilitate() {
		return valabilitate;
	}

	public void setPictureFile(String pictureFile) {
		this.pictureLocation = pictureFile;
	}

	@Override
	public String toString() {
		String result = new String("");
		ArrayList<String> strings = getGUIlist();
		
		for (String s: strings) {
			result += s + " ";
		}
		
		result += "Picture file: " + pictureLocation;
		return result;
	}
}



