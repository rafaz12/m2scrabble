package com.company;

import java.util.*;

import static java.lang.Integer.parseInt;


public class Player {
    public IllegalArguments ill = new IllegalArguments();
    public Integer dred = new Integer(3) , pred = new Integer(2), start = new Integer(2) , dblue = new Integer(3), pblue= new Integer(2);
    public Integer[][] fieldPoints;
    public HashMap<Character,Integer> tileValue = new HashMap<>();
    public Tiles tiles = new Tiles();
    public int wordScore = 0;
    public boolean letterExist, validPlacement;
    public boolean tilesExist;
    public boolean giveUp = false ;
    public String h = "", v ="";
    public int turn;
    public Dictionary dic = new Dictionary();
    public int c = 0;
    public ArrayList<Integer> row = new ArrayList<>();
    ArrayList<Integer> col = new ArrayList<>();
    Board b = new Board();
    public int score = 0, m = 0, k = 0;
    Tiles t = new Tiles();

    ArrayList<Map.Entry<Character, Integer>> playerBag  = new ArrayList<>();
    String name, word = " ", coord = "";

    public Player(String name, Board b) {
        this.b  = b;
        this.name = name;
        t.setTiles();
    }

    void playerSetScore(){
        this.score = getScore();
    }
    int getPlayerScore(){
        return this.score;
    }
    void setPlayerBag(ArrayList<Map.Entry<Character,Integer>> genBag) {
        for (int i = 0; i < 7; i++) {
            this.playerBag.add(genBag.get(i));
           genBag.remove(genBag.get(i));
        }
    }
    ArrayList<Map.Entry<Character, Integer>> playerBag(){
        return this.playerBag;
    }
    boolean checkPlayerCommand( String text){
        if (!text.equals("M") && !text.equals("SK") && !text.equals("SW") && !text.equals("G"))
            return false;
        return true;
    }
    String getPlayerName(){
        return this.name;
    }
    public void makeMove(String cmd, String letters, String coord, String h , String v , String word){
        this.coord = coord;
        this.h = h;
        this.v = v;
        this.word = word;
        if(cmd.equals("M") && b.turn == 0) {
            enterFirstWord(coord, h, v, word);
            checkGameOver(this.playerBag);
        }
        else if (cmd.equals("M")){

            checkGameOver(this.playerBag);
        }
        else if(cmd.equals("SK")){
            skipTurn();
        }
        else if(cmd.equals("G")){
            this.giveUp = true;
        }
        else if(cmd.equals("SW")){
            ArrayList<Map.Entry<Character, Integer>> lettersArray;

                //letters = sc.next().toUpperCase();
                tilesExist = t.validSwap(letters.length(),t.generalTiles);
                lettersArray = getLettersArray(letters);
                letterExist = checkBagLetters(letters, lettersArray, this.playerBag);
                if(t.generalTiles.size() == 0){
                    System.out.println("There are not tiles in the general bag");

                }
                else if (letterExist && tilesExist) {
                    swapLetters(letters);
                    System.out.println("Your tiles now are " + getPlayerBag());
                }
                else {
                    System.out.println("not valid swap");
                    System.out.println(" Select tiles to swap");
                }


        }
         }


