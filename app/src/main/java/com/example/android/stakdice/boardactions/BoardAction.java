package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public interface BoardAction {

    boolean isActive();

    GameMattViewState onButtonClicked(GameMattViewState currentState);

    GameMattViewState onBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState currentState);
}

