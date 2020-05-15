package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class ReRollBoardAction extends BaseBoardAction {

    @Override
    void modifyViewStateOnButtonClick(GameMattViewState newState) {
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(newState);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState newState) {
        int rerolledValue = ViewStateUtils.getDiceRollValue();
        newState.setSetAdapterToSelecting(false);
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, rerolledValue);
    }

}
