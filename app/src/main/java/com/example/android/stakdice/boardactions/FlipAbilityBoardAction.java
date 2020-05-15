package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class FlipAbilityBoardAction implements BoardAction {
    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void onButtonClicked(MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState) {
        isActive = true;
        GameMattViewStateK newState = gameMattViewState.getAnExactCopy();
        newState.setUndoButtonEnabled(false);
        //make boardsquare with dice values able to be selected
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(newState);
        mutableLiveData.setValue(newState);
    }

    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState) {
        isActive = false;
        GameMattViewStateK newState = gameMattViewState.getAnExactCopy();

        //save value of clicked boardsquare
        int intValueOfBs = boardSquareClicked.getDiceRollValue();

        //input int to method and receive new flipped int
        int flippedValue = flipAbility(intValueOfBs);

        // after they click, set the adapter to not selecting
        newState.setSetAdapterToSelecting(false);

        //update boardsquare with new flipped int
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, flippedValue);
        mutableLiveData.setValue(newState);
    }

    private int flipAbility(int diceValueForAbility) {


        switch (diceValueForAbility) {
            case 6:
                return 1;
            case 5:
                return 2;

            case 4:
                return 3;

            case 3:
                return 4;

            case 2:
                return 5;

            case 1:
                return 6;


        }
        return 0;

    }
}
