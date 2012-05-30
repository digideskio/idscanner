package com.id.scanner.synchronize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.auth.AuthenticationException;

import com.id.scanner.core.ProfileManager;
import com.id.scanner.database.DatabaseAdapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class ServerSynchronization extends Thread {
	private static final String TAG = ServerSynchronization.class.getSimpleName();

	private Socket socket;
	private PrintWriter output;
	private BufferedReader input;
	
	private DatabaseAdapter db;
	private int lastIndex;
	
	private String ip = "192.168.2.100";
	private int port = 1212;


	public ServerSynchronization(Context context) {
		ProfileManager pm= ProfileManager.getInstance();
		this.ip = pm.getIp();
		this.port = pm.getPort();
		
		db = new DatabaseAdapter(context);
		db.open();
		
		String profileName = ProfileManager.getInstance().getProfileName();
		lastIndex = db.getSyncIndex(profileName);
	}
	
	
	public void run() {
		Log.d(TAG, "Synchronization started ");
		
		try {
			this.openConnection();
			
			//
			// Write the messages according to the protocol.
			//
			this.authenticate();
			this.sendTableName();
			this.sendTableContent();
			this.sendStop();
			
			this.closeConnection();
			Log.d(TAG, "Synchronization finished.");
			
			db.close();
		} catch (UnknownHostException e) {
			db.close();
			e.printStackTrace();
		} catch (IOException e) {
			db.close();
			e.printStackTrace();
		} catch (AuthenticationException e) {
			db.close();
			e.printStackTrace();
		} catch (Exception e) {
			db.close();
			e.printStackTrace();
		}
	}

	private void sendTableContent() throws AuthenticationException, IOException {
		Cursor c = db.getDataTable(lastIndex);

		if (c != null && c.moveToFirst()) {
			this.sendTableHeaders(c.getColumnNames());
			
			int columnCount = c.getColumnCount();
			
			String rowNr = "0", row;
			while ( ! c.isAfterLast()) {
				rowNr = c.getString(0);
				row = rowNr;
				
				for (int i=1; i<columnCount; i++ ) {
					row += ";" + c.getString(i);
				}
				this.writeMessage(row);
				
				c.moveToNext();
			}
			//
			// update the index in the database
			//
			db.updateSynchronizationIndex(Integer.valueOf(rowNr));
		}
		
	}

	private void sendTableHeaders(String[] names) throws AuthenticationException, IOException {
		String headers = names[0];
		for (int i=1; i< names.length; i++) {
			headers += ";" + names[i];
		}
		this.writeMessage(headers);			
	}

	private void sendTableName() throws AuthenticationException, IOException {
		String name = db.getProfileTableName();
		this.writeMessage(name);
	}

	
	private void writeMessage(String message) throws IOException, AuthenticationException {
		output.println(message);
		output.flush();
		
		String st = input.readLine();
		if ( st == null || ! st.equalsIgnoreCase("OK") ) {
			throw new AuthenticationException("Error writing message: " + message);
		}
	}


	private void authenticate() throws IOException, AuthenticationException {
		this.writeMessage("user:pass");
	}

	private void openConnection() throws UnknownHostException, IOException {
		socket = new Socket(ip, port);
		output = new PrintWriter(socket.getOutputStream());
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	private void closeConnection() throws IOException {
		if (input != null) {
			input.close();
		}
		if (output != null) {
			output.close();
		}
		if (socket != null) {
			socket.close();
		}
	}
	
	private void sendStop() throws AuthenticationException, IOException {
		try {
			this.writeMessage("stop");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}




