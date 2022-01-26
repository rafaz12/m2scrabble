package com.company;

import java.lang.reflect.Array;
import java.util.*;

public class Tiles {
    private HashMap<Character,Integer> tiles ;
    private int[] quantity ;
    private ArrayList<Map.Entry<Character, Integer>> generalTiles;
    private ArrayList<Map.Entry<Character, Integer>> playerBag;
    public Tiles(){
        tiles = new HashMap<Character,Integer>();
        tiles.put('A',1);
        tiles.put('B',3);
        tiles.put('C',3);
        tiles.put('D',2);
        tiles.put('E',1);
        tiles.put('F',4);
        tiles.put('G',2);
        tiles.put('H',4);
        tiles.put('I',1);
        tiles.put('J',8);
        tiles.put('K',5);
        tiles.put('L',1);
        tiles.put('M',3);
        tiles.put('N',1);
        tiles.put('O',1);
        tiles.put('P',3);
        tiles.put('Q',10);
        tiles.put('R',1);
        tiles.put('S',1);
        tiles.put('T',1);
        tiles.put('U',1);
        tiles.put('V',4);
        tiles.put('W',4);
        tiles.put('X',8);
        tiles.put('Y',4);
        tiles.put('Z',10);
        tiles.put(' ',0);

        quantity = new int[27];
        quantity[0] = 9;
        quantity[1] = 2;
        quantity[2] = 2;
        quantity[3] = 4;
        quantity[4] = 12;
        quantity[5] = 2;
        quantity[6] = 2;
        quantity[7] = 2;
        quantity[8] = 8;
        quantity[9] = 2;
        quantity[10] = 2;
        quantity[11] = 4;
        quantity[12] = 2;
        quantity[13] = 6;
        quantity[14] = 8;
        quantity[15] = 2;
        quantity[16] = 1;
        quantity[17] = 6;
        quantity[18] = 4;
        quantity[19] = 6;
        quantity[20] = 4;
        quantity[21] = 2;
        quantity[22] = 2;
        quantity[23] = 1;
        quantity[24] = 2;
        quantity[25] = 1;
        quantity[26] = 2;


    }

    HashMap<Character, Integer> getTilesValues(){
        return tiles;
    }

    void setPlayerBag(){
        playerBag = new ArrayList<>();
        for ( int i=0; i < 7; i++ ) {
            playerBag.add(generalTiles.get(i));
            generalTiles.remove(generalTiles.get(i));
        }

    }
    void setNewPlayerBag(int i ){
        for ( int j = 0; j < i; j++){
            playerBag.add(generalTiles.get(j));
            generalTiles.remove(j);
        }

    }
    ArrayList<Map.Entry<Character, Integer>> getPlayerBag() {
        if(playerBag.contains(Map.entry(" ",0))){
            int temp = playerBag.indexOf(Map.entry(" ",0));
            playerBag.get(temp).equals(playerBag.get(playerBag.size()-1));
            playerBag.get(playerBag.size()-1).equals(Map.entry(" ",0));

        }
        return this.playerBag;
    }



    void setTiles(){
        ArrayList<Map.Entry<Character, Integer>> setTiles = new ArrayList<>();
        generalTiles = new ArrayList<>();

        Set<Map.Entry<Character,Integer>> set = tiles.entrySet();
        for (Map.Entry<Character, Integer> t : set)
            setTiles.add(t);

        for(int j=0 ; j < 27; j++){
            for (int i=0 ; i < quantity[j]; i++){
                generalTiles.add(setTiles.get(j));
            }
        }
        Collections.shuffle(generalTiles);
        System.out.println(generalTiles.size());

    }
    ArrayList<Map.Entry<Character, Integer>> getTiles(){
        return generalTiles;
    }

    public static void main(String[] args) {
        Tiles t = new Tiles();
        t.setTiles();
        t.getTiles();
    }
}
