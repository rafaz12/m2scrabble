package com.company.model;


import com.company.controllers.IllegalArguments;
import com.company.controllers.Tiles;
import com.company.controllers.Dictionary;
import com.company.view.BoardTUI;

import java.util.*;

import static java.lang.Integer.parseInt;


public class Board {
    public static final int DIM = 15;
    public IllegalArguments ill = new IllegalArguments();
    public Integer dred = new Integer(3) , pred = new Integer(2), start = new Integer(2) , dblue = new Integer(3), pblue= new Integer(2);
    public Integer[][] fieldPoints;
    public Tiles tiles ;
    public com.company.controllers.Dictionary dic = new Dictionary();
    public int c = 0;
    public ArrayList<String> wordsWritten = new ArrayList<>();
    public ArrayList<Integer> row = new ArrayList<>();
    ArrayList<Integer> col = new ArrayList<>();
    public int score = 0, m = 0, k = 0, turn =0;
    public String[][] board = new String[DIM][DIM];
    public ArrayList<String> newWordsFormed = new ArrayList<>();
    // -- Constructors -----------------------------------------------

    /**
     * Creates an empty board.
     *
     * @ensures all fields are EMPTY
     */
    public Board() {
        tiles = new Tiles();
        fieldPoints = new Integer[DIM][DIM];
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++) {
                board[i][j] = "-";
            }
        setFieldsScore();
    }

    /**
     *
     * @returns a 2x array that includes the data of the board.
     */
    public String [][] getBoardData(){
        return board;
    }

    /*
        @requires y and x coordinate and a word.
        sets the word horiontally on the board.
     */
    public void setYCoordinate(int x, int y, String word) {
        for (k = 0; k < word.length(); k++) {
           board[x][y + k] = String.valueOf(word.charAt(k));
        }

    }

    /*
        @requires y and x coordinate and a word.
        sets the word vertically on the board.
     */
    public void setXCoordinate(int x, int y , String word) {
        for (k = 0; k < word.length(); k++) {
            board[x + k][y] = String.valueOf(word.charAt(k));
        }
    }
    /*
        @requires an array of Integer objects
        sets the fieldpoints as the arrar.
     */
    public void setNewFieldsScore(Integer[][] arr){
        this.fieldPoints = arr;
    }
    /*
        sets the score of the fields for the board.
     */
    public void setFieldsScore() {
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++)
                fieldPoints[i][j] = 1;
        fieldPoints[0][0] = dred;
        fieldPoints[0][7] = dred;
        fieldPoints[0][14] = dred;
        fieldPoints[7][0] = dred;
        fieldPoints[7][14] = dred;
        fieldPoints[14][0] = dred;
        fieldPoints[14][7] = dred;
        fieldPoints[14][14] = dred;

        for (int i = 1; i < 5; i++) {
            fieldPoints[i][i] = pred;
            fieldPoints[DIM - 1 - i][DIM - 1 - i] = pred;
            fieldPoints[i][DIM - 1 - i] = pred;
            fieldPoints[DIM - 1 - i][i] = pred;
        }

        fieldPoints[7][7] = start;
        fieldPoints[1][5] = dblue;
        fieldPoints[1][9] = dblue;
        fieldPoints[5][1] = dblue;
        fieldPoints[5][5] = dblue;
        fieldPoints[5][9] = dblue;
        fieldPoints[5][13] = dblue;
        fieldPoints[9][1] = dblue;
        fieldPoints[9][5] = dblue;
        fieldPoints[9][9] = dblue;
        fieldPoints[9][13] = dblue;
        fieldPoints[13][5] = dblue;
        fieldPoints[13][9] = dblue;

        fieldPoints[0][3] = pblue;
        fieldPoints[0][11] = pblue;
        fieldPoints[2][6] = pblue;
        fieldPoints[2][8] = pblue;
        fieldPoints[12][6] = pblue;
        fieldPoints[12][8] = pblue;
        fieldPoints[11][7] = pblue;
        fieldPoints[6][2] = pblue;
        fieldPoints[8][2] = pblue;
        fieldPoints[6][12] = pblue;
        fieldPoints[8][12] = pblue;
        fieldPoints[7][11] = pblue;
        fieldPoints[7][3] = pblue;
        fieldPoints[6][6] = pblue;
        fieldPoints[6][8] = pblue;
        fieldPoints[8][6] = pblue;
        fieldPoints[8][8] = pblue;
        fieldPoints[14][3] = pblue;
        fieldPoints[14][11] = pblue;
        fieldPoints[3][0] = pblue;
        fieldPoints[3][7] = pblue;
        fieldPoints[11][0] = pblue;
        fieldPoints[3][14] = pblue;
        fieldPoints[11][14] = pblue;
    }
    /*
        @returns a 2x Integer array that has all the values of the fields.
     */
    public Integer[][] getFieldPoints() {
        return this.fieldPoints;
    }

}