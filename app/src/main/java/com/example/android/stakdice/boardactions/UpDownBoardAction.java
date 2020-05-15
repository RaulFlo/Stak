package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class UpDownBoardAction extends BaseBoardAction {
    private boolean isUpClicked = false;
    private boolean isDownClicked = false;

    @Override
    void modifyViewStateOnButtonClick(GameMattViewStateK newState) {
        newState.setUndoButtonEnabled(false);
        ViewStateUtils.disableAbilities(newState);
        newState.setUpBtnVisible(true);
        newState.setDownBtnVisible(true);
    }

    public void onUpBtnClicked(GameMattViewStateK newState) {
        isUpClicked = true;
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        newState.setUpBtnVisible(false);
        newState.setDownBtnVisible(false);
    }

    public void onDownBtnClicked(GameMattViewStateK newState) {
        isDownClicked = true;
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        newState.setUpBtnVisible(false);
        newState.setDownBtnVisible(false);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewStateK newState) {
        newState.setUndoButtonEnabled(true);
        newState.setSetAdapterToSelecting(true);

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

        newState.getGameMatt().updateBoardSquare(boardSquareClicked, newDiceRollValue);
        isUpClicked = false;
        isDownClicked = false;
    }


}
