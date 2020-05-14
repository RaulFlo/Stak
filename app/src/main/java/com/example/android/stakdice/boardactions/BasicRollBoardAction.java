package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.Random;

public class BasicRollBoardAction implements BoardAction {

    private int lastDiceRoll = 0;
    private Random rng = new Random();
    private boolean isActive = false;


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
        disableAbilities(newState);
        // disable undo button
        newState.setUndoButtonEnabled(false);
        // enable adapter to select
        newState.getGameMatt().enableAllSelectableSquares();
        // set new dice value
        newState.setDiceRollValue(lastDiceRoll);
        // update dice image
        newState.getDiceImageViewState().setDiceImageRes(getDiceImageByRollAmount(lastDiceRoll));
        // update dice visibility to visible
        newState.getDiceImageViewState().setDiceImageVisibility(true);
        newState.setSetAdapterToSelecting(true);

        // set new state
        mutableLiveData.setValue(newState);
    }

    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState) {
        isActive = false;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = gameMattViewState.getAnExactCopy();

        newState.setRollButtonEnabled(true);
        newState.setUndoButtonEnabled(true);
        newState.setSetAdapterToSelecting(false);
        newState.getDiceImageViewState().setDiceImageVisibility(false);
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, lastDiceRoll);
        SimpleGameMatt.StakColumn stakColumn = newState.getGameMatt().returnStakColumn(boardSquareClicked);
        enablePower(newState, stakColumn);

        mutableLiveData.setValue(newState);
    }

    private Integer getDiceImageByRollAmount(int rollValue) {
        switch (rollValue) {
            case 1:
                return R.drawable.dice1;
            case 2:
                return (R.drawable.dice2);
            case 3:
                return (R.drawable.dice3);
            case 4:
                return R.drawable.dice4;
            case 5:
                return R.drawable.dice5;
            case 6:
                return R.drawable.dice6;
        }

        return 0;
    }

    private void disableAbilities(GameMattViewStateK viewState) {
        viewState.getPowerUpsViewState().setFlipEnabled(false);
        viewState.getPowerUpsViewState().setReRollEnabled(false);
        viewState.getPowerUpsViewState().setSwitchEnabled(false);
        viewState.getPowerUpsViewState().setUpDownEnabled(false);
    }

    private void enablePower(GameMattViewStateK viewState, SimpleGameMatt.StakColumn stakColumn) {
        switch (stakColumn) {
            case STRENGTH:
                viewState.getPowerUpsViewState().setSwitchEnabled(true);
                break;
            case TOUGHNESS:
                viewState.getPowerUpsViewState().setUpDownEnabled(true);
                break;
            case AGILITY:
                viewState.getPowerUpsViewState().setFlipEnabled(true);
                break;
            case KNOWLEDGE:
                viewState.getPowerUpsViewState().setReRollEnabled(true);
                break;
        }
    }


    private int getDiceRollValue() {
        return rng.nextInt(6) + 1;
    }

}
