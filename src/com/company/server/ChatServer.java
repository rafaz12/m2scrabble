package com.company.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.company.Game;
import com.company.server.UserThread;

public class ChatServer {
    // game functions
    int turn;
    boolean started;


    public Game onlineGame ;
    private int port , req = 0;
    public Set<String> userNames = new HashSet<>();
    private Set<UserThread> userThreads = new HashSet<>();
    private ArrayList<String> userNamesArray = new ArrayList<>();
    public ServerSocket serverSocket;
    private ArrayList<UserThread> userThreadsArray = new ArrayList<>();

    public ChatServer(int port) {
        this.port = port;
        turn = 0;
        started = false;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("  ____        _   _   _           _     _       \n |  _ \\      | | | | | |         | |   (_)      \n | |_) | __ _| |_| |_| | ___  ___| |__  _ _ __  \n |  _ < / _` | __| __| |/ _ \\/ __| '_ \\| | '_ \\ \n | |_) | (_| | |_| |_| |  __/\\__ \\ | | | | |_) |\n |____/ \\__,_|\\__|\\__|_|\\___||___/_| |_|_| .__/ \n                                         | |    \n                                         |_|     ");
            System.out.println("Chat Server is listening on port " + port);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected");

                UserThread newUser = new UserThread(socket, this);
                userNamesArray.add(newUser.userName);
                userThreads.add(newUser);
                newUser.start();
            }



        } catch (IOException ex) {
            System.out.println("Error in the server: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntax: java ChatServer <port-number>");
            System.exit(0);
        }

        int port = Integer.parseInt(args[0]);

        ChatServer server = new ChatServer(port);
        server.execute();
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    // UserThread user, is the user that send this message
    // String message, is the message send by user
    // broadcast, exclude user to prevent the command to be send back to the user
    void handle(String message, UserThread user) {
        String [] digest;
        digest = message.split("~");
        switch (digest[1]){
            case "SENDCHAT":
                message = user.userName+" : "+ digest[2];
                System.out.println("Command was issued: " + digest[2]);
                broadcast(message, user);
                user.sendMessage(message);
                break;
            case "REQUESTGAME":
                System.out.println(user.userName+ " requested a game.");
                req++;
                if(req == 1){
                    message = "Waiting for another player. . . ";
                    user.turn = 1;
                    user.sendMessage(message);
                    break;
                }
                if(req == 2) {
                    user.turn = 2;
                    onlineGame = new Game(userNames.toArray()[0].toString(),userNames.toArray()[1].toString());
                    onlineGame.setUpGame(userNames.toArray()[0].toString(), userNames.toArray()[1].toString());
                    message = "The game between "+userNames.toArray()[0]+" and "+userNames.toArray()[1]+" begins";
                    broadcast(message , null);
                    break;
                }
                }
        }




    /**
     * Stores username of the newly connected client.
     */
    void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    boolean hasUsers() {
        return !this.userNames.isEmpty();
    }


    String clearScreen() {
        return "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    }
}