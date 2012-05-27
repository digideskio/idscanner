package com.id.scanner.synchronize;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.util.Log;

public class ServerSynchronization extends Thread {
	private static final String TAG = ServerSynchronization.class.getSimpleName();
	
	private String ip = "192.168.2.102";
	private int port = 10008;


	public void run() {
		Log.d(TAG, "Synchronization started ");
		
		try {
			Socket s = new Socket(ip, port);

			//outgoing stream redirect to socket
			OutputStream out = s.getOutputStream();

			PrintWriter output = new PrintWriter(out);
			output.println("Hello Android!");
			output.flush();
			
			BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));

			//read line(s)
			
			String st = input.readLine();

			output.println("");
			output.flush();
			
			//Close connection
			output.close();
			input.close();
			
			if (s != null ) {
				s.close();
			}
			
			Log.d(TAG, "Synchronization finished: " + st);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
