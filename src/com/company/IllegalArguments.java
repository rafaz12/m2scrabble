package com.company;

import java.util.ArrayList;

public class IllegalArguments {
    Dictionary dic = new Dictionary();
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
    boolean isFirstTurn(){
        return true;
    }

    boolean checkSurrounding(int i, int j, String[][] board) {
        return (checkNorth(i, j ,board) || checkEast(i, j ,board)) || checkSouth(i, j , board) || checkWest(i, j, board);
    }

    boolean checkNorth(int i, int j, String[][] board) {
        if (i == 0) {
            return false;
        }
        return !checkEmpty(i - 1, j, board);
    }

    boolean checkEast(int i, int j, String board[][]) {
        if (j >= 14) {
            return false;
        }
        return !checkEmpty(i, j + 1, board);
    }

    boolean checkSouth(int i, int j, String board[][]) {
        if (i >= 14) {
            return false;
        }
        return !checkEmpty(i + 1, j , board);
    }

    boolean checkWest(int i, int j, String board[][]) {
        if (j <= 0) {
            return false;
        }
        return !checkEmpty(i, j - 1, board);
    }
    boolean validCoordPlacement(int i , int j ,String coord, String word, String [][] board) {
        boolean valid = false;
        for (int x = 0; x < word.length(); x++) {
            if (coord.equals("x")) {
                if (checkSurrounding(i, j + x, board)) {
                    valid = true;
                    break;
                }

            } else {
                if (checkSurrounding(i + x, j, board)) {
                    valid = true;
                    break;
                }

            }
        }
        return valid;
    }
        boolean checkDictionary(String word){
        return dic.contains(word);
        }

        boolean checkEmpty(int i , int j, String [][] board ) {
            return board[i][j].equals("-");
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
                    if( checkSameLetter(i ,j +k ,String.valueOf(word.charAt(k)),board)|| checkEmpty( i ,j + k , board) )
                        valid = true;
                    else {
                        valid = false;
                        break;
                    }
                }
            }
            else{
                for(int k = 0 ; k< word.length(); k++){
                    if( checkSameLetter(i + k ,j  ,String.valueOf(word.charAt(k)),board) || checkEmpty( i + k,j , board))
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
                board[i][j] ="-";
            }
        }


        board[0][1] = "a";

        System.out.println(ill.validWordPlacement(0,0,"x","Ta", board));
    }

}
