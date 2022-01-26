package com.company;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public class IllegalArguments {
    public IllegalArguments(){

    }
    public Boolean isInteger(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    boolean validCoordinate(int x, int y){
        if(x < 15 && x >= 0 && y < 15 && y >= 0)
            return true;
        return false;
    }

    boolean checkSurrounding(int i, int j, String[][] board) {
        return (checkNorth(i, j ,board) || checkEast(i, j ,board)) || checkSouth(i, j , board) || checkWest(i, j, board);
    }

    boolean checkNorth(int i, int j, String[][] board) {
        if (i == 0) {
            return false;
        }
        return !checkNoLetter(i - 1, j, board);
    }

    boolean checkEast(int i, int j, String board[][]) {
        if (j >= 14) {
            return false;
        }
        return !checkNoLetter(i, j + 1, board);
    }

    boolean checkSouth(int i, int j, String board[][]) {
        if (i >= 14) {
            return false;
        }
        return !checkNoLetter(i + 1, j , board);
    }

    boolean checkWest(int i, int j, String board[][]) {
        if (j <= 0) {
            return false;
        }
        return !checkNoLetter(i, j - 1, board);
    }
    boolean validCoordPlacement(int i , int j ,String coord, String word, String [][] board) {
        boolean valid = false;
        for (int x = 0; x < word.length(); x++) {
            if (coord.equals("x")) {
                if (checkSurrounding(i, j + x, board))
                    valid = true;
                break;

            } else {
                if (checkSurrounding(i + x, j, board)) {
                    valid = true;
                    break;
                }

            }
        }
        return valid;
    }
        String reverseWord(String word){
            StringBuilder reverseWord = new StringBuilder();
            reverseWord.append(word);
            reverseWord.reverse();
            return reverseWord.toString();
        }

        String getRightWord(int i, int j ,String word, String[][] board){
            boolean empty = false;
            String rightWord ="";
            int rightCoord = word.length() + j;
            while(!empty) {
                empty = true;
                if (checkNoLetter(i, rightCoord, board)) {
                    empty = false;
                    rightWord = rightWord+board[i][rightCoord];
                    rightCoord++;
                }
            }
            return rightWord;
        }
        String getLeftWord(int i, int j ,String[][] board){
        boolean empty = false;
            String leftWord ="";
            int leftCoord = j - 1;
            while(!empty) {
                empty = true;
                if (checkNoLetter(i, leftCoord, board)) {
                    empty = false;
                    leftWord = leftWord+board[i][leftCoord];
                    leftCoord--;
                }
            }
            return reverseWord(leftWord);
        }

        String getAboveWord(int i, int j ,String[][] board){
            boolean empty = false;
            String aboveWord ="";
            int aboveCoord = i - 1;
            while(!empty) {
                empty = true;
                if (checkNoLetter(aboveCoord, j, board)) {
                    empty = false;
                    aboveWord = aboveWord+board[aboveCoord][j];
                    aboveCoord--;
                }
            }
            return reverseWord(aboveWord);
        }
        String getBelowWord(int i, int j,String word ,String[][] board){
            boolean empty = false;
            String belowWord ="";
            int belowCoord = word.length() + i;
            while(!empty) {
                empty = true;
                if (checkNoLetter(belowCoord, j, board)) {
                    empty = false;
                    belowWord = belowWord+board[belowCoord][j];
                    belowCoord++;
                }
            }
            return belowWord;
        }
        boolean checkNoLetter(int i , int j, String [][] board ) {
            return board[i][j].equals("- ");
        }
          boolean checkSameLetter(int i , int j , String ch , String [][] board ) {
              boolean sameLetter;
                      if (board[i][j].equals(ch))
                          sameLetter = true;
                      else {
                          sameLetter = false;

                      }
                  return sameLetter;
          }
        boolean validWordPlacement( int i , int j , String coord,String word , String [][] board) {
            boolean valid = false;
            if(coord.equals("x")){
                for(int k = 0 ; k< word.length(); k++){
                    if(checkNoLetter( i ,j + k , board) || checkSameLetter(i ,j +k ,String.valueOf(word.charAt(k)),board))
                        valid = true;
                    else {
                        valid = false;
                        break;
                    }
                }
            }
            else{
                for(int k = 0 ; k< word.length(); k++){
                    if(checkNoLetter( i + k,j , board) || checkSameLetter(i + k ,j  ,String.valueOf(word.charAt(k)),board))
                        valid = true;
                    else {
                        valid = false;
                        break;
                    }
                }
            }

            return valid;
        }

    boolean wordExists(String word , ArrayList<String> words){
        if(words.contains(word))
            return true;
        return false;
    }
    /*
    Checks if there exist the desired tiles in the bag.
     */


    public static void main(String[] args) {
        IllegalArguments ill = new IllegalArguments();
        String[][] board = new String[10][10];

        for( int i = 0 ; i<10; i ++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] ="- ";
            }
        }

        board[0][0] = "T";

        System.out.println(ill.validWordPlacement(0,0,"x","Takis", board));
    }

}
