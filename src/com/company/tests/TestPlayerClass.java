package com.company.tests;

import com.company.controllers.Player;
import com.company.controllers.Tiles;
import com.company.model.Board;
import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayerClass {
    Board b = new Board();


   @Test
    void testBlankTileFalse(){
       Player pl = new Player("Rafa", b);
        HashMap<Character,Integer> map = new HashMap<>();
        ArrayList<Map.Entry<Character,Integer>> playerBag = new ArrayList<>();
        map.put('A',1);
        map.put('B',5);
        map.put('C',5);
        playerBag.addAll(map.entrySet());
        assertFalse(pl.checkBlankTile(playerBag));
    }

    @Test
    void testBlankTileTrue(){
        Player pl = new Player("Rafa", b);
        HashMap<Character,Integer> map = new HashMap<>();
        ArrayList<Map.Entry<Character,Integer>> playerBag = new ArrayList<>();
        map.put('A',1);
        map.put('!',5);
        map.put('C',5);
        playerBag.addAll(map.entrySet());
        assertFalse(pl.checkBlankTile(playerBag));
    }
    @Test
    void TestReverseWordFalse(){
        Player pl = new Player("Rafa", b);
        String reversedWord = pl.reverseWord("Asfg");
        assertNotEquals("word",reversedWord);
    }

    @Test
    void TestReverseWordTrue(){
        Player pl = new Player("Rafa", b);
        String reversedWord = pl.reverseWord("Asfg");
        assertEquals("gfsA",reversedWord);
    }

}
