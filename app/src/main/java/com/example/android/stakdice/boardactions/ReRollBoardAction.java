package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class ReRollBoardAction extends BaseBoardAction {

    @Override
    void modifyViewStateOnButtonClick(GameMattViewStateK newState) {
        newState.getGameMatt().makeBoardSquareSelectableForAbility();
        newState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(newState);
    }

    @Override
    void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewStateK newState) {
        int rerolledValue = ViewStateUtils.getDiceRollValue();
        newState.setSetAdapterToSelecting(false);
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, rerolledValue);
    }

}
