package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class MainRollUndoBoardAction extends BaseBoardAction {

    private int lastDiceRoll = 0;
    private BoardSquare lastBoardSquareClicked;

    @Override
    void modifyViewStateOnButtonClick(GameMattViewState newState) {
        lastDiceRoll = ViewStateUtils.getDiceRollValue();
        // update round
        newState.setRoundValue(newState.getRoundValue() + 1);
        // disable abilities
        ViewStateUtils.disableAbilities(newState);
        // disable undo button
        newState.setUndoButtonEnabled(false);
        // enable adapter to select
        newState.getGameMatt().enableAllSelectableSquares();
        // set new dice value
        newState.setDiceRollValue(lastDiceRoll);
        // update dice image
        newState.getDiceImageViewState().setDiceImageRes(ViewStateUtils.getDiceImageByRollAmount(lastDiceRoll));
        // update dice visibility to visible
        newState.getDiceImageViewState().setDiceImageVisibility(true);
        newState.setSetAdapterToSelecting(true);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState newState) {
        lastBoardSquareClicked = boardSquareClicked;

        newState.setUndoButtonEnabled(true);
        newState.setRollButtonEnabled(true);
        newState.setSetAdapterToSelecting(false);
        newState.getDiceImageViewState().setDiceImageVisibility(false);
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, lastDiceRoll);
        SimpleGameMatt.StakColumn stakColumn = newState.getGameMatt().returnStakColumn(boardSquareClicked);
        ViewStateUtils.enablePower(newState, stakColumn);
    }

    public void undoSettingSquare(MutableLiveData<GameMattViewState> mutableLiveData, GameMattViewState viewState) {
        if (lastBoardSquareClicked != null) {
            GameMattViewState newState = viewState.getAnExactCopy();
            newState.getGameMatt().updateBoardSquare(lastBoardSquareClicked, 0);
            lastBoardSquareClicked = null;
            newState.setUndoButtonEnabled(false);
            ViewStateUtils.disableAbilities(newState);
            mutableLiveData.setValue(newState);
        }
    }
}
