package com.example.android.stakdice.boardactions;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.models.GameMattViewStateK;
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
        Integer flippedValue = flipMap.getOrDefault(intValueOfBs, 0);

        // after they click, set the adapter to not selecting
        newState.setSetAdapterToSelecting(false);

        //update boardsquare with new flipped int
        newState.getGameMatt().updateBoardSquare(boardSquareClicked, flippedValue);
    }
}
