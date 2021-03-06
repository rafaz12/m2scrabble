package com.company.client;

import com.company.view.ReadThread;
import com.company.controllers.WriteThread;

import java.net.*;
import java.io.*;

public class Client {
    public String hostname;
    public int port;
    public String userName;

    public Client(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("WELCOME TO SCRABBLE FOLLOW THE COMMANDS TO PLAY THE GAME IN THE LIST. ENJOY");
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

   public void setUserName(String userName) {
        this.userName = userName;
    }


    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        Client client = new Client(hostname, port);
        client.execute();
    }
}