package com.company;

public class TestClass {


    public static void main(String[] args) {
        Board b = new Board();
        String[][] board = new String[10][10];

        for( int i = 0 ; i<10; i ++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] ="-";
            }
        }
        board[0][1] = "a";
        board[1][1] = "b";
        System.out.println(b.getAboveWord(2,1,board));
    }
}
