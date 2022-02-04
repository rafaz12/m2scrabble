package com.company.model;

import com.company.controllers.Player;
import com.company.controllers.Tiles;
import com.company.view.BoardTUI;

import java.util.Scanner;

public class Game {
    String name1,name2;
    public Player p1, p2;
    public Board board;
    public BoardTUI viewBoard;
    public Tiles t;
    public Game(String name1, String name2) {
        this.name1 = name1;
        this.name2 = name2;
    }

    public void printMenu() {
        System.out.println("Welcome to Scarbble game \r\n"
                + "If you want to play online press '1' \r\n"
                + "If you want to play locally press '2'\r\n"
                + "When you make a move insert the coordinates in the form of (11 for '1,1' , 22 for '2,2' etc) \r\n"
                + "Selecting numbers from (0-14) \r\n"
                + "Score as many points you can to win \r\n"
                + "Game ends whether player inserts 'G' (Give up) or one of the players use its last tile."
                + "Enjoy!");
    }

    public void setUpGame(String name1, String name2) {
        t = new Tiles();
        board = new Board();
        viewBoard = new BoardTUI(board);
        board.setFieldsScore();
        Tiles t = new Tiles();
        t.setTiles();
        p1 = new Player(name1, board);
        p2 = new Player(name2, board);
        p1.setPlayerBag(t.generalTiles);
        p2.setPlayerBag(t.generalTiles);

    }

    boolean isWinner(Player pl1, Player pl2) {
        if (pl1.giveUp) {
            return true;
        } else if (pl2.giveUp) {
            return true;
        } else if (pl1.getPlayerBag().size() == 0 || pl2.getPlayerBag().size() == 0)
            return true;
        else return false;
    }
    public String gameBoard(){
        return viewBoard.printBoard();
    }
    public void gamePlay() {
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Player " + p1.getPlayerName() + "'s turn ");
            String cmd;
            viewBoard.printBoard();
            do {
                System.out.println(p1.getPlayerBag());
                p1.printOptions();
                cmd = sc.next();
            } while (!p1.checkPlayerCommand(cmd));
            if (p1.giveUp || p1.playerBag.size() == 0) {
                isWinner(p1, p2);
                break;
            }
            System.out.println("Player " + p2.getPlayerName() + "'s turn");
            viewBoard.printBoard();
            do {
                System.out.println(p2.getPlayerBag());
                System.out.println("Your score is: " + p2.getScore());
                p2.printOptions();
                cmd = sc.next();
            } while (!p2.checkPlayerCommand(cmd));
            if (p2.giveUp  || p2.playerBag.size() == 0){
                isWinner(p1, p2);
                break;
            }
        } while (!isWinner(p1, p2));

    }

    String announceWinner() {
        if (p1.giveUp || (p2.score > p1.score)) {
            return "The winner is " + p2.getPlayerName() + "with " + p2.getPlayerScore();
        } else if (p2.giveUp || (p1.score > p2.score)) {
            return "The winner is " + p1.getPlayerName() + "with " + p1.getPlayerScore();
        } else {
            return "DRAW";
        }
    }

    void setFinalScore() {
        int bagScore1 = 0, bagScore2 = 0;
        for (int i = 0; i < p1.getPlayerBag().size(); i++) {
            bagScore1 = bagScore1 + p1.getPlayerBag().get(i).getValue();
        }
        for (int i = 0; i < p2.getPlayerBag().size(); i++) {
            bagScore2 = bagScore2 + p2.getPlayerBag().get(i).getValue();
        }

        if (p1.getPlayerBag().size() == 0) {
            p1.score = p1.score + bagScore2;
            p2.score = p2.score - bagScore2;
        }
        if (p2.getPlayerBag().size() == 0) {
            p2.score = p2.score + bagScore1;
            p1.score = p1.score - bagScore1;
        }
    }
    String endGame(){
        setFinalScore();
        return " "+announceWinner();
    }



}
