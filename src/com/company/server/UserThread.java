package com.company.server;

import java.io.*;
import java.net.*;

public class UserThread extends Thread {
    private Socket socket;
    private ChatServer server;
    public PrintWriter writer;
    public BufferedReader reader;
    public BufferedReader serverReader;
    public String  userName = null;
    public int turn;

    public UserThread(Socket socket, ChatServer server) throws IOException {
        this.socket = socket;
        this.server = server;
        InputStream input = socket.getInputStream();
        reader = new BufferedReader(new InputStreamReader(input));
        OutputStream output = socket.getOutputStream();
        writer = new PrintWriter(output, true);
    }

    int userTurn(){
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
            sendMessage("\n\nScrabble MENU\n ANNOUNCE-> Enter name\nSENDCHAT-> message the opponent(s)\n" +
                    "REQUESTGAME->Request join a game.\nMAKEMOVE~WORD|SWAP|SKIP|OVER-> Make move either by entering word, Skipping turn, Swapping letters or Giving up\n\n");

            String clientMessage;

                try {
                    do {
                        clientMessage = reader.readLine();
                        serverMessage = userName + "~" + clientMessage;
                        server.handle(serverMessage, this);
                    } while (!clientMessage.equals("bye"));

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
    void printUsers() {
        if (server.hasUsers()) {
            writer.println("Connected users: " + server.getUserNames());
        } else {
            writer.println("No other users connected");
        }
    }

    /**
     * Sends a message to the client.
     */
    void sendMessage(String message) {
        writer.println(message);
    }
}