package com.company.server;

import java.io.*;
import java.net.*;
import java.util.*;

import com.company.model.Game;
import com.company.controllers.UserThread;

public class Server {
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

    public Server(int port) {
        this.port = port;
        turn = 0;
        started = false;
    }

    public void execute() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("WELCOME TO SCRABBLE FOLLOW THE COMMANDS TO PLAY THE GAME IN THE LIST. ENJOY");
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

        Server server = new Server(port);
        server.execute();
    }

    /**
     * Delivers a message from one user to others (broadcasting)
     */
    public void broadcast(String message, UserThread excludeUser) {
        for (UserThread aUser : userThreads) {
            if (aUser != excludeUser) {
                aUser.sendMessage(message);
            }
        }
    }

    // UserThread user, is the user that send this message
    // String message, is the message send by user
    // broadcast, exclude user to prevent the command to be send back to the user
    public void handle(String message, UserThread user) {
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
            case "OVER":
                if (user.numero == 1) {
                    onlineGame.p1.score = -1;
                    message = "The winner is : "+onlineGame.p2.getPlayerName()+" with score: "+onlineGame.p2.score;
                    broadcast(message,null);
                    broadcast("OVER", null);
                    break;
                }
                else {
                    onlineGame.p2.score = -1;
                    message = "The winner is : " + onlineGame.p1.getPlayerName() + " with score: " + onlineGame.p1.score;
                    broadcast(message, null);
                    broadcast("OVER", null);
                    break;
                }

            case "MAKEMOVE":
                if (user.userTurn() != 1) {
                user.sendMessage("It is not your turn.");
                break;
            }
                switch(digest[2]) {
                    case "SKIP":
                        message = "Turn skipped. ";
                        user.sendMessage(message);
                        for (int i = 0; i < userThreadsArray.size(); i++) {
                            if (userThreadsArray.get(i) == user) {
                                userThreadsArray.get(i).turn = 2;
                            } else userThreadsArray.get(i).turn = 1;
                        }
                        if (userThreadsArray.get(0).turn == 1) {
                            message = "It is " + userThreadsArray.get(0).userName + " turn";
                            user.sendMessage(message);
                            message = "It is your turn";
                            broadcast(message , user);
                            break;
                        }
                        else {
                            message = "It is " + userThreadsArray.get(1).userName + " turn";
                            user.sendMessage(message);
                            message = "It is your turn";
                            broadcast(message, user);
                            break;
                        }

                    case "SWAP":
                        if(digest.length != 4){
                            user.sendMessage("UNKNOWN COMMAND");
                            break;
                        }
                        if (user.numero == 1) {
                            onlineGame.p1.makeMove("SW", digest[3], null,null,null,null);
                            if (!onlineGame.p1.letterExist || !onlineGame.p1.tilesExist) {
                                user.sendMessage("You don't have such tiles in your bag.\r\nMake another move");
                                break;
                            } else {
                                message = "Your tiles are now" + onlineGame.p1.getPlayerBag();
                                user.sendMessage(message);
                                for (int i = 0; i < userThreadsArray.size(); i++) {
                                    if (userThreadsArray.get(i) == user) {
                                        userThreadsArray.get(i).turn = 2;
                                    } else userThreadsArray.get(i).turn = 1;
                                }
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
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
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
                            }
                        }
                    case "WORD":
                        if(digest.length != 7) {
                            user.sendMessage("UNKNOWN COMMAND");
                            break;
                        }
                        if (user.numero == 1) {
                            if(onlineGame.board.turn == 0 )
                                onlineGame.p1.enterFirstWord(digest[3], digest[4], digest[5], digest[6].toUpperCase());
                            else
                                onlineGame.p1.enterWord(digest[3], digest[4], digest[5], digest[6].toUpperCase());
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
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
                            }
                            else{
                                message = onlineGame.viewBoard.printBoard();
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
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
                            }
                        }
                        else if (user.numero == 2){
                            if(onlineGame.board.turn == 0)
                                onlineGame.p2.enterFirstWord(digest[3], digest[4], digest[5], digest[6].toUpperCase());
                            else
                                onlineGame.p2.enterWord(digest[3], digest[4], digest[5], digest[6].toUpperCase());
                            if(!digest[3].equalsIgnoreCase("x") && !digest[3].equalsIgnoreCase("y")) {
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
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
                            }
                            else{
                                message = onlineGame.viewBoard.printBoard();
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
                                if (userThreadsArray.get(0).turn == 1) {
                                    message = "It is " + userThreadsArray.get(0).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message , user);
                                    break;
                                }
                                else {
                                    message = "It is " + userThreadsArray.get(1).userName + " turn";
                                    user.sendMessage(message);
                                    message = "It is your turn";
                                    broadcast(message, user);
                                    break;
                                }
                        }
                        }
                }
                }
        }




    /**
     * Stores username of the newly connected client.
     */
    public void addUserName(String userName) {
        userNames.add(userName);
    }

    /**
     * When a client is disconneted, removes the associated username and UserThread
     */
    public void removeUser(String userName, UserThread aUser) {
        boolean removed = userNames.remove(userName);
        if (removed) {
            userThreads.remove(aUser);
            System.out.println("The user " + userName + " quitted");
        }
    }

    public Set<String> getUserNames() {
        return this.userNames;
    }

    /**
     * Returns true if there are other users connected (not count the currently connected user)
     */
    public boolean hasUsers() {
        return !this.userNames.isEmpty();
    }


    String clearScreen() {
        return "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n";
    }
}