   public ArrayList<Map.Entry<Character, Integer>> getPlayerBag() {
        if (this.playerBag.contains(Map.entry('!', 0))) {
            int temp = playerBag.indexOf(Map.entry('!', 0));
            this.playerBag.remove(temp);
            this.playerBag.add(Map.entry('!', 0));
        }
        return this.playerBag;
    }
    void printPlayersBoard(){
        b.printBoard();
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
    HashMap<Character,Integer> getValues() {
        Tiles tile = new Tiles();
        return tile.getTilesValues();
    }

    String getWord() {
        String word;
        System.out.println("Please enter a word. ");
        Scanner sc = new Scanner(System.in);
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
            if ((ill.checkDictionary(words.get(x)))) {
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
            if (ill.checkDictionary(words.get(x)))
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

    void
    updateBagTiles(ArrayList<Map.Entry<Character, Integer>> playerBag , String word, int i , int j , String[][] board) {
        for (int x = 0; x < word.length(); x++){
            for (int m = 0; m < playerBag.size(); m++) {
                if (this.playerBag.get(m).getKey().equals(word.charAt(x)) || ill.checkEmpty(i,j,b.getBoardData())) {
                    this.playerBag.remove(playerBag.get(m));
                    Collections.shuffle(t.generalTiles);
                    this.playerBag.add(t.generalTiles.get(m));
                    this.t.generalTiles.remove(t.generalTiles.get(m));
                    break;
                } else if (playerBag.get(m).getKey().equals('!') || ill.checkEmpty(i,j, b.getBoardData())) {
                    this.playerBag.remove(playerBag.get(m));
                    Collections.shuffle(t.generalTiles);
                    this.playerBag.add(t.generalTiles.get(m));
                    this.t.generalTiles.remove(t.generalTiles.get(m));
                    break;
                }
            }
        }
    }

    void setHorizontalScore(int x, int y, String word, String[][] board){
        Integer arr[][] = b.getFieldPoints();
        if((getLeftWord(x, y, board).length() != 0 || getRightWord(x, y,word, board).length() != 0) &&
                (!b.newWordsFormed.contains((getLeftWord(x, y, board) + word + getRightWord(x, y, word, board))))){
            setWordScore(x, y , "x", getLeftWord(x, y, board) + word + getRightWord(x, y, word, board), b);
            b.newWordsFormed.add(getLeftWord(x, y, board) + word + getRightWord(x, y, word, board));
            System.out.println(arr[x][y]);
            System.out.println("x: "+ x);
            System.out.println("y: "+y );
            System.out.println(score);
        }
    }
    void setVerticalScore(int x, int y, String word, String[][] board)  {
        Integer arr[][] = b.getFieldPoints();
        if ((getAboveWord(x, y, board).length() != 0 || getBelowWord(x, y, word, board).length() != 0)
                && (!b.newWordsFormed.contains(getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board)))
                && dic.contains(getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board))){
            setWordScore(x , y, "y", getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board),b);
            b.newWordsFormed.add(getAboveWord(x, y, board) + word + getBelowWord(x, y, word, board));
            System.out.println(score);
            System.out.println(arr[x][y]);
        }
    }

    public void setScore(int x, int y,String coord, String word, String[][] board, Board b ) {
        row = getXCoordSpecialVal();
        col = getYCoordSpecialVal();
        Integer arr[][] = b.getFieldPoints();

        setWordScore(x, y, coord, word, b);
        if(word.length() == 7) {
            System.out.println("BINGO!");
            this.score = score + 50;
        }
        b.newWordsFormed.add(word);

        if (coord.equals("x")) {
            setHorizontalScore(x , y , word , board);
            for (int i =0; i< word.length(); i++){
                setVerticalScore(x ,y + i,String.valueOf(word.charAt(i)),board);
            }
        }
        else{
            setVerticalScore(x, y, word, board);
            for (int i =0; i< word.length(); i++){
                setHorizontalScore(x + i , y , String.valueOf(word.charAt(i)) , board);
            }
        }
        for(int r = 0 ; r<row.size(); r++){
            arr[this.row.get(r)][this.col.get(r)] = 1;
        }
        b.setNewFieldsScore(arr);
    }


    ArrayList<Integer> getXCoordSpecialVal(){
        return row;
    }

