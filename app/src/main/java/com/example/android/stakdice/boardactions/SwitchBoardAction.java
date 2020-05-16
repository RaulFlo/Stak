package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class SwitchBoardAction implements BoardAction {

    private BoardSquare firstBoardSquareChosen;
    private boolean isActive;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public GameMattViewState onButtonClicked(GameMattViewState currentState) {
        isActive = true;
        currentState.setUndoButtonEnabled(false);
        ViewStateUtils.disableAbilities(currentState);
        currentState.getGameMatt().makeBoardSquareSelectableForAbility();
        currentState.setSetAdapterToSelecting(true);
        return currentState;
    }

    @Override
    public GameMattViewState onBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        if (firstBoardSquareChosen == null) {
            firstBoardSquareChosen = boardSquareClicked;
        } else {
            BoardSquare secondBoardSquareChosen = boardSquareClicked;
            int tempSecondaryValue = secondBoardSquareChosen.getDiceRollValue();
            int tempFirstValue = firstBoardSquareChosen.getDiceRollValue();
            currentState.getGameMatt().updateBoardSquare(firstBoardSquareChosen, tempSecondaryValue);
            currentState.getGameMatt().updateBoardSquare(secondBoardSquareChosen, tempFirstValue);
            currentState.setUndoButtonEnabled(true);
            currentState.setSetAdapterToSelecting(false);
            // reset state
            isActive = false;
            firstBoardSquareChosen = null;
        }
        return currentState;
    }
}
