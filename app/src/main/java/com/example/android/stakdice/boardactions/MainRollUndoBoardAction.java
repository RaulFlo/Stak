package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.R;
import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.Random;

public class MainRollUndoBoardAction implements BoardAction {

    private int lastDiceRoll = 0;
    private Random rng = new Random();
    private boolean isActive = false;
    private BoardSquare lastBoardSquareClicked;


    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void onButtonClicked(MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState) {
        isActive = true;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = gameMattViewState.getAnExactCopy();

        lastDiceRoll = getDiceRollValue();
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

        // set new state
        mutableLiveData.setValue(newState);
    }

    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState) {
        isActive = false;
        lastBoardSquareClicked = boardSquareClicked;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = gameMattViewState.getAnExactCopy();
        newState.setUndoButtonEnabled(true);
        newState.setRollButtonEnabled(true);
        newState.setSetAdapterToSelecting(false);
        newState.getDiceImageViewState().setDiceImageVisibility(false);
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, lastDiceRoll);
        SimpleGameMatt.StakColumn stakColumn = newState.getGameMatt().returnStakColumn(boardSquareClicked);
        ViewStateUtils.enablePower(newState, stakColumn);

        // set new state
        mutableLiveData.setValue(newState);
    }

    public void undoSettingSquare(MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK viewState) {
        if (lastBoardSquareClicked != null) {
            GameMattViewStateK newState = viewState.getAnExactCopy();
            newState.getGameMatt().updateBoardSquare(lastBoardSquareClicked, 0);
            lastBoardSquareClicked = null;
            newState.setUndoButtonEnabled(false);
            mutableLiveData.setValue(newState);
        }
    }

    private int getDiceRollValue() {
        return rng.nextInt(6) + 1;
    }
}
