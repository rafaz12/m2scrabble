package com.company;

public class Coordinates {
    public int  m = 0, k = 0;
    public Tiles tiles;

    public Coordinates(){
        tiles = new Tiles();
        tiles.setTiles();


    }

   void setXCoordinate(String word, String[][] board, int x , int y){
        for ( k = 0; k < word.length(); k++) {
            board[x][y + k].equals(word.charAt(k));
            board[x][y + k] = String.valueOf(word.charAt(k));
            for ( m = 0; m < tiles.getTiles().size() - 1; m++) {
                if (tiles.getTiles().get(m).getKey().equals(word.charAt(k))) {
                    tiles.getTiles().remove(tiles.getTiles().get(m));
                    break;
                }
            }
        }
    }
    void setYCoordinate(String word, String[][] board, int x , int y){
        for ( k = 0; k < word.length(); k++) {
            board[x + k][y].equals(word.charAt(k));
            board[x + k][y] = String.valueOf(word.charAt(k));
            for ( m = 0; m < tiles.getTiles().size() - 1; m++) {
                if (tiles.getTiles().get(m).getKey().equals(word.charAt(k))) {
                    tiles.getTiles().remove(tiles.getTiles().get(m));
                    break;
                }
            }
        }

    }
}
