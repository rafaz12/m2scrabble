package com.company.model;

import com.company.server.Server;

public class Scrabble {
    public Scrabble(){
        System.out.println("This method executes only the server." +
                "To play the game you should individually run Client main method twice\r\n" +
                "once with Client configuration and one with ChatClient2 connfiguration");
        Server server =  new Server(3000);
        server.execute();

    }

    public static void main(String[] args) {
        new Scrabble();

    }
}
