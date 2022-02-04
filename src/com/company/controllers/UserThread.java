package com.company.controllers;

import com.company.server.Server;

import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    private Socket socket;
    private Server server;
    public PrintWriter writer;
    public BufferedReader reader;
    public String  userName = null;
    public boolean request = false;
    public int turn, numero;

    public UserThread(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
    }

    public int userTurn(){
        return this.turn;
    }

    public void run() {

        printUsers();

        try {
            userName = reader.readLine();
        } catch (IOException e) {
            System.out.println("Error while reading");
        }


            String serverMessage = userName+ " connected";
            server.broadcast(serverMessage, this);

            server.addUserName(userName);
            sendMessage("\n\nScrabble MENU\nSENDCHAT-> message the opponent(s)\n" +
                    "REQUESTGAME->Request join a game.\nMAKEMOVE~WORD|SWAP|SKIP-> Make move either by entering word, Skipping turn, Swapping letters\n" +
                    "OVER to give up\nAn example command for placing a word would be 'MAKEMOVE~WORD~X~0~0~HEY'\n" +
                    "This will print at the X axis at coordinate (0,0) the word 'HEY'\nPlayer who executes the first move" +
                    "must have a tile in (7,7).\n\n");

            String clientMessage;

                try {
                    do {
                        clientMessage = reader.readLine();
                        serverMessage = userName + "~" + clientMessage;
                        server.handle(serverMessage, this);
                    } while (!clientMessage.equals("OVER"));

            server.removeUser(userName, this);
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        serverMessage = userName + " has quitted.";
            server.broadcast(serverMessage, this);


    }

    /**
     * Sends a list of online users to the newly connected user.
     */
   public void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    /**
     * Sends a message to the client.
     */
   public void sendMessage(String message) {
        writer.println(message);
    }
}