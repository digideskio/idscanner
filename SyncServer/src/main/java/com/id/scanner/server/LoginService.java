package com.id.scanner.server;

import java.util.HashMap;

public class LoginService {
	private HashMap<String,String> credentials; 
	
	public LoginService() {
		credentials = new HashMap<String, String>();
		
		credentials.put("user","pass");
	}
	
	public boolean validate(String user, String pass){
		if (credentials.get(user).equals(pass)) {
			return true;
		}
		return false;
	}
	
}
