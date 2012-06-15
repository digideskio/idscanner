package com.id.scanner.core;

import id.scanner.app.R;

import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

public class IDdata {
	private static final String TAG = IDdata.class.getSimpleName();
	
	// serii valide de buletin
	private String[] seriiBuletin;
		
	// used for GUI.
	private final String[] fields = {
			"Nume",			// 0
			"Prenume",			// 1
			"Seria",			// 2
			"Numarul",			// 3
			"Cetatenia",		// 4
			"Data Nasterii",	// 5
			"Sexul",			// 6
			"Valabilitate",	// 7
			"CNP"				// 8
	};
	public final int NR_OF_FIELDS = fields.length;
	
	private final String[] valid = { "0", "0", "0", "0", "0", "0", "0","0", "0"};

	private String pictureLocation;

	private String nume;
	private String prenume;
	private String seria;
	private String CNP;
	private char sex;
	private String numarul;
	private String cetatenia;
	private String dataNasteri;
	private String valabilitate;

	
	public IDdata(Context context){
		seriiBuletin = context.getResources().getStringArray(R.array.seriiBuletin);
	}
	

	/**
	 * Process text and do validations.
	 * @param text
	 * @return
	 */
	public boolean setRawText(String text) {
		try {
			text = text.replace(" ", "");
			String[] line = text.split("\n");

			if (line.length != 2) {
				return false;
			}

			line[0] = replaceWithChars(line[0]);
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

			this.numarul = replaceWithNumbers( names[0].substring(2) );
			this.cetatenia = replaceWithNumbers( names[1].substring(0,4) ) ;
			this.dataNasteri = replaceWithNumbers( names[1].substring(4,10) ) ;
			this.sex = names[1].charAt(11);
			if (sex == 'H') {
				sex = 'M';
			}
			
			this.valabilitate = replaceWithNumbers(names[1].substring(12,18));

			this.CNP = sex == 'F'?"2" : "1";
			this.CNP += this.dataNasteri + replaceWithNumbers( names[1].substring(20,26));
			
			this.validate();

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Validate that the data read is correct.
	 */
	private void validate() {
		// just in case;
		for (int i=0;i<NR_OF_FIELDS; i++) {
			valid[i] = "0";
		}
		//
		// Validate name and surname:
		//
		if ( ! this.isUpperAlpha(nume)) {
			fields[0] = "1";
		}
		
		if ( ! this.isUpperAlphaWithSpace(nume)) {
			fields[1] = "1";
		}
		//
		// validate seria
		//
		valid[2] = "1";							// presupun ca seria este invalida, si incerc sa o validez
		for (int i=0; i<seriiBuletin.length; i++) {
			if (seriiBuletin[i].equals(seria)) {
				valid[2] = "0";
			}
		}
		//
		// validate numarul
		//
		try {
			valid[3] = "1";
			int nr = Integer.valueOf(numarul);
			if ( nr >= 100000 && nr <= 999999 ) {
				valid[3] = "0";
			}
		} catch (Exception e) {
			Log.d(TAG, "Error validating seria.");
		}

		//
		// validate sex
		//
		if ( ! (sex == 'M' || sex == 'F')) {
			valid[6] = "1";
		}
		//
		// Validate valabilitate
		//
		if ( ! isNumber(this.valabilitate)) {
			valid[7] = "1";
		}
		//
		// validate CNP
		//
		if ( ! (isNumber(CNP) && isValidCNP(CNP))) {
			valid[8] = "1";
		}
		
	}

	private boolean isValidCNP(String cnp) {
		if ( cnp.length() == 13 && cnp.charAt(0) != '0') {
			if ( ! isNumber(cnp)) {
				cnp = replaceWithNumbers(cnp);
			}
			if ( isNumber(cnp)) {
				try {
					//int aa = Integer.valueOf(cnp.substring(1,3));	// any year is valid.
					int ll = Integer.valueOf(cnp.substring(3,5));
					int zz = Integer.valueOf(cnp.substring(5,7));
					
					if (ll <=12 && zz <= 31) {
						int jj = Integer.valueOf(cnp.substring(7,9));
						
						if (jj<=52)
							//
							// Calculate control number?
							//
							return true;
					}
				} catch (Exception e) {
					Log.d(TAG, "Error trying to process CNP.");
					return false;
				}
			}
			
		}
		return false;
	}

	private String replaceWithNumbers(String nr) {
		nr = nr.replace("S", "5");
		nr = nr.replace("O", "0");
		nr = nr.replace("D", "0");
		nr = nr.replace("I", "1");
		nr = nr.replace("B", "8");
		return nr;
	}
	
	private String replaceWithChars(String nr) {
		nr = nr.replace("5" , "S");
		nr = nr.replace("0" , "O");
		nr = nr.replace("1" , "I");
		nr = nr.replace("8" , "B");
		return nr;
	}

	/**
	 * @param string
	 * @return			true if the string contains chars between 0 and 9.
	 */
	private boolean isNumber(String string) {
		for (int i=0;i<string.length(); i++) {
			if ( ! ((string.charAt(i) >= '0' && string.charAt(i) <= '9' ))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param nume2
	 * @return		true if the string only contains upper case letters and spaces.
	 */
	private boolean isUpperAlphaWithSpace(String nume2) {
		for (int i=0;i<nume2.length(); i++) {
			if ( ! Character.isUpperCase(nume2.charAt(i)) && Character.isWhitespace(nume2.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param nume2
	 * @return			true if the string only contains upper case letters.
	 */
	private boolean isUpperAlpha(String nume2) {
		for (int i=0;i<nume2.length(); i++) {
			if ( ! Character.isUpperCase(nume2.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @return	A list containing label name and value that will be shown in the 
	 * GUI, plus a number: 0 = valid, 1 = invalid, so make it red.
	 */
	public ArrayList<String> getGUIlist() {
		ArrayList<String> result = new ArrayList<String>();

		result.add(fields[0]);
		result.add(nume);
		result.add(valid[0]);

		result.add(fields[1]);
		result.add(prenume);
		result.add(valid[1]);

		result.add(fields[2]);
		result.add(seria);
		result.add(valid[2]);

		result.add(fields[3]);
		result.add(numarul + "");
		result.add(valid[3]);
		
		result.add(fields[4]);
		result.add(cetatenia+"");
		result.add(valid[4]);
		
		result.add(fields[5]);
		result.add(dataNasteri+"");
		result.add(valid[5]);
		
		result.add(fields[6]);
		result.add(sex+"");
		result.add(valid[6]);
		
		result.add(fields[7]);
		result.add(valabilitate+"");
		result.add(valid[7]);
		
		result.add(fields[8]);
		result.add(CNP);
		result.add(valid[8]);
		
		return result;
	}

	public void setField(String value, int i) {
		try {
			switch (i){
			case 0:
				this.nume = value;
				break;
			case 1:
				this.prenume = value;
				break;
			case 2:
				this.seria = value;
				break;
			case 3:
				this.numarul = value;
				break;
			case 4:
				this.cetatenia = value;
				break;
			case 5:
				this.dataNasteri= value;
				break;
			case 6:
				this.sex = value.charAt(0);
				break;
			case 7:
				this.valabilitate = value;
				break;
			case 8:
				this.CNP = value;
				break;
			}
		} catch (Exception e) {
			Log.d(TAG, "Some values inserted in the GUI are incorect");
		}
		
	}

	public String getPictureLocation() {return pictureLocation;}
	public String getNume() {return nume;}
	public String getPrenume() {return prenume;}
	public String getSeria() {return seria;}
	public String getCNP() {return CNP;}
	public char getSex() {return sex;}
	public String getNumarul() {return numarul;}
	public String getCetatenia() {return cetatenia;}
	public String getDataNasteri() {return dataNasteri;}
	public String getValabilitate() {return valabilitate;}
	public void setPictureFile(String pictureFile) {this.pictureLocation = pictureFile;}
	public int getNrOfFields() { return NR_OF_FIELDS; }

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



