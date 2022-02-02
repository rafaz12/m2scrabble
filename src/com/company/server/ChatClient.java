package com.company.server;

import java.net.*;
import java.io.*;

public class ChatClient {
    public String hostname;
    public int port;
    public String userName;

    public ChatClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public void execute() {
        try {
            Socket socket = new Socket(hostname, port);
            System.out.println("  ____        _   _   _           _     _       \n |  _ \\      | | | | | |         | |   (_)      \n | |_) | __ _| |_| |_| | ___  ___| |__  _ _ __  \n |  _ < / _` | __| __| |/ _ \\/ __| '_ \\| | '_ \\ \n | |_) | (_| | |_| |_| |  __/\\__ \\ | | | | |_) |\n |____/ \\__,_|\\__|\\__|_|\\___||___/_| |_|_| .__/ \n                                         | |    \n                                         |_|     ");
            System.out.println("Connected to the chat server");

            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();

        } catch (UnknownHostException ex) {
            System.out.println("Server not found: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O Error: " + ex.getMessage());
        }

    }

    void setUserName(String userName) {
        this.userName = userName;
    }


    public static void main(String[] args) {
        if (args.length < 2) return;

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);

        ChatClient client = new ChatClient(hostname, port);
        client.execute();
    }
}