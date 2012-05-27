package com.id.scanner.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class SyncServer extends Thread{
	protected Socket clientSocket;

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(10008);
            System.out.println("Connection Socket Created");

            while (true) {
                System.out.println("Waiting for Connection");
                new SyncServer(serverSocket.accept());
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port: 10008.");
            System.exit(1);
        }
        serverSocket.close();
    }

    private SyncServer(Socket clientSoc) {
        clientSocket = clientSoc;
        start();
    }

    public void run() {
        System.out.println("New Communication Thread Started" + new Date());
        try {
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println("Server: " + inputLine);
                out.println(inputLine);

                if (inputLine.trim().equals(""))
                    break;
            }

            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Problem with Communication Server");
            System.exit(1);
        }
    }
}