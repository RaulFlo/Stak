package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class MainRollUndoBoardAction extends BaseBoardAction {

    private int lastDiceRoll = 0;
    private BoardSquare lastBoardSquareClicked;

    @Override
    GameMattViewState getButtonClickedViewState(GameMattViewState currentState) {
        lastDiceRoll = ViewStateUtils.getDiceRollValue();
        // update round
        currentState.setRoundValue(currentState.getRoundValue() + 1);
        // disable abilities
        ViewStateUtils.disableAbilities(currentState);
        // disable undo button
        currentState.setUndoButtonEnabled(false);
        // enable adapter to select
        currentState.getGameMatt().enableAllSelectableSquares();
        // set new dice value
        currentState.setDiceRollValue(lastDiceRoll);
        // update dice image
        currentState.setDiceImageRes(ViewStateUtils.getDiceImageByRollAmount(lastDiceRoll));
        // update dice visibility to visible
        currentState.setDiceImageVisibility(true);
        currentState.setSetAdapterToSelecting(true);
        return currentState;
    }

    @Override
    GameMattViewState getBoardClickedViewState(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        lastBoardSquareClicked = boardSquareClicked;

        currentState.setUndoButtonEnabled(true);
        currentState.setRollButtonEnabled(true);
        currentState.setSetAdapterToSelecting(false);
        currentState.setDiceImageVisibility(false);
        currentState.getGameMatt().updateBoardSquare(boardSquareClicked, lastDiceRoll);
        SimpleGameMatt.StakColumn stakColumn = currentState.getGameMatt().returnStakColumn(boardSquareClicked);
        ViewStateUtils.enablePower(currentState, stakColumn);
        return currentState;
    }

    public GameMattViewState undoSettingSquare(GameMattViewState currentState) {
        if (lastBoardSquareClicked != null) {
            currentState.getGameMatt().updateBoardSquare(lastBoardSquareClicked, 0);
            lastBoardSquareClicked = null;
            currentState.setUndoButtonEnabled(false);
            ViewStateUtils.disableAbilities(currentState);
        }
        return currentState;
    }
}