    ArrayList<Integer> getYCoordSpecialVal(){
        return col;
    }
    public  void setWordScore(int x, int y, String coord , String word, Board b) {
        Integer[][] arr = b.getFieldPoints();


        int dredCount = 0;
        int predCount = 0;
        wordScore = 0;
        if (coord.equals("x")) {
            for (k = 0; k < word.length(); k++) {
                if(y + k < 15) {
                    if (arr[x][y + k] == dred) {
                        wordScore = wordScore + getValues().get(word.charAt(k));
                        dredCount++;
                        row.add(x);
                        col.add(y + k);
                    } else if (arr[x][y + k] == pred || arr[x][y + k] == start) {
                        wordScore = wordScore + getValues().get(word.charAt(k));
                        row.add(x);
                        col.add(y + k);
                        predCount++;
                    } else {
                        wordScore = wordScore + arr[x][(y + k)] * t.tl.get(word.charAt(k));
                        if (arr[x][y + k] > 1) {
                            row.add(x);
                            col.add(y + k);
                        }
                    }
                }
            }
            if (dredCount > 0 && predCount > 0) {
                this.score = score + wordScore * predCount * pred * dredCount * dred;
            }
            else if (dredCount > 0 && predCount == 0) {
                this.score = score + wordScore * dredCount * dred;

            }
            else if (dredCount == 0 && predCount > 0) {
                this.score = score + wordScore * predCount * pred;
            }
            else
                this.score = score + wordScore;
        } else {
            if(coord.equals("y")) {
                for (k = 0; k < word.length(); k++) {
                    if(x + k < 15) {
                        if (arr[x + k][y] == dred) {
                            wordScore = wordScore + getValues().get(word.charAt(k));
                            System.out.println("Score is : " + score);

                            dredCount++;
                            row.add(x + k);
                            col.add(y);
                        } else if (arr[x + k][y] == pred || arr[x + k][y] == start) {
                            row.add(x + k);
                            col.add(y);
                            wordScore = wordScore + getValues().get(word.charAt(k));
                            System.out.println("Score is : " + score);
                            predCount++;
                        } else {
                            wordScore = wordScore + b.getFieldPoints()[x+k][y] * getValues().get(word.charAt(k));
                            System.out.println("Score is : " + score);
                            if (arr[x + k][y] > 1) {
                                row.add(x + k);
                                col.add(y);
                            }
                        }
                    }
                }
            }
            if (dredCount > 0 && predCount > 0) {
                this.score = score + wordScore * predCount * pred * dredCount * dred;
            }
            else if (dredCount > 0 && predCount == 0) {
                this.score = score + wordScore * dredCount * dred;

            }
            else if (dredCount == 0 && predCount > 0) {
                this.score = score + wordScore * predCount * pred;
            }
            else
                this.score = score + wordScore;
        }


        System.out.println("Score is : "+this.score);
        System.out.println("Score of "+word+"is : "+wordScore);

    }


    int getScore() {
        return this.score;
    }





    public void enterFirstWord(String coord , String k , String l, String word) {
        validPlacement = false;
        int x, y, w, e, c;
        tiles = new Tiles();
        tiles.setTiles();

        c = 0;
        x = parseInt(k);
        y = parseInt(l);
        w = x;
        e = y;
        if (coord.equals("x")) {
            for (int i = 0; i < word.length(); i++) {
                if (w == 7 && e == 7) {
                    c = 1;
                    break;
                }
                e++;
            }
        } else {
            for (int i = 0; i < word.length(); i++) {
                if (w == 7 && e == 7) {
                    c = 1;
                    break;
                }
                w++;

            }
        }
        if (dic.contains(word) && c == 1 && checkBagLetters(word,getLettersArray(word),playerBag) &&
                ( coord.equalsIgnoreCase("x") || coord.equalsIgnoreCase("y"))) {
            validPlacement = true;
            b.turn++;
            coord = coord.toLowerCase();
            if (coord.equals("x")) {
                b.setYCoordinate(x, y, word);
            } else
                b.setXCoordinate(x, y, word);
            updateBagTiles(playerBag, word, x, y, b.getBoardData());
            setScore(x,y,coord,word,b.board,b);
            b.wordsWritten.add(word);
        }
    }



