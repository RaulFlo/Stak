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
        sBoardSquares.add(new BoardSquare(false, 0));
        sBoardSquares.add(new BoardSquare(true, 0));

        tBoardSquares.add(new BoardSquare(false, 0));
        tBoardSquares.add(new BoardSquare(false, 0));
        tBoardSquares.add(new BoardSquare(false, 0));
        tBoardSquares.add(new BoardSquare(true, 0));

        aBoardSquares.add(new BoardSquare(false, 0));
        aBoardSquares.add(new BoardSquare(false, 0));
        aBoardSquares.add(new BoardSquare(false, 0));
        aBoardSquares.add(new BoardSquare(true, 0));

        kBoardSquares.add(new BoardSquare(false, 0));
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


    // called after one board square is clicked
    private void updateColumnData(List<BoardSquare> boardSquareListToModify, BoardSquare boardSquare, int lastDiceRolled) {

        // set the square to have the diced roll
        boardSquare.setDiceRollValue(lastDiceRolled);
        // make it so it's not selectable any more
        boardSquare.setIsAvailableForSelecting(false);

        // enable the one above this board square
        int indexOfModifiedBoardSquare = boardSquareListToModify.indexOf(boardSquare);

        int nextUpBoardSquareIndex = indexOfModifiedBoardSquare - 1;
        // is it valid
        if (nextUpBoardSquareIndex >= 0) {
            boardSquareListToModify.get(nextUpBoardSquareIndex).setIsAvailableForSelecting(true);
        }
    }

}
