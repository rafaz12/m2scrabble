package com.company;


import java.lang.reflect.Array;
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
    private int c = 0;
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

    int getTileNumbers() {
        return tiles.getTiles().size();
    }

    void setYCoordinate(int x, int y, String word) {
        for (k = 0; k < word.length(); k++) {
            this.board[x][y + k].equals(word.charAt(k));
            this.board[x][y + k] = String.valueOf(word.charAt(k));
        }

    }

    void setXCoordinate(int x, int y , String word) {
        for (k = 0; k < word.length(); k++) {
            this.board[x + k][y].equals(word.charAt(k));
            this.board[x + k][y] = String.valueOf(word.charAt(k));
        }
    }

    void remainingLetters(ArrayList<Map.Entry<Character, Integer>> bag) {
        System.out.println(bag);
    }

    String getWordDirection() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please select coordinate x or y for word expansion ");
        coord = sc.next();
        while (!coord.equals("x") && !coord.equals("y")) {

            System.out.println("Please select coordinate x or y for word expansion ");
            coord = sc.next();
        }
        return coord;
    }

    String getWord() {
        String word;
        System.out.println("Please enter a word. ");
        sc = new Scanner(System.in);
        word = sc.next().toUpperCase();

        return word;
    }

    void addRemove(ArrayList<Map.Entry<Character,Integer>> playerBag, int m){
        System.out.println(playerBag.get(m));
        playerBag.remove(playerBag.get(m));
        Collections.shuffle(tiles.getTiles());
        playerBag.add(tiles.getTiles().get(m));
        this.tiles.getTiles().remove(tiles.getTiles().get(m));
    }

    void updateBagTiles(ArrayList<Map.Entry<Character, Integer>> playerBag , String word, int i , int j , String[][] board) {
        for (int x = 0; x < word.length(); x++){
            for (int m = 0; m < playerBag.size(); m++) {
                    if (playerBag.get(m).getKey().equals(word.charAt(x)) || ill.checkNoLetter(i,j,this.board)) {
                        playerBag.remove(playerBag.get(m));
                        Collections.shuffle(tiles.getTiles());
                        playerBag.add(tiles.getTiles().get(m));
                        this.tiles.getTiles().remove(tiles.getTiles().get(m));
                        break;
                    } else if (playerBag.get(m).getKey().equals(' ') || ill.checkNoLetter(i,j, this.board)) {
                        playerBag.remove(playerBag.get(m));
                        Collections.shuffle(tiles.getTiles());
                        playerBag.add(tiles.getTiles().get(m));
                        this.tiles.getTiles().remove(tiles.getTiles().get(m));
                        break;
                    }
                }
            }
            }




    void enterFirstWord(){
        tiles = new Tiles();
        tiles.setTiles();
        tiles.setPlayerBag();
        remainingLetters(tiles.getPlayerBag());
        remainingLetters(tiles.getTiles());
        String coord = getWordDirection();
        do {
            word = getWord();
        } while (!ill.validCoordinate(7 + word.length() - 1, 7) || !ill.validCoordinate(7, 7 + word.length() - 1) || !checkBagLetters(word,getLettersArray(word)));

        if (coord.equals("x")) {
            setYCoordinate(7, 7 , word);
        } else
            setXCoordinate(7, 7 , word);
        updateBagTiles(this.tiles.getPlayerBag(), this.word, 7, 7,this.board);
        wordsWritten.add(word);
        setScore(7, 7, coord, word);
        score = getScore();
        setNewBoard();
        getNewBoard();
        System.out.println("Your score is: " + score);
        remainingLetters(tiles.getPlayerBag());
        remainingLetters(tiles.getTiles());
    }

    void swapLetters(String letters ) {
        for (int i = 0; i < letters.length(); i++)
            for (int j = 0; j < tiles.getPlayerBag().size(); j++)
                if (tiles.getPlayerBag().get(j).getKey().equals(letters.charAt(i))) {
                    tiles.getTiles().add(tiles.getPlayerBag().get(j));
                    tiles.getPlayerBag().remove(tiles.getPlayerBag().get(j));
                    Collections.shuffle(tiles.getTiles());
                    tiles.getPlayerBag().add(tiles.getTiles().get(j));
                    tiles.getTiles().remove(tiles.getPlayerBag().get(j));
                }
    }
    boolean checkBlankTile(ArrayList<Map.Entry<Character, Integer>> playerBag){
        if(playerBag.contains(' '))
            return true;
        return false;
    }
    int getBlankTiles(ArrayList<Map.Entry<Character, Integer>> playerBag){
        int c = 0;
        for( int i = 0; i< playerBag.size(); i++){
            if(playerBag.get(i).getKey().equals(' '))
                c++;
        }
        return c;
    }

    boolean checkBagLetters(String letters, ArrayList<Map.Entry<Character, Integer>> lettersArray) {
        int counter = 0;
        boolean letterExist = false;
        for (int i = 0; i < letters.length(); i++) {
            if(tiles.getTiles().contains(lettersArray.get(i)))
                letterExist = true;
            else if (!tiles.getPlayerBag().contains(lettersArray.get(i)) && !checkBlankTile(tiles.getPlayerBag())) {
                break;
            } else {
                getBlankTiles(tiles.getPlayerBag());
                 counter++;
            }
        }
        if(counter == getBlankTiles(tiles.getPlayerBag()))
            letterExist = true;
        return letterExist;
    }

    ArrayList<Map.Entry<Character, Integer>> getLettersArray(String letters) {
        ArrayList<Map.Entry<Character, Integer>> lettersArray = new ArrayList<>();
        for (int i = 0; i < letters.length(); i++) {
            char key = letters.charAt(i);
            lettersArray.add(Map.entry(key, tiles.getTilesValues().get(key)));
        }
        return lettersArray;
    }

    void skipTurn() {
        String letters;
        String skip = "skip!".toUpperCase();
        ArrayList<Map.Entry<Character, Integer>> lettersArray;
        boolean letterExist;
        do {
            System.out.println( "enter 'skip!' to skip or enter tiles to replace.");
            letters = sc.next().toUpperCase();
            if (letters.equals(skip))
                break;

            lettersArray = getLettersArray(letters);
            letterExist = checkBagLetters(letters, lettersArray);
            if (letterExist) {
                swapLetters(letters);
                System.out.println("Your tiles now are " + tiles.getPlayerBag());
            }
        } while (!letterExist);
    }

    boolean validateCoordinate(String k, String l) {
        boolean trueCoord = false;
        int x = 0, y = 0;
        if (ill.isInteger(k) && ill.isInteger(l)) {
            x = parseInt(k);
            y = parseInt(l);

        } else return false;
        if (ill.validCoordinate(x, y))
            trueCoord = true;
        return trueCoord;
    }

    void enterWord() {
        String k, l;
        int x ,y;
        getWordDirection();
        do {
            do {
                System.out.println("Please select coordinates x,y");
                k = sc.next();
                l = sc.next();
            } while (!validateCoordinate(k, l));
            x = parseInt(k);
            y = parseInt(l);
            if (coord.equals("x"))
                do {
                    word = getWord().toUpperCase();
                } while (!ill.validCoordinate(x, word.length() - 1 + y));
            else do {
                word = getWord().toUpperCase();
            } while (!ill.validCoordinate(x + word.length() - 1, y));
        } while ( !ill.validWordPlacement(x, y, coord, word, board) || !checkBagLetters(word,getLettersArray(word)) ||
                !ill.validCoordPlacement(x, y, coord, word, board) || ill.wordExists(word,wordsWritten));
        wordsWritten.add(word);
        if (coord.equals("x")) {
            setYCoordinate(x, y , word);
        } else
            setXCoordinate(x, y , word);
        setScore(x, y, coord, word);
        int score = getScore();
        updateBagTiles(this.tiles.getPlayerBag(), word, x, y,board);
        updateBoard(this.tiles.getPlayerBag());

    }
    void updateBoard(ArrayList<Map.Entry<Character,Integer>> playerBag ){
        setNewBoard();
        getNewBoard();
        System.out.println("Your remaining letters are " + playerBag);
        System.out.println("General bag " + tiles.getTiles());
        System.out.println(playerBag.size());
        System.out.println(tiles.getTiles().size());
        System.out.println(score);
    }



    void setScore(int x, int y, String coord ,  String word) {
        setFieldsScore();
        int[][] arr = getFieldPoints();
        int dredCount = 0;
        int predCount = 0;
        if(word.length() == 7)
            score = score + 50;
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