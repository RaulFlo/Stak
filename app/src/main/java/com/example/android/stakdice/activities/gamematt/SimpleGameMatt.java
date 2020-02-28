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
        sBoardSquares.add(new BoardSquare(false, 0));
        sBoardSquares.add(new BoardSquare(false, 0));
        sBoardSquares.add(new BoardSquare(true, 0));

        tBoardSquares.add(new BoardSquare(false, 0));
        tBoardSquares.add(new BoardSquare(false, 0));
        tBoardSquares.add(new BoardSquare(true, 0));


        aBoardSquares.add(new BoardSquare(false, 0));
        aBoardSquares.add(new BoardSquare(false, 0));
        aBoardSquares.add(new BoardSquare(true, 0));


        kBoardSquares.add(new BoardSquare(false, 0));
        kBoardSquares.add(new BoardSquare(false, 0));
        kBoardSquares.add(new BoardSquare(true, 0));
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

    public enum StakColumn{
        NONE, STRENGTH, TOUGHNESS, AGILITY, KNOWLEDGE;
    }

    @Override
    public StakColumn returnLastColumn(BoardSquare boardSquare) {
        if (sBoardSquares.contains(boardSquare)) {
            return StakColumn.STRENGTH;
        } else if (tBoardSquares.contains(boardSquare)) {
            return StakColumn.TOUGHNESS;
        } else if (aBoardSquares.contains(boardSquare)) {
            return StakColumn.AGILITY;
        } else  if(kBoardSquares.contains(boardSquare)){
            return StakColumn.KNOWLEDGE;
        }
        return StakColumn.NONE;
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

}
