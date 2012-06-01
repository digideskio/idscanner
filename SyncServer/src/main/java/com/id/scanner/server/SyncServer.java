package com.id.scanner.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;

import javax.security.sasl.AuthenticationException;

public class SyncServer extends Thread{
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;

	private boolean _authenticated = false;
	private boolean _weKnowTableName = false;
	private boolean _weKnowTableHeaders = false;

	private ArrayList<String> keys;
	private DatabaseAdapter db;
	

	public SyncServer(Socket clientSoc, DatabaseAdapter db) {
		clientSocket = clientSoc;
		this.db = db;
		start();
	}


	public void run() {
		System.out.println("New Communication Thread Started" + new Date());

		try {
			this.openConnection();

			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				if ( ! isStopMessage(inputLine) ) {
					this.handleMessage(inputLine);
				} else {
					out.println("OK");
					break;
				}
				
				//
				// Let the client know that the message was sent ok
				//
				out.println("OK");
			}

			this.closeConnection();
		} catch (AuthenticationException e) {
			this.closeConnection();

		} catch (IOException e) {
			System.err.println("Problem with Communication Server");
			System.exit(1);
		}
	}

	private void openConnection() throws IOException {
		out = new PrintWriter(clientSocket.getOutputStream(),true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	private void closeConnection(){
		try {
			if (out != null ) {
				out.close();
			}
			if (in != null ) {
				in.close();
			}
			if (clientSocket != null ) {
				clientSocket.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * Handles authentication and message processing.
	 * @param inputLine
	 * @throws AuthenticationException
	 */
	private void handleMessage(String inputLine) throws AuthenticationException {
		if ( ! _authenticated ) {
			String[] credentials = inputLine.split(":");

			LoginService ls = new LoginService();
			if ( credentials != null &&
					credentials.length == 2 &&
					ls.validate(credentials[0], credentials[1])) {
				_authenticated = true;

			} else {
				System.out.println("Invalid credentials! Closing connection.");
				throw new AuthenticationException();
			}
			
			return;
		}
		
		String message = inputLine.trim();
		System.out.println(message);
		if (_weKnowTableName) {
			if (_weKnowTableHeaders) {
				//
				// handle table data
				//
				ArrayList<String> values = new ArrayList<String>();
				
				String[] aux = message.split(";");
				
				for (int  i=1; i<aux.length; i++) {
					values.add(aux[i]);
				}
				if (db.insert(keys, values)) {
					System.out.println("Inserted row into database.");
				}
				
			} else {
				//
				// handle table headers
				//
				keys = new ArrayList<String>();
				String[] aux = message.split(";");
				
				for (int  i=1; i<aux.length; i++) {
					keys.add(aux[i]);
				}
				
				_weKnowTableHeaders = true;
			}
		} else {
			_weKnowTableName = true;
		}
	}



	/** 
	 * Verifies if the message is a signal that the communication has ended.
	 * @param message
	 * @return
	 */
	private boolean isStopMessage(String message) {
		if (message.equalsIgnoreCase("Stop")) {
			return true;
		}
		return false;
	}
}