package com.company.tests;

import com.company.model.Board;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class TestBoardClass {
    Board b = new Board();

    @Test
    void TestSetYCoordinateTrue(){
        b.setYCoordinate(1,1,"sakis");
        assertEquals("s",b.board[1][5]);
    }
    @Test
    void TestSetYCoordinateFalse(){
        b.setYCoordinate(1,1,"sakis");
        assertNotEquals("s",b.board[1][4]);
    }
    @Test
    void TestSetXCoordinateTrue(){
        b.setYCoordinate(1,1,"sakis");
        assertEquals("s",b.board[1][5]);
    }
    @Test
    void TestSetXCoordinateFalse(){
        b.setYCoordinate(1,1,"sakis");
        assertNotEquals("s",b.board[5][1]);
    }
}
