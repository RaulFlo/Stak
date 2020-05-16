package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.HashMap;

public class FlipAbilityBoardAction extends BaseBoardAction {
    private HashMap<Integer, Integer> flipMap = new HashMap<>();

    public FlipAbilityBoardAction() {
        flipMap.put(6, 1);
        flipMap.put(5, 2);
        flipMap.put(4, 3);
        flipMap.put(3, 4);
        flipMap.put(2, 5);
        flipMap.put(1, 6);
    }

    @Override
    GameMattViewState getButtonClickedViewState(GameMattViewState currentState) {
        currentState.setUndoButtonEnabled(false);
        //make boardsquare with dice values able to be selected
        currentState.getGameMatt().makeBoardSquareSelectableForAbility();
        currentState.setSetAdapterToSelecting(true);
        ViewStateUtils.disableAbilities(currentState);
        return currentState;
    }

    @Override
    GameMattViewState getBoardClickedViewState(BoardSquare boardSquareClicked, GameMattViewState currentState) {
        //save value of clicked boardsquare
        int intValueOfBs = boardSquareClicked.getDiceRollValue();

        //input int to method and receive new flipped int
        Integer flippedValue = flipMap.getOrDefault(intValueOfBs, 0);

        // after they click, set the adapter to not selecting
        currentState.setSetAdapterToSelecting(false);

        //update boardsquare with new flipped int
        currentState.getGameMatt().updateBoardSquare(boardSquareClicked, flippedValue);
        return currentState;
    }
}
