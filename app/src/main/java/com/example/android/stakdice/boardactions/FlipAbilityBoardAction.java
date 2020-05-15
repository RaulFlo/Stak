package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class FlipAbilityBoardAction extends BaseBoardAction {
    @Override
    void modifyViewStateOnButtonClick(GameMattViewStateK newState) {
        newState.setUndoButtonEnabled(false);
        //make boardsquare with dice values able to be selected
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(newState);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewStateK newState) {
        //save value of clicked boardsquare
        int intValueOfBs = boardSquareClicked.getDiceRollValue();

        //input int to method and receive new flipped int
        int flippedValue = flipAbility(intValueOfBs);

        // after they click, set the adapter to not selecting
        newState.setSetAdapterToSelecting(false);

        //update boardsquare with new flipped int
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, flippedValue);
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
