package com.company.view;

import com.company.client.Client;

import java.io.*;
import java.net.*;

public class ReadThread extends Thread {
    private BufferedReader reader;
    private Socket socket;
    private Client client;

    public ReadThread(Socket socket, Client client) {
        this.socket = socket;
        this.client = client;

        try {
            InputStream input = socket.getInputStream();
            reader = new BufferedReader(new InputStreamReader(input));
        } catch (IOException ex) {
            System.out.println("Error getting input stream: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void run() {
        while (true) {
            try {
                String response = "";
                response = reader.readLine();
                if (response.equals("OVER")) {
                    System.exit(0);
                }
                System.out.println(response);

            } catch (IOException ex) {
                System.out.println("Error reading from server: " + ex.getMessage());
                ex.printStackTrace();
                break;
            }
        }
    }
}