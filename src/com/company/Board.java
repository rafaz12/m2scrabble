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
    private Integer dred = new Integer(3) , pred = new Integer(2), start = new Integer(2) , dblue = new Integer(3), pblue= new Integer(2);

    private Integer[][] fieldPoints;
    private Coordinates coordinates = new Coordinates();
    private Tiles tiles;
    private Dictionary dic = new Dictionary();
    private int c = 0;
    private ArrayList<String> wordsWritten = new ArrayList<>();
    private ArrayList<Integer> row = new ArrayList<>();
    ArrayList<Integer> col = new ArrayList<>();
    private int score = 0, m = 0, k = 0;
    private String[][] board = new String[DIM][DIM];
    private String symbol, word = "", coord = "";
    private Scanner sc;
    private ArrayList<String> newWordsFormed = new ArrayList<>();
    // -- Constructors -----------------------------------------------

    /**
     * Creates an empty board.
     *
     * @ensures all fields are EMPTY
     */
    public Board() {
        fieldPoints = new Integer[DIM][DIM];
        String[][] newBoard = new String[DIM][DIM];
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++) {
                this.board[i][j] = "-";
            }
    }

    int getTileNumbers() {
        return tiles.getTiles().size();
    }

    void setYCoordinate(int x, int y, String word) {
        for (k = 0; k < word.length(); k++) {
            board[x][y + k] = String.valueOf(word.charAt(k));
        }

    }

    void setXCoordinate(int x, int y , String word) {
        for (k = 0; k < word.length(); k++) {
            board[x + k][y] = String.valueOf(word.charAt(k));
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
    String reverseWord(String word){
        StringBuilder reverseWord = new StringBuilder();
        reverseWord.append(word);
        reverseWord.reverse();
        return reverseWord.toString();
    }
    boolean checkListWordsHorizontal(int i, int j , String word, String[][] board){
        boolean validWord = false;
        ArrayList<String> words = new ArrayList<>();
        String horizontalWord;
        String verticalWord;
        words.add(word);

            horizontalWord = getLeftWord(i,j,board)+word+getRightWord(i,j,word,board);
            words.add(horizontalWord);
            for(int x = 0; x< word.length(); x++){
                verticalWord= getAboveWord(i,j + x ,board)+word.charAt(x)+getBelowWord(i,j + x,String.valueOf(word.charAt(x)),board);
                words.add(verticalWord);
                validWord = false;
                if ((ill.checkDictionary(words.get(x)) || words.get(x).length() ==1) && !ill.wordExists(words.get(x), wordsWritten)) {
                    validWord = true;
                }
                else break;

            }

         return validWord;
    }
    boolean checkListWords(String coord,int i, int j , String word, String[][] board){
        if(coord.equals("x"))
            return checkListWordsHorizontal( i, j ,word, board);
        else
           return checkListWordsVertical( i, j ,word, board);
    }

    boolean checkListWordsVertical(int i, int j , String word, String[][] board){
        boolean validWord = false;
        ArrayList<String> words = new ArrayList<>();
        String horizontalWord;
        String verticalWord;
        words.add(word);
        verticalWord = getAboveWord(i,j,board)+word+getBelowWord(i,j,word,board);
        words.add(verticalWord);
        for(int x = 0; x< word.length(); x++){
            horizontalWord= getLeftWord(i,j,board)+word.charAt(x)+getRightWord(i,j,String.valueOf(word.charAt(x)),board);
            words.add(horizontalWord);
            validWord = false;
            if (ill.checkDictionary(words.get(x)) || (words.get(x).length() == 1))
                validWord = true;
            else break;
        }
        return validWord;
    }


    String getRightWord(int i, int j ,String word, String[][] board){
        boolean empty = false;
        String rightWord ="";
        int rightCoord = word.length() + j;
        while(!empty && rightCoord < 15) {
            empty = true;
            if (!ill.checkEmpty(i, rightCoord, board)) {
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
        while(!empty && leftCoord >= 0) {
            empty = true;
            if (!ill.checkEmpty(i, leftCoord, board)) {
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
        while(!empty && (aboveCoord >= 0)) {
            empty = true;
            if (!ill.checkEmpty(aboveCoord, j, board)) {
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
        while(!empty && belowCoord < 15) {
            empty = true;
            if (!ill.checkEmpty(belowCoord, j, board)) {
                empty = false;
                belowWord = belowWord+board[belowCoord][j];
                belowCoord++;
            }
        }
        return belowWord;
    }
    String getVerticalWord(String aboveWord, String word , String belowWord,int i , int j, String board[][]){
        String verticalWord = getAboveWord(i,j,board)+word+getBelowWord(i,j,word,board);
        return verticalWord;
    }
    String getHorizontalWord(String leftWord, String word , String rightWord,int i , int j, String board[][]){
        String horizontalWord = getLeftWord(i,j,board)+word+getRightWord(i,j,word,board);
        return horizontalWord;
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
                    if (playerBag.get(m).getKey().equals(word.charAt(x)) || ill.checkEmpty(i,j,this.board)) {
                        playerBag.remove(playerBag.get(m));
                        Collections.shuffle(tiles.getTiles());
                        playerBag.add(tiles.getTiles().get(m));
                        this.tiles.getTiles().remove(tiles.getTiles().get(m));
                        break;
                    } else if (playerBag.get(m).getKey().equals(' ') || ill.checkEmpty(i,j, this.board)) {
                        playerBag.remove(playerBag.get(m));
                        Collections.shuffle(tiles.getTiles());
                        playerBag.add(tiles.getTiles().get(m));
                        this.tiles.getTiles().remove(tiles.getTiles().get(m));
                        break;
                    }
                }
            }
            }




    void enterFirstWord() {
        String k, l;
        int x, y, a , b, c = 0;
        tiles = new Tiles();
        tiles.setTiles();
        tiles.setPlayerBag();
        remainingLetters(tiles.getPlayerBag());
        remainingLetters(tiles.getTiles());


        do {
            coord =  getWordDirection();
            Scanner sc = new Scanner(System.in);
                do {
                    System.out.println("Please select coordinates x,y");
                    k = sc.next();
                    l = sc.next();
                } while (!validateCoordinate(k, l));
                x = parseInt(k);
                y = parseInt(l);
                a = x;
                b = y;

                word = getWord().toUpperCase();
                if (coord.equals("x")) {
                    for (int i = 0; i < word.length(); i++) {
                        a++;
                        if (a == 7 && b == 7)
                            c++;
                    }
                } else {
                    for (int i = 0; i < word.length(); i++) {
                        b++;
                        if (a == 7 && b == 7)
                            c++;
                    }
                }
            }while (!dic.contains(word) || !ill.validCoordinate(x + word.length() - 1, y)
                        || !ill.validCoordinate(x, y + word.length() - 1) //||
                       /*!checkBagLetters(word, getLettersArray(word))*/ && c !=1) ;

                if (coord.equals("x")) {
                    setYCoordinate(x, y, word);
                } else
                    setXCoordinate(x, y, word);
                updateBagTiles(this.tiles.getPlayerBag(), this.word, x, y, this.board);
                wordsWritten.add(word);
                setScore(x, y, coord, word,board);

                score = getScore();
                printBoard();
                System.out.println("Your score is: " + score);
                remainingLetters(tiles.getPlayerBag());
                remainingLetters(tiles.getTiles());
    }

    void testEnterFirstWord() {
        String k, l;
        int x, y, a , b, c = 0;
        tiles = new Tiles();
        tiles.setTiles();
        tiles.setPlayerBag();
        remainingLetters(tiles.getPlayerBag());
        remainingLetters(tiles.getTiles());


        do {
            coord = "x";
            do {
                k = "7";
                l = "5";
            } while (!validateCoordinate(k, l));
            x = parseInt(k);
            y = parseInt(l);
            a = x;
            b = y;

            word = "HORN";
            if (coord.equals("x")) {
                for (int i = 0; i < word.length(); i++) {
                    a++;
                    if (a == 7 && b == 7)
                        c++;
                }
            } else {
                for (int i = 0; i < word.length(); i++) {
                    b++;
                    if (a == 7 && b == 7)
                        c++;
                }
            }
        }while (!dic.contains(word) || !ill.validCoordinate(x + word.length() - 1, y)
                || !ill.validCoordinate(x, y + word.length() - 1) //||
                /*!checkBagLetters(word, getLettersArray(word))*/ && c !=1) ;

        if (coord.equals("x")) {
            setYCoordinate(x, y, word);
        } else
            setXCoordinate(x, y, word);
        updateBagTiles(this.tiles.getPlayerBag(), this.word, x, y, this.board);
        wordsWritten.add(word);
        setScore(x, y, coord, word,board);

        score = getScore();
        printBoard();
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

    void testEnterWord() {
        int num =0;
        do {
            String k, l;
            int x, y;
            do {
                do {
                    k = "7";
                    l = "4";
                } while (!validateCoordinate(k, l));
                x = parseInt(k);
                y = parseInt(l);
                if (coord.equals("x"))
                    do {
                        word = "THORN";
                    } while (!ill.validCoordinate(x, word.length() - 1 + y));
                else do {
                    word = "Y";
                } while (!ill.validCoordinate(x + word.length() - 1, y));
            } while (!ill.validWordPlacement(x, y, coord, word, board)  || /*!checkBagLetters(word, getLettersArray(word))*/
                    !ill.validCoordPlacement(x, y, coord, word, board) || ill.wordExists(word, wordsWritten)
                            || !checkListWords(coord, x, y, word, board));
            if (coord.equals("x")) {
                setYCoordinate(x, y, word);
            } else
                setXCoordinate(x, y, word);
            setScore(x, y, coord, word,board);
            int score = getScore();
            updateBagTiles(this.tiles.getPlayerBag(), word, x, y, board);
            updateBoard(this.tiles.getPlayerBag());
            num++;
        }while(num < 5);
    }

    void enterWord() {
        int num =0;
        do {
            String k, l;
            int x, y;
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
                System.out.println(ill.validWordPlacement(x, y, coord, word, board));
                System.out.println(checkListWords(coord, x, y, word, board));
                System.out.println(ill.validCoordPlacement(x, y, coord, word, board));
            } while (!ill.validWordPlacement(x, y, this.coord, word, this.board) || !checkListWords(coord, x, y, word, board)  ||  /*!checkBagLetters(word, getLettersArray(word))*/
                    !ill.validCoordPlacement(x, y, coord, word, board) || ill.wordExists(word, wordsWritten)
                    );
            wordsWritten.addAll(newWordsFormed);
            if (coord.equals("x")) {
                setYCoordinate(x, y, word);
            } else
                setXCoordinate(x, y, word);
            setScore(x, y, coord, word,board);
            int score = getScore();
            updateBagTiles(this.tiles.getPlayerBag(), word, x, y, board);
            updateBoard(this.tiles.getPlayerBag());
            num++;
        }while(num < 5);
    }
    ArrayList<String> newWordsFormed(){
        return newWordsFormed;
    }
    void updateBoard(ArrayList<Map.Entry<Character,Integer>> playerBag ){
        printBoard();
        System.out.println("Your remaining letters are " + playerBag);
        System.out.println("General bag " + tiles.getTiles());
        System.out.println(playerBag.size());
        System.out.println(tiles.getTiles().size());
        System.out.println(score);
    }


    void setScore(int x, int y,String coord, String word, String[][] board) {
        row = getXCoordSpecialVal();
        col = getYCoordSpecialVal();
        Integer arr[][] = getFieldPoints();
        System.out.println(score);
        setWordScore(x, y, coord, word);
        newWordsFormed.add(word);

        System.out.println(score);
        System.out.println(arr[7][7]);
        if (coord.equals("x")) {
            if((getLeftWord(x, y, board).length() != 0 || getRightWord(x, y,word, board).length() != 0) &&
                    (!newWordsFormed.contains((getLeftWord(x, y, board) + word + getRightWord(x, y, word, board))))){
                setWordScore(x, y , "x", getLeftWord(x, y, board) + word + getRightWord(x, y, word, board));
                newWordsFormed.add(getLeftWord(x, y, board) + word + getRightWord(x, y, word, board));
                System.out.println(arr[x][y]);
                System.out.println("x: "+ x);
                System.out.println("y: "+y );
                System.out.println(score);
            }
            for (int l = 0; l < word.length(); l++) {
                System.out.println(arr[x][y+l]);

                if ((getAboveWord(x, ((y +l)), board).length() != 0 || getBelowWord(x , ((y +l)), word, board).length() != 0)
                &&(!newWordsFormed.contains(getAboveWord(x, ((y +l)), board) +word.charAt(l) + getBelowWord(x , ((y +l)), String.valueOf(word.charAt(l)), board)))){
                    setWordScore(x , y, "y", getAboveWord(x, (y + l), board) +word.charAt(l) + getBelowWord(x , ((y +l)), String.valueOf(word.charAt(l)), board));
                    newWordsFormed.add(getAboveWord(x, ((y +l)), board) +word.charAt(l) + getBelowWord(x , ((y +l)), String.valueOf(word.charAt(l)),board));
                    System.out.println(score);

                }
            }
        } else {
            if ((getAboveWord(x, y, board).length() != 0 || getBelowWord(x, y, word, board).length() != 0)
                && (!newWordsFormed.contains(getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board)))){
                setWordScore(x , y, "y", getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board));
                newWordsFormed.add(getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board));
                System.out.println(score);
                System.out.println(arr[x][y]);

            }
            for (int i = 0; i < word.length(); i++) {
                System.out.println(arr[x+i][y]);

                if(((getLeftWord((x + i), y, board).length() != 0) || (getRightWord((x + i), y,word, board).length() != 0)) &&
                (!newWordsFormed.contains(getLeftWord((x + i), y, board) + (word.charAt(i)) + getRightWord((x + i), y, String.valueOf(word.charAt(i)), board)))) {
                    setWordScore(x, y , "x", getLeftWord((x + i), y, board) + (word.charAt(i)) + getRightWord((x + i), y, String.valueOf(word.charAt(i)), board));
                    System.out.println(score);
                    newWordsFormed.add(getLeftWord((x + i), y, board) + (word.charAt(i)) + getRightWord((x + i), y, String.valueOf(word.charAt(i)),board));

                }
            }
        }
        for(int r = 0 ; r<row.size(); r++){
            arr[row.get(r)][col.get(r)] = 1;
        }
        setNewFieldsScore(arr);
    }


    ArrayList<Integer> getXCoordSpecialVal(){
        return row;
    }

    ArrayList<Integer> getYCoordSpecialVal(){
        return col;
    }
    void setWordScore(int x, int y, String coord , String word) {
        Integer[][] arr = getFieldPoints();
        int dredCount = 0;
        int predCount = 0;
        //if(word.length() == 7)
            //score = score + 50;
        if (coord.equals("x")) {
            for (k = 0; k < word.length(); k++) {
                if (arr[x][y + k] == dred) {
                    this.score = score + tiles.getTilesValues().get(word.charAt(k));
                    dredCount++;
                    row.add(x);
                    col.add(y + k);
                } else if (arr[x][y + k] ==pred || arr[x][y + k] ==start) {
                    this.score = score + tiles.getTilesValues().get(word.charAt(k));
                    row.add(x);
                    col.add(y + k);
                    predCount++;
                } else  {
                    this.score = score + arr[x][y + k] * tiles.getTilesValues().get(word.charAt(k));
                    if(arr[x][y + k] > 1 ) {
                        row.add(x);
                        col.add(y + k);
                    }
                }
            }
            if (dredCount > 0 && predCount > 0)
                this.score = score * predCount * pred * dredCount * dred;
            else if (dredCount > 0 && predCount == 0)
                this.score = score * dredCount * dred;

            else if (dredCount == 0 && predCount > 0)
                this.score = score * predCount * pred;
        } else {
            if(coord.equals("y")) {
                for (k = 0; k < word.length(); k++) {
                    if (arr[x + k][y] == dred) {
                        this.score = score + tiles.getTilesValues().get(word.charAt(k));
                        dredCount++;
                        row.add(x + k);
                        col.add(y);
                    } else if (arr[x + k][y] == pred || arr[x + k][y] == start) {
                        row.add(x + k);
                        col.add(y);
                        this.score = score + tiles.getTilesValues().get(word.charAt(k));
                        predCount++;
                    } else {
                        this.score = score + arr[x + k][y] * tiles.getTilesValues().get(word.charAt(k));
                        if (arr[x + k][y] > 1) {
                            row.add(x + k);
                            col.add(y);
                        }
                    }
                }
            }
            if (dredCount > 0 && predCount > 0)
                this.score = score * predCount * pred * dredCount * dred;
            else if (dredCount > 0 && predCount == 0)
                this.score = score * dredCount * dred;

            else if (dredCount == 0 && predCount > 0)
                this.score = score * predCount * pred;
        }

    }


    int getScore() {
        return this.score;
    }

    boolean isField(int i, int j) {
        return !board[i][j].equals("-");
    }


    void setNewBoard() {
        for (int i = 0; i < DIM; i++)
            for (int j = 0; j < DIM; j++) {
                if (j == 0)
                    symbol = symbol + "\r\n";
                symbol = symbol + " " + board[i][j];
            }
    }

    void printBoard() {
        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                System.out.print(board[i][j] + " ");
//                System.out.print("   ");
                if (j == DIM - 1) {
                    System.out.println();
                }
            }
        }
    }

    void getNewBoard() {
        System.out.println(symbol);
    }
    void setNewFieldsScore(Integer[][] arr){
        this.fieldPoints = arr;
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
        fieldPoints[14][3] = pblue;
        fieldPoints[14][11] = pblue;
        fieldPoints[3][0] = pblue;
        fieldPoints[3][7] = pblue;
        fieldPoints[11][0] = pblue;
        fieldPoints[3][14] = pblue;
        fieldPoints[11][14] = pblue;
    }

    Integer[][] getFieldPoints() {
        return this.fieldPoints;
    }


    public static void main(String[] args) {
        Board b = new Board();
        b.setFieldsScore();
        b.enterFirstWord();
        b.enterWord();
        b.skipTurn();

    }
}