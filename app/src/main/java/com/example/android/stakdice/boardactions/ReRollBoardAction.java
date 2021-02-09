package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class ReRollBoardAction extends BaseBoardAction {

    @Override
    GameMattViewState getButtonClickedViewState(GameMattViewState currentState) {
        currentState.getGameMatt().makeBoardSquareSelectableForAbility();
        currentState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(currentState);
        return currentState;
    }

    @Override
    GameMattViewState getBoardClickedViewState(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        int rerolledValue = ViewStateUtils.getDiceRollValue();
        currentState.setSetAdapterToSelecting(false);
        currentState.getGameMatt().updateBoardSquare(boardSquareClicked, rerolledValue);
        return currentState;
    }

}
