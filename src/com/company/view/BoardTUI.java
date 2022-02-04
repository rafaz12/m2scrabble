package com.company.view;

import com.company.model.Board;

import static com.company.model.Board.DIM;

public class BoardTUI {
    Board board = new Board();

    public BoardTUI(Board board) {
        this.board = board;

    }

    public String printBoard() {
        String printBoard = "";
        for (int i = 0; i < DIM; i++) {
            if (i == 0)
                printBoard = "    " + printBoard + i + "   ";
            else if (i < 10)
                printBoard = printBoard + i + "   ";
            else
                printBoard = printBoard + i + "  ";


        }
        printBoard = printBoard + "\r\n";

        for (int i = 0; i < DIM; i++) {
            for (int j = 0; j < DIM; j++) {
                if (j == 0 && i < 10) {
                    printBoard = printBoard + i + "   " + board.board[i][j] + "   ";
                } else if (j == 0)
                    printBoard = printBoard + i + "  " + board.board[i][j] + "   ";
                else
                    printBoard = printBoard + board.board[i][j] + "   ";
                if (j == DIM - 1) {
                    printBoard = printBoard + "\r\n";
                }
            }
        }
        return printBoard;
    }
}
