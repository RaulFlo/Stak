package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class SwitchBoardAction extends BaseBoardAction {

    private BoardSquare firstBoardSquareChosen;

    @Override
    void modifyViewStateOnButtonClick(GameMattViewStateK newState) {
        newState.setUndoButtonEnabled(false);
        ViewStateUtils.disableAbilities(newState);
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
    }

    // not calling super because we're going to handle setting isActive back to false when we're ready
    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData) {
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = mutableLiveData.getValue().getAnExactCopy();
        modifyViewStateOnBoardSquareClicked(boardSquareClicked, newState);
        mutableLiveData.postValue(newState);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewStateK newState) {
        if (firstBoardSquareChosen == null) {
            firstBoardSquareChosen = boardSquareClicked;
        } else {
            BoardSquare secondBoardSquareChosen = boardSquareClicked;
            int tempSecondaryValue = secondBoardSquareChosen.getDiceRollValue();
            int tempFirstValue = firstBoardSquareChosen.getDiceRollValue();
            newState.getGameMatt().updateBoardSquare(firstBoardSquareChosen, tempSecondaryValue);
            newState.getGameMatt().updateBoardSquare(secondBoardSquareChosen, tempFirstValue);

            newState.setUndoButtonEnabled(true);
            newState.setSetAdapterToSelecting(false);
            isActive = false;
            // reset state
            firstBoardSquareChosen = null;
        }
    }
}
