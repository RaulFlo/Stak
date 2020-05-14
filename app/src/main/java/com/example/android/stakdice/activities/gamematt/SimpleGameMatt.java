package com.example.android.stakdice.activities.gamematt;

import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameMatt implements GameMatt {

    private List<BoardSquare> sBoardSquares = new ArrayList<>();
    private List<BoardSquare> tBoardSquares = new ArrayList<>();
    private List<BoardSquare> aBoardSquares = new ArrayList<>();
    private List<BoardSquare> kBoardSquares = new ArrayList<>();

    public SimpleGameMatt() {
        sBoardSquares.add(new BoardSquare(false, 0, false));
        sBoardSquares.add(new BoardSquare(false, 0, false));
        sBoardSquares.add(new BoardSquare(true, 0, false));

        tBoardSquares.add(new BoardSquare(false, 0, false));
        tBoardSquares.add(new BoardSquare(false, 0, false));
        tBoardSquares.add(new BoardSquare(true, 0, false));


        aBoardSquares.add(new BoardSquare(false, 0, false));
        aBoardSquares.add(new BoardSquare(false, 0, false));
        aBoardSquares.add(new BoardSquare(true, 0, false));


        kBoardSquares.add(new BoardSquare(false, 0, false));
        kBoardSquares.add(new BoardSquare(false, 0, false));
        kBoardSquares.add(new BoardSquare(true, 0, false));
    }

    @Override
    public List<BoardSquare> getSColumnBoards() {
        return sBoardSquares;
    }

    @Override
    public List<BoardSquare> getTColumnBoards() {
        return tBoardSquares;
    }

    @Override
    public List<BoardSquare> getAColumnBoards() {
        return aBoardSquares;
    }

    @Override
    public List<BoardSquare> getKColumnBoards() {
        return kBoardSquares;
    }

    @Override
    public void updateBoardSquare(BoardSquare boardSquare, int diceValue) {
        if (sBoardSquares.contains(boardSquare)) {
            updateColumnData(sBoardSquares, boardSquare, diceValue);
        } else if (tBoardSquares.contains(boardSquare)) {
            updateColumnData(tBoardSquares, boardSquare, diceValue);
        } else if (aBoardSquares.contains(boardSquare)) {
            updateColumnData(aBoardSquares, boardSquare, diceValue);
        } else if (kBoardSquares.contains(boardSquare)) {
            updateColumnData(kBoardSquares, boardSquare, diceValue);
        }
    }

    @Override
    public void undoLastBoardSquare(BoardSquare boardSquare) {

        boardSquare.setDiceRollValue(0);

        boardSquare.setIsAvailableForSelecting(true);

        //check the boardsquare location and disable the top boardsquare
        if (sBoardSquares.contains(boardSquare)) {
            undoColumnData(sBoardSquares, boardSquare);
        } else if (tBoardSquares.contains(boardSquare)) {
            undoColumnData(tBoardSquares, boardSquare);
        } else if (aBoardSquares.contains(boardSquare)) {
            undoColumnData(aBoardSquares, boardSquare);
        } else if (kBoardSquares.contains(boardSquare)) {
            undoColumnData(kBoardSquares, boardSquare);
        }
    }

    public enum StakColumn {
        NONE, STRENGTH, TOUGHNESS, AGILITY, KNOWLEDGE;
    }

    @Override
    public StakColumn returnStakColumn(BoardSquare boardSquare) {
        if (sBoardSquares.contains(boardSquare)) {
            return StakColumn.STRENGTH;
        } else if (tBoardSquares.contains(boardSquare)) {
            return StakColumn.TOUGHNESS;
        } else if (aBoardSquares.contains(boardSquare)) {
            return StakColumn.AGILITY;
        } else if (kBoardSquares.contains(boardSquare)) {
            return StakColumn.KNOWLEDGE;
        }
        return StakColumn.NONE;
    }

    @Override
    public void makeBoardSquareSelectableForAbility() {
        //input List<BoardSquare> and check for diceValue, if so make selectable
        makeSelectableIfValueIsFound(sBoardSquares);
        makeSelectableIfValueIsFound(tBoardSquares);
        makeSelectableIfValueIsFound(aBoardSquares);
        makeSelectableIfValueIsFound(kBoardSquares);

    }

    @Override
    public void enableAllSelectableSquares() {
        makeBsWithValueAboveSelectable(sBoardSquares);
        makeBsWithValueAboveSelectable(tBoardSquares);
        makeBsWithValueAboveSelectable(aBoardSquares);
        makeBsWithValueAboveSelectable(kBoardSquares);
    }


    private void undoColumnData(List<BoardSquare> boardSquareListToModify, BoardSquare boardSquare) {


        // try to disable the one above this board square
        int indexOfModifiedBoardSquare = boardSquareListToModify.indexOf(boardSquare);
        int nextUpBoardSquareIndex = indexOfModifiedBoardSquare - 1;
        // if it's a valid index
        if (nextUpBoardSquareIndex >= 0) {
            // disable it
            boardSquareListToModify.get(nextUpBoardSquareIndex)
                    .setIsAvailableForSelecting(false);
        }

    }


    // called after one board square is clicked
    private void updateColumnData(List<BoardSquare> boardSquareListToModify, BoardSquare boardSquare, int lastDiceRolled) {

        // set the square to have the diced roll
        boardSquare.setDiceRollValue(lastDiceRolled);
        // make it so it's not selectable any more
        boardSquare.setIsAvailableForSelecting(false);

        // try to enable the one above this board square
        int indexOfModifiedBoardSquare = boardSquareListToModify.indexOf(boardSquare);
        int nextUpBoardSquareIndex = indexOfModifiedBoardSquare - 1;
        // if it's a valid index
        if (nextUpBoardSquareIndex >= 0) {
            // set it as available
            boardSquareListToModify.get(nextUpBoardSquareIndex)
                    .setIsAvailableForSelecting(true);
        }
    }

    //helper method for makeBoardSquareSelectable for abilities
    private void makeSelectableIfValueIsFound(List<BoardSquare> boardSquares) {

        //for each boardsquare in the List<BoardSquare> find out if it has a dice value, if yes
        //make it available for selecting
        for (BoardSquare bs : boardSquares
        ) {

            if (bs.getHasDiceValue() == true) {
                bs.setIsAvailableForSelecting(true);

            } else {

                bs.setIsAvailableForSelecting(false);

            }

        }
    }

    private void makeBsWithValueAboveSelectable(List<BoardSquare> boardSquares) {

        for (BoardSquare bs : boardSquares
        ) {
            if(boardSquares.indexOf(bs)== 2){
                if(bs.getHasDiceValue() == false){
                    bs.setIsAvailableForSelecting(true);
                }
            }

            if(bs.getHasDiceValue() == true){
                int indexOfModifiedBoardSquare  = boardSquares.indexOf(bs);
                int nextUpBoardSquareIndex = indexOfModifiedBoardSquare - 1;

                if (nextUpBoardSquareIndex >= 0) {
                    // set it as available
                    boardSquares.get(nextUpBoardSquareIndex)
                            .setIsAvailableForSelecting(true);
                }


            }


        }


    }


}
