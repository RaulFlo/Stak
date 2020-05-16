package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

/**
 * Base class that handles setting isActive to true when the button is clicked and false when the
 * board square is clicked. This works for most simple actions.
 */
abstract public class BaseBoardAction implements BoardAction {

    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public GameMattViewState onButtonClicked(GameMattViewState currentState) {
        isActive = true;
        return getButtonClickedViewState(currentState);
    }

    @Override
    public GameMattViewState onBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        isActive = false;
        return getBoardClickedViewState(boardSquareClicked, currentState);
    }

    abstract GameMattViewState getButtonClickedViewState(GameMattViewState currentState);

    abstract GameMattViewState getBoardClickedViewState(BoardSquare boardSquareClicked, GameMattViewState currentState);
}
