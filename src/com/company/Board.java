package com.company;


import java.util.*;

import static java.lang.Integer.parseInt;

/**
 * Board for the Tic Tac Toe game. Module 2 lab assignment.
 *
 * @author Theo Ruys en Arend Rensink
 * @version $Revision: 1.4 $
 */
public class Board {
    public static final int DIM = 15;
    private IllegalArguments ill = new IllegalArguments();
    private static final int dred = 3, pred = 2, start = 2, dblue = 3, pblue = 2;
    private int[][] fieldPoints;
    private Coordinates coordinates = new Coordinates();
    private Tiles tiles;
    private ArrayList<String> wordsWritten = new ArrayList<>();
    private int score = 0, m = 0, k = 0;
    private String[][] board = new String[DIM][DIM];
    private String symbol, word = "", coord = "";
    private Scanner sc;
    // -- Constructors -----------------------------------------------

    /**
     * Creates an empty board.
     *
     * @ensures all fields are EMPTY
     */
    public Board() {
        fieldPoints = new int[DIM][DIM];
        symbol = " ";
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++) {
                if (j == 0)
                    symbol = symbol + "\r\n";
                board[i][j] = "- ";
                symbol = symbol + board[i][j];

            }
        System.out.println(symbol);

    }

    int getTileNumbers(){
        return tiles.getTiles().size();
    }
    void setYCoordinate(int x, int y) {
        for (k = 0; k < word.length(); k++) {
            board[x][y + k].equals(word.charAt(k));
            board[x][y + k] = String.valueOf(word.charAt(k));
        }

    }
    void setXCoordinate(int x, int y){
        for ( k = 0; k < word.length(); k++) {
            board[x + k][y].equals(word.charAt(k));
            board[x + k][y] = String.valueOf(word.charAt(k));

        }
    }
    void enterFirstWord() {
        int i;
        tiles = new Tiles();
        tiles.setTiles();
        tiles.setPlayerBag();
        System.out.println("Your remaining letters are " + tiles.getPlayerBag());
        System.out.println("General bag "+tiles.getTiles());
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please select coordinate x or y for word expansion ");
            coord = sc.next();
        } while (!coord.equals("x") && !coord.equals("y"));
        do {
            System.out.println("Please enter a word. ");
            sc = new Scanner(System.in);
            word = sc.next().toUpperCase();


        }while(!ill.validCoordinate(7 + word.length()-1, 7) && !ill.validCoordinate(7 , 7 + word.length()-1) || !ill.noMoreTiles(word, tiles.getPlayerBag()));
        if (coord.equals("x")) {
            setXCoordinate( 7, 7);
        } else
            setYCoordinate(7,7);
        wordsWritten.add(word);
        setScore(7, 7,coord, word);
        score = getScore();
        tiles.setNewPlayerBag(word.length());
        tiles.getPlayerBag();
        setNewBoard();
        getNewBoard();
        System.out.println("Your score is: "+score);


    }
    void skipTurn(){
        String letters ="";
        String skip = "skip!".toUpperCase();
        System.out.println("Select letters to replace or enter 'skip!' to skip.");
        boolean letterExist;
        ArrayList<Map.Entry<Character, Integer>> lettersArray = new ArrayList<>();
        do {
            letters = sc.next().toUpperCase();
            if(letters.equals(skip))
                break;
            letterExist = false;


            for (int i = 0; i < letters.length(); i++) {
                char key = letters.charAt(i);
                lettersArray.add(Map.entry(key,tiles.getTilesValues().get(key)));
            }
            for (int i = 0; i < letters.length(); i++) {
                if (!tiles.getPlayerBag().contains(lettersArray.get(i))) {
                    System.out.println("You don't have this letters in your bag please select another one.");
                    break;
                }
                else letterExist = true;
            }
            if(letterExist){
                for(int i = 0; i < letters.length(); i++ )
                    for(int j=0; j<tiles.getPlayerBag().size(); j++ )
                        if(tiles.getPlayerBag().get(j).getKey().equals(letters.charAt(i))){
                            tiles.getPlayerBag().remove(j);
                            tiles.getTiles().add(tiles.getPlayerBag().get(j));
                            Collections.shuffle(tiles.getTiles());

                        }
                tiles.setNewPlayerBag(letters.length());
                System.out.println("Your tiles now are "+ tiles.getPlayerBag());




            }
        }while(!letterExist);
    }
    void enterWord() {

        System.out.println("Your remaining letters are " + tiles.getPlayerBag());
        String k, l;
        int x = 0,y = 0;
        int i;
        do {
            System.out.println("Please select coordinate x or y for word expansion ");
            Scanner sc = new Scanner(System.in);
            coord = sc.next();
        }while (!coord.equals("x") && !coord.equals("y"));

        do {
            System.out.println("Please select coordinates x,y");
            k = sc.next();
            l = sc.next();
            if(ill.isInteger(k) && ill.isInteger(l)) {
                x = parseInt(k);
                y = parseInt(l);
            }
            else
                System.out.println("Not valid coordinates.");
        } while (!ill.validCoordinate(x, y  ) ||isField(x, y) || !ill.isInteger(k) || !ill.isInteger(l));




            System.out.println("Please enter a word. ");
            word = sc.next().toUpperCase();

            for (i = 0; i < word.length(); i++) {
                if (coord.equals("x")) {
                    if (!ill.validCoordinate(x, y + i) || isField(x, y + i) )
                        y = y + i;
                        break;
                } else if ( !ill.validCoordinate(x + i, y) || isField(x + i, y))
                    x = x + i;
                    break;

        } while (!ill.noMoreTiles(word,tiles.getPlayerBag()) || ill.wordExists(word , wordsWritten )
                ||  !ill.validCoordinate( x, y) || !ill.validCoordinate(x , y)
                || isField(x, y )) {
            System.out.println("Word not valid please enter another word with the remaining tiles and inside the bounds. ");
            word = sc.next().toUpperCase();

            for (i = 0; i < word.length(); i++) {
                if (coord.equals("x")) {
                    if ( !ill.validCoordinate(x, y + i) || isField(x, y + i) )
                        break;
                } else if ( !ill.validCoordinate(x + i, y) || isField(x + i, y) )
                    break;

            }
        }
        wordsWritten.add(word) ;
        if (coord.equals("x")) {
            setXCoordinate(x , y);
        } else
            setYCoordinate(x,y);

        setScore(x, y,coord, word);
        int score = getScore();
        tiles.setNewPlayerBag(word.length());
        tiles.getPlayerBag();
        setNewBoard();
        getNewBoard();
        System.out.println("Your remaining letters are " + tiles.getPlayerBag());
        System.out.println("General bag "+tiles.getTiles());
        System.out.println(tiles.getPlayerBag().size());
        System.out.println(tiles.getTiles().size());
        System.out.println(score);
    }

    void setScore(int x, int y, String coord ,  String word) {
        setFieldsScore();
        int[][] arr = getFieldPoints();
        int dredCount = 0;
        int predCount = 0;
        if (coord.equals("x")) {
            for (k = 0; k < word.length(); k++) {
                if (arr[x][y + k] == dred) {
                    score = score + tiles.getTilesValues().get(word.charAt(k));
                    dredCount++;
                } else if (arr[x][y + k] == pred || arr[x][y + k] == start) {
                    score = score + tiles.getTilesValues().get(word.charAt(k));
                    predCount++;
                } else
                    score = score + arr[x][y + k] * tiles.getTilesValues().get(word.charAt(k));
            }
            if (dredCount > 0 && predCount > 0)
                score = score * predCount * pred * dredCount * dred;
            else if (dredCount > 0 && predCount == 0)
                score = score * dredCount * dred;

            else if (dredCount == 0 && predCount > 0)
                score = score * predCount * pred;
        } else {
            for (k = 0; k < word.length(); k++) {
                if (arr[x + k][y ] == dred) {
                    score = score + tiles.getTilesValues().get(word.charAt(k));
                    dredCount++;
                } else if (arr[x + k][y ] == pred || arr[x + k][y] == start) {
                    score = score + tiles.getTilesValues().get(word.charAt(k));
                    predCount++;
                } else
                    score = score + arr[x + k][y] * tiles.getTilesValues().get(word.charAt(k));
            }
            if (dredCount > 0 && predCount > 0)
                score = score * predCount * pred * dredCount * dred;
            else if (dredCount > 0 && predCount == 0)
                score = score * dredCount * dred;

            else if (dredCount == 0 && predCount > 0)
                score = score * predCount * pred;
        }
    }


    int getScore() {
        return this.score;
    }

    boolean isField(int i, int j) {
        return !board[i][j].equals("- ");
    }


    void setNewBoard() {
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++) {
                if (j == 0)
                    symbol = symbol + "\r\n";
                symbol = symbol + " " + board[i][j];
            }
    }

    void getNewBoard() {
        System.out.println(symbol);
    }

    void setFieldsScore() {
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
        fieldPoints[7][11] = pblue;
        fieldPoints[7][3] = pblue;
        fieldPoints[14][3] = pblue;
        fieldPoints[14][11] = pblue;
        fieldPoints[3][0] = pblue;
        fieldPoints[3][7] = pblue;
        fieldPoints[11][0] = pblue;
        fieldPoints[3][14] = pblue;
        fieldPoints[11][14] = pblue;
    }

    int[][] getFieldPoints() {
        return fieldPoints;
    }


    public static void main(String[] args) {
        Board b = new Board();
        b.setFieldsScore();
        b.enterFirstWord();
        b.enterWord();
        b.skipTurn();

    }
}