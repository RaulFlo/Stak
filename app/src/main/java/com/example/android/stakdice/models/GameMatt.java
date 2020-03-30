package com.example.android.stakdice.models;

import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.List;

public interface GameMatt {
    List<BoardSquare> getSColumnBoards();

    List<BoardSquare> getTColumnBoards();

    List<BoardSquare> getAColumnBoards();

    List<BoardSquare> getKColumnBoards();

    void updateBoardSquare(BoardSquare boardSquare, int diceValue);

    void undoLastBoardSquare(BoardSquare boardSquare);

    SimpleGameMatt.StakColumn returnLastColumn(BoardSquare boardSquare);

    void makeBoardSquareSelectableForAbility();

    void enableAllSelectableSquares();

}