    void swapLetters(String letters ) {
        for (int i = 0; i < letters.length(); i++)
            for (int j = 0; j < this.getPlayerBag().size(); j++)
                if (this.getPlayerBag().get(j).getKey().equals(letters.charAt(i))) {
                    t.generalTiles.add(this.getPlayerBag().get(j));
                    this.getPlayerBag().remove(getPlayerBag().get(j));
                    Collections.shuffle(t.generalTiles);
                    this.getPlayerBag().add(t.generalTiles.get(j));
                    t.generalTiles.remove(this.getPlayerBag().get(j));
                    break;
                }
    }
    boolean checkBlankTile(ArrayList<Map.Entry<Character, Integer>> playerBag){
        if(this.playerBag.contains('!'))
            return true;
        return false;
    }
    int getBlankTiles(ArrayList<Map.Entry<Character, Integer>> playerBag){
        int c = 0;
        for( int i = 0; i< this.playerBag.size(); i++){
            if(this.playerBag.get(i).getKey().equals('!'))
                c++;
        }
        return c;
    }

    boolean checkBagLetters(String letters, ArrayList<Map.Entry<Character, Integer>> lettersArray, ArrayList<Map.Entry<Character, Integer>> playerBag ) {
        int counter = 0;
        boolean letterExist = false;
        for (int i = 0; i < letters.length(); i++) {
            if(this.t.generalTiles.contains(lettersArray.get(i)))
            if (this.playerBag.contains(lettersArray.get(i)) || checkBlankTile(this.playerBag())) {
                break;
            } else {
                getBlankTiles(this.playerBag);
                counter++;
            }
        }
        if(counter == getBlankTiles(this.playerBag))
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
    boolean giveUp( String cmd) {
        boolean giveUp = false;
        if(cmd.equals("G"))
            giveUp = true;
        return giveUp;
    }
    boolean checkGameOver( ArrayList<Map.Entry<Character, Integer>> playerBag){
        if(playerBag.size() == 0){
            System.out.println("Game over");
        }
        return true;
    }


    void skipTurn() {
        System.out.println("Turn skipped. ");
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

    public void enterWord(String coord, String k, String l, String word) {
        validPlacement = false;
        int x, y;


        x = parseInt(k);
        y = parseInt(l);
      /*  if (coord.equals("x"))
            do {
                word = getWord().toUpperCase();
                if (!ill.validCoordinate(x, word.length() - 1 + y))
                    System.out.println("Enter another word inside bounds");
            } while (!ill.validCoordinate(x, word.length() - 1 + y));
        else do {
            word = getWord().toUpperCase();
            if (!ill.validCoordinate(x + word.length() - 1, y))
                System.out.println("Enter another word inside bounds");
        } while (!ill.validCoordinate(x + word.length() - 1, y));
       */
        if (notAllConditionsMet(x, y , word , coord )) {
            System.out.println("Invalid move");
        } else {
            validPlacement = true;
            b.wordsWritten.addAll(b.newWordsFormed);
            if (coord.equals("x")) {
                b.setYCoordinate(x, y, word);
            } else
                b.setXCoordinate(x, y, word);
            setScore(x, y, coord, word, b.getBoardData(), b);
            updateBagTiles(this.playerBag, word, x, y, b.getBoardData());
        }
    }
    public boolean notAllConditionsMet(int x, int y , String word , String coord ){
        if(!dic.contains(word) ||  !ill.validWordPlacement(x, y, this.coord, word, b.getBoardData()) || !checkListWords(coord, x, y, word, b.getBoardData())  ||
                !checkBagLetters(word, getLettersArray(word),playerBag) ||
                !ill.validCoordPlacement(x, y, coord, word, b.getBoardData()) || ill.wordExists(word, b.wordsWritten))
            return true;
        return false;
    }


    void printOptions(){
        System.out.println(
                "Type 'M' to make move" +
                "Type 'SK' to skip turn"+
                "Type 'SW' to swap letters"+
                "Type 'G' to give up"
        );
    }

    public static void main(String[] args) {


    }
}
