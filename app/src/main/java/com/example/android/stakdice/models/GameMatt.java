package com.example.android.stakdice.models;

import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.List;

public interface GameMatt {
    List<BoardSquare> getInitialBoards();

    List<BoardSquare> updateBoardSquare(BoardSquare boardSquare);
}
