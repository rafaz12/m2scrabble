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
    private ArrayList<UserThread> userThreadsArray = new ArrayList<>();
    private ArrayList<Integer> userTurns = new ArrayList<>();
    public boolean isStarted;

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
                userThreadsArray.add(newUser);
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
        System.out.println(digest.length);
        switch (digest[1]){
            case "SENDCHAT":
                message = user.userName + " : " + digest[2];
                System.out.println("Command was issued: " + digest[2]);
                broadcast(message, user);
                user.sendMessage(message);
                break;
            case "REQUESTGAME":
                if(user.request){
                    message = "You already requested a game. ";
                    user.sendMessage(message);
                    break;
                }
                System.out.println(user.userName+ " requested a game.");
                req++;
                if(req == 1){
                    message = "Waiting for another player. . . ";
                    user.numero = 1;
                    user.request = true;
                    user.turn = 1;
                    userTurns.add(user.turn);
                    user.sendMessage(message);
                    break;
                }
                if(req == 2) {
                    user.request = true;
                    user.numero = 2;
                    user.turn = 2;
                    userTurns.add(user.turn);
                    onlineGame = new Game(userNames.toArray()[0].toString(),userNames.toArray()[1].toString());
                    onlineGame.setUpGame(userNames.toArray()[0].toString(), userNames.toArray()[1].toString());
                    message = "The game between "+userNames.toArray()[0]+" and "+userNames.toArray()[1]+" begins";
                    isStarted = true;
                    broadcast(message , null);
                    message = onlineGame.gameBoard();
                    broadcast(message , null);
                    message = "Your tiles are : "+onlineGame.p1.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p1.score;
                    broadcast(message , user);
                    message = "Your tiles are : "+onlineGame.p2.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p2.score;;
                    user.sendMessage(message);
                    if(userThreadsArray.get(0).turn == 1 )
                        message = "It is "+userThreadsArray.get(0).userName+" turn";
                    else
                        message = "It is "+userThreadsArray.get(1).userName+" turn";
                    broadcast(message, null);
                    break;
                }
            case "MAKEMOVE":
                if (user.userTurn() != 1) {
                user.sendMessage("It is not your turn.");
                break;
            }
                switch(digest[2]) {
                    case "OVER":

                        user.sendMessage("Game over, winner is");

                    case "SKIP":
                        message = "Turn skipped. ";
                        user.sendMessage(message);
                        for (int i = 0; i < userThreadsArray.size(); i++) {
                            if (userThreadsArray.get(i) == user) {
                                userThreadsArray.get(i).turn = 2;
                            } else userThreadsArray.get(i).turn = 1;
                        }
                        user.sendMessage(message);
                        if (userThreadsArray.get(0).turn == 1)
                            message = "It is " + userThreadsArray.get(0).userName + " turn";
                        else
                            message = "It is " + userThreadsArray.get(1).userName + " turn";
                        broadcast(message, null);


                    case "SWAP":
                        if (user.numero == 1) {
                            onlineGame.p1.makeMove("SW", digest[3], null,null,null,null);
                            if (!onlineGame.p1.letterExist || !onlineGame.p1.tilesExist) {
                                user.sendMessage("You don't have such tiles in your bag.\r\nMake another move");
                                break;
                            } else {
                                message = "Your tiles are now" + onlineGame.p1.getPlayerBag() + "\r\nYour score is: " + onlineGame.p1.score;
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                user.sendMessage(message);
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                            }
                        }
                        if (user.numero == 2) {
                            onlineGame.p2.makeMove("SW", digest[3], null , null , null, null );
                            if (!onlineGame.p2.letterExist || !onlineGame.p2.tilesExist) {
                                user.sendMessage("You don't have such tiles in your bag.\r\n" + "Place another move");
                                break;
                            } else {
                                message = "Your tiles are now" + onlineGame.p2.getPlayerBag();
                                user.sendMessage(message);
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                            }
                        }
                    case "WORD":
                        if (user.numero == 1) {
                            if(onlineGame.board.turn == 0 )
                                onlineGame.p1.enterFirstWord(digest[3], digest[4], digest[5], digest[6]);
                            else
                                onlineGame.p1.enterWord(digest[3], digest[4], digest[5], digest[6]);
                            if(!digest[3].equalsIgnoreCase("x") && !digest[3].equalsIgnoreCase("y")) {
                                message = "Wrong coordinate for expansion. It must have been either 'x' or 'y'\r\nTry another move";
                            user.sendMessage(message);
                            break;
                            }
                            else if (!onlineGame.p1.ill.validCoordinate(Integer.parseInt(digest[4]),Integer.parseInt(digest[5]))) {
                                message = "Coordinates shall be between '0-14'\r\ntry another move";
                                user.sendMessage(message);
                                break;
                            }
                            else if(!onlineGame.p1.validPlacement){
                                message = "Invalid move";
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                user.sendMessage(message);
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                            }
                            else{
                                message = onlineGame.gameBoard();
                                broadcast(message, null);
                                message = "Your tiles are : "+onlineGame.p2.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p2.score;
                                broadcast(message , user);
                                message = "Your tiles are : "+onlineGame.p1.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p1.score;;
                                user.sendMessage(message);
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                            }
                        }
                        else if (user.numero == 2){
                            if(onlineGame.board.turn == 0)
                                onlineGame.p2.enterFirstWord(digest[3], digest[4], digest[5], digest[6]);
                            else
                                onlineGame.p2.enterWord(digest[3], digest[4], digest[5], digest[6]);
                            if(!digest[3].equals("x") && !digest[3].equals("y")) {
                                message = "Wrong coordinate for expansion. It must have been either 'x' or 'y'\r\nTry another move";
                                user.sendMessage(message);
                                break;
                            }
                            else if (!onlineGame.p2.ill.validCoordinate(Integer.parseInt(digest[4]),Integer.parseInt(digest[5]))) {
                                message = "Coordinates shall be between '0-14'\r\ntry another move";
                                user.sendMessage(message);
                                break;
                            }
                            else if(!onlineGame.p2.validPlacement){
                                message = "Invalid move";
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                user.sendMessage(message);
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                            }
                            else{
                                message = onlineGame.gameBoard();
                                broadcast(message, null);
                                message = "Your tiles are : "+onlineGame.p1.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p1.score;
                                broadcast(message , user);
                                message = "Your tiles are : "+onlineGame.p2.getPlayerBag().toString()+"\r\nYour score is "+onlineGame.p2.score;;
                                user.sendMessage(message);
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                if (userThreadsArray.get(0).turn == 1)
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                else
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                broadcast(message, null);
                                break;
                        }
                        }
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