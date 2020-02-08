package com.example.android.stakdice.models;

import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.List;

public interface GameMatt {
    List<BoardSquare> getSColumnBoards();

    List<BoardSquare> getTColumnBoards();

    List<BoardSquare> getAColumnBoards();

    List<BoardSquare> getKColumnBoards();

    void updateBoardSquare(BoardSquare boardSquare, int diceValue);
}
