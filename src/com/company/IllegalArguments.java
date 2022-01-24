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
    boolean wordExists(String word , ArrayList<String> words){
        if(words.contains(word))
            return true;
        return false;
    }
    boolean noMoreTiles(String word , ArrayList<Map.Entry<Character, Integer>> tiles) {
        ArrayList<Character> removedLetters = new ArrayList<>();
        ArrayList<Map.Entry<Character, Integer>> tilesClone =  new ArrayList<>(tiles);
        boolean availTile= false;
        for( int k = 0; k<word.length(); k++){
            availTile = false;
            for(int m = 0; m< tiles.size(); m++){
                if(tiles.get(m).getKey().equals(word.charAt(k))){
                    removedLetters.add(tiles.get(m).getKey());
                    tiles.remove(tiles.get(m));
                    availTile = true;
                    break;
                }

            }
            if(!availTile) {
                for (int i = 0; i< removedLetters.size(); i++){
                    for(int m = 0; m< tilesClone.size(); m++){
                       if(tilesClone.get(m).getKey().equals(removedLetters.get(i))) {
                           tiles.add(tilesClone.get(m));
                           break;

                       }


                    }
                }
                return false;
                }

            }




        return availTile;
    }

}
