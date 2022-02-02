package com.company.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable{
    static int port = 8080;
    ServerSocket listener;
    Socket connection;

    void setUpConnection(){
        try {
            connection = new Socket();
            listener = new ServerSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        public static void main(String[] args) {
        }

    @Override
    public void run() {

    }
}
