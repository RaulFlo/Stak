package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class UpDownBoardAction extends BaseBoardAction {
    private boolean isUpClicked = false;
    private boolean isDownClicked = false;

    @Override
    GameMattViewState getButtonClickedViewState(GameMattViewState currentState) {
        currentState.setUndoButtonEnabled(false);
        ViewStateUtils.disableAbilities(currentState);
        currentState.setUpBtnVisible(true);
        currentState.setDownBtnVisible(true);
        return currentState;
    }

    public GameMattViewState onUpBtnClicked(GameMattViewState newState) {
        isUpClicked = true;
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        newState.setUpBtnVisible(false);
        newState.setDownBtnVisible(false);
        return newState;
    }

    public GameMattViewState onDownBtnClicked(GameMattViewState newState) {
        isDownClicked = true;
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        newState.setUpBtnVisible(false);
        newState.setDownBtnVisible(false);
        return newState;
    }

    @Override
    GameMattViewState getBoardClickedViewState(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        currentState.setUndoButtonEnabled(true);
        currentState.setSetAdapterToSelecting(false);

        int oldDiceRollValue = boardSquareClicked.getDiceRollValue();
        int newDiceRollValue;
        if (isUpClicked) {
            newDiceRollValue = oldDiceRollValue + 1;
        } else if (isDownClicked) {
            newDiceRollValue = oldDiceRollValue - 1;
        } else {
            newDiceRollValue = oldDiceRollValue;
        }

        if (newDiceRollValue > 6) {
            newDiceRollValue = 6;
        }

        if (newDiceRollValue < 1) {
            newDiceRollValue = 1;
        }

        currentState.getGameMatt().updateBoardSquare(boardSquareClicked, newDiceRollValue);
        isUpClicked = false;
        isDownClicked = false;
        return currentState;
    }
}
