package com.company.controllers;

import java.util.ArrayList;
/*
    @invariant all parameters may not be null.
 */
public class IllegalArguments {
    Dictionary dic = new Dictionary();
    public IllegalArguments(){

    }
    /*
        @requires x,y coordinate.
         checks if a coordinate is between 0 - 14.
         @returns true if this is the case.
     */
    public boolean validCoordinate(int x, int y){
        if(x < 15 && x >= 0 && y < 15 && y >= 0)
            return true;
        return false;
    }
    /*
        @requires cooridinates x,y, board.
        returns true if there are letters either north, west, east, or south for at least one letter
        of the word.
     */
    boolean checkSurrounding(int i, int j, String[][] board) {
        return (checkNorth(i, j ,board) || checkEast(i, j ,board)) || checkSouth(i, j , board) || checkWest(i, j, board);
    }
    /*@requires cooridinates x,y, board.
    returns true if there are letters  north for  at least one letter
    of the word.
     */
    boolean checkNorth(int i, int j, String[][] board) {
        if (i == 0) {
            return false;
        }
        return !checkEmpty(i - 1, j, board);
    }
    /*@requires cooridinates x,y, board.
    returns true if there are letters  east for  at least one letter
    of the word.
     */
    boolean checkEast(int i, int j, String board[][]) {
        if (j >= 14) {
            return false;
        }
        return !checkEmpty(i, j + 1, board);
    }

    /*@requires cooridinates x,y, board.
    returns true if there are letters south for  at least one letter
    of the word.
     */
    boolean checkSouth(int i, int j, String board[][]) {
        if (i >= 14) {
            return false;
        }
        return !checkEmpty(i + 1, j , board);
    }
    /*@requires cooridinates x,y, board.
    returns true if there are letters west for  at least one letter
    of the word.
     */
    boolean checkWest(int i, int j, String board[][]) {
        if (j <= 0) {
            return false;
        }
        return !checkEmpty(i, j - 1, board);
    }
    /*@requires cooridinates x,y, coordinate of expansion, word, board.
     is there are checkSurrounding is true for wither y axis or x axis depending on the desired
     coordninate of expansion.
     */
    public boolean validCoordPlacement(int i , int j ,String coord, String word, String [][] board) {
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
    /*
        @requires word.
        returns true if the word is in the dictionary
     */
    public boolean checkDictionary(String word){
        return dic.contains(word);
        }
     /*@requires cooridinates x,y,character as string ,  board.
        returns true if field is empty.
      */
    public boolean checkEmpty(int i , int j, String [][] board ) {
            return board[i][j].equals("-");
        }
            /*@requires cooridinates x,y, board.
        returns true if field is the same letter as the character.
        */
            public boolean checkSameLetter(int i, int j, String ch, String[][] board) {
              boolean sameLetter;
                      if (board[i][j].equals(ch))
                          sameLetter = true;
                      else {
                          sameLetter = false;

                      }
                  return sameLetter;
          }
    /*
        @requires cooridinates x,y,coordinate of expansion, word, board.
        returns true it's possible to enter a word there when (check empty or check same
        letter applies).
     */
    public boolean validWordPlacement( int i , int j , String coord,String word , String [][] board) {
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
    /*
    @requires word and a list of word placed on the board.
    if word exists on the board it returns true.
     */
    public boolean wordExists(String word , ArrayList<String> words){
        if(words.contains(word))
            return true;
        return false;
    }


    public static void main(String[] args) {
    }

}
