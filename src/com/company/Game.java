package com.company;

import javax.imageio.stream.ImageInputStream;
import java.util.Scanner;

public class Game {
    Player p1, p2;
    Board board;
    Tiles t;
    int turn = 0;

    public Game() {

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

    void setUpGame() {
        Scanner sc = new Scanner(System.in);
        String name1 , name2;
        t = new Tiles();
        board = new Board();
        board.setFieldsScore();
        Tiles t = new Tiles();
        t.setTiles();
        System.out.println("Please enter your name: ");
        name1 = sc.next();
        p1 = new Player(name1, board);
        System.out.println("Please enter your name");
        do{
           name2 = sc.next();
           if(name2.equals(name1))
               System.out.println("Please enter different name than "+p1.getPlayerName());
        }while(name2.equals(name1));

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

    void gamePlay() {
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Player " + p1.getPlayerName() + "'s turn ");
            String cmd;
            board.printBoard();
            do {
                System.out.println(p1.getPlayerBag());
                p1.printOptions();
                cmd = sc.next();
                p1.makeMove(cmd);
            } while (!p1.checkPlayerCommand(cmd));
            if (p1.giveUp || p1.playerBag.size() == 0) {
                isWinner(p1, p2);
                break;
            }
            System.out.println("Player " + p2.getPlayerName() + "'s turn");
            board.printBoard();
            do {
                System.out.println(p2.getPlayerBag());
                System.out.println("Your score is: " + p2.getScore());
                p2.printOptions();
                cmd = sc.next();
                p2.makeMove(cmd);
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


    public static void main(String[] args) {
        Game g = new Game();
        g.printMenu();
        g.setUpGame();
        g.gamePlay();
        g.setFinalScore();
        System.out.println(g.announceWinner());
        g.endGame();
    }


}
