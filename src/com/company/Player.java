package com.company;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;


public class Player {
    IllegalArguments ill = new IllegalArguments();
    Tiles t = new Tiles();
    Board b = new Board();
    String name;
    public Player(String name) {
        this.name = name;
    }
    void setPlayerName(String name){}

    String getPlayerName(){
        return name;
    }
    void addFirstWord(){
        b.enterFirstWord();
    }
    void addNewWord(){
        b.enterWord();
    }
    ArrayList<Map.Entry<Character, Integer>> getPlayerTiles(){
       return t.getTiles();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter name: ");
        String name = sc.next();
        Player player = new Player(name);
        player.setPlayerName(name);
        System.out.println(player.getPlayerName() + " plays ");
        player.addFirstWord();

        player.addNewWord();

    }
}
