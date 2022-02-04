package com.company.controllers;

import java.lang.reflect.Array;
import java.util.*;

public class Tiles {
    public HashMap<Character,Integer>tl ;
    public int[] quantity ;
    public ArrayList<Map.Entry<Character, Integer>> generalTiles;
    public Tiles(){
        this.tl = new HashMap<>();
        this.tl.put('A',1);
        this.tl.put('B',3);
        this.tl.put('C',3);
        this.tl.put('D',2);
        this.tl.put('E',1);
        this.tl.put('F',4);
        this.tl.put('G',2);
        this.tl.put('H',4);
        this.tl.put('I',1);
        this.tl.put('J',8);
        this.tl.put('K',5);
        this.tl.put('L',1);
        this.tl.put('M',3);
        this.tl.put('N',1);
        this.tl.put('O',1);
        this.tl.put('P',3);
        this.tl.put('Q',10);
        this.tl.put('R',1);
        this.tl.put('S',1);
        this.tl.put('T',1);
        this.tl.put('U',1);
        this.tl.put('V',4);
        this.tl.put('W',4);
        this.tl.put('X',8);
        this.tl.put('Y',4);
        this.tl.put('Z',10);
        this.tl.put('!',0);

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

    public HashMap<Character, Integer> getTilesValues(){
        return this.tl;
    }

    public boolean validSwap(int x, ArrayList<Map.Entry<Character, Integer>> generalBag){
        return x <= generalBag.size();
    }
    public void setTiles(){
        ArrayList<Map.Entry<Character, Integer>> setTiles = new ArrayList<>();
        generalTiles = new ArrayList<>();
        Set<Map.Entry<Character,Integer>> set = this.tl.entrySet();
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

}
