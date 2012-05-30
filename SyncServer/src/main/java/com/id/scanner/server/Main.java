package com.id.scanner.server;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    private static int port = 1212;

	public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        DatabaseAdapter db = new DatabaseAdapter();
        db.open();
        
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Connection Socket Created");

            while (true) {
                System.out.println("Waiting for Connection");
                new SyncServer(serverSocket.accept(), db);
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + port);
            db.close();
            System.exit(1);
        } catch (Exception e) {
        	serverSocket.close();
            db.close();
        }
        serverSocket.close();
        db.close();
    }

}
