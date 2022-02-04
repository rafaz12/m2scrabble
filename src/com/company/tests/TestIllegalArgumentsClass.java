package com.company.tests;

import com.company.controllers.IllegalArguments;
import com.company.model.Board;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestIllegalArgumentsClass {
    IllegalArguments ill = new IllegalArguments();
    Board b = new Board();

    @Test
    void TestWordExistsTrue(){
        ArrayList<String> list = new ArrayList<>();
        list.add("Sakis");
        ill.wordExists("Sakis",list);
        assertTrue(ill.wordExists("Sakis",list));
    }
    @Test
    void TestWordNotExistTrue(){
        ArrayList<String> list = new ArrayList<>();
        list.add("not");
        ill.wordExists("Sakis",list);
        assertFalse(ill.wordExists("Sakis",list));
    }
    @Test
    void TestCheckEmptyTrue(){
        b.board[0][0] = "-";
        assertTrue(ill.checkEmpty(0,0, b.board));
    }
    @Test
    void TestCheckEmptyFalse(){
        b.board[0][0] = "d";
        assertFalse(ill.checkEmpty(0,0, b.board));
    }
    @Test
    void TestCheckSameLetterTrue(){
        b.board[0][0] = "e";
        assertTrue(ill.checkSameLetter(0,0,"e", b.board));
    }

    @Test
    void TestCheckSameLetterFalse(){
        b.board[0][0] = "e";
        assertFalse(ill.checkSameLetter(0,0,"d", b.board));
    }


}
