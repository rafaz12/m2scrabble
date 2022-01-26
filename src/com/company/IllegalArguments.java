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
    boolean validCoordPlacement(int i , int j ,String coord, String word, String [][] board) {
        boolean valid = false;
        for (int x = 0; x < word.length(); x++) {
            if (coord.equals("x")) {
                if (!board[i][j + x + 1].equals("- ") || !board[i][j + x - 1].equals("- ") || !board[i + 1][j + x].equals("- ") || !board[i - 1][j + x].equals("- ")) {
                    valid = true;
                    break;
                }
            } else {
                if (!board[i + x][j + 1].equals("- ") || !board[i + x][j - 1].equals("- ") || !board[i + 1 + x][j].equals("- ") || !board[i + x - 1][j].equals("- ")) {
                    valid = true;
                    break;
                }
                }
            }
            return valid;
        }
        boolean checkNoLetter(int i , int j, String [][] board ) {
            boolean noLetter = false;
                    if (board[i][j].equals(("- ")))
                        noLetter = true;
                     else {
                         noLetter = false;
                }
            return noLetter;
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
