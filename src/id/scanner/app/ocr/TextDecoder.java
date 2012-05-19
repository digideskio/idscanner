package id.scanner.app.ocr;

import java.util.ArrayList;

public class TextDecoder {
 
	static String[] fields = {
			"Tip:",
			"Cod Tara:",
			"Nume:",
			"Prenume:",
			"Seria:",
			"Numarul:",
			"Cetatenia:",
			"Data Nasterii:",
			"Sexul:",
			"Valabilitate:",
			"CNP:"
	};
	
	public static ArrayList<String> getDecodedTect(String text) {
		ArrayList<String> result = new ArrayList<String>();
		
		text.replace(" ", "");
		
		String[] line = text.split("\n");
		
		if (line.length != 2) {
			return null;
		}
		
		line[0].replace("0", "O");
		
		String aux = line[0].substring(0, 2);
		result.add(fields[0]);
		result.add(aux);
		
		aux = line[0].substring(2, 5);
		result.add(fields[1]);
		result.add(aux);
		
		line[0] = line[0].substring(5);
		
		String[] names = line[0].split("<");
		
		result.add(fields[2]);
		result.add(names[0]);
		
		aux = "";
		for (int i=1; i<names.length; i++) {
			if (names[i] != null && ! names[i].equals("")) {
				aux += names[i] + " ";
			}
		}
			
		result.add(fields[3]);
		result.add(aux);
		//
		// Start processing second line. 
		//
		
		names = line[1].split("<");
		
		if (names.length != 2) {
			return null;
		}
		
		aux = "" + names[0].charAt(0); 
		if (names[0].charAt(1) == '0') {
			aux += "D";
		} else {
			aux += names[0].charAt(1);
		}
		
		result.add(fields[4]);
		result.add(aux);
		
		aux = names[0].substring(2);
		result.add(fields[5]);
		result.add(aux);

		aux = names[1].substring(0,4);
		result.add(fields[6]);
		result.add(aux);
		
		String dataN = names[1].substring(4,10);
		result.add(fields[7]);
		result.add(dataN);
		
		String sex = names[1].charAt(11) + "";
		result.add(fields[8]);
		result.add(sex);
		
		aux = names[1].substring(12,18);
		result.add(fields[9]);
		result.add(aux);
		
		aux = names[1].substring(20,26);
		result.add(fields[10]);
		result.add(sex.equals("M")?"1" : "2" + dataN + aux);
		
		return result;
	}

}
