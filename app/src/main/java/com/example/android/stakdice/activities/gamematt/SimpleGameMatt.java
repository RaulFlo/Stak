package com.example.android.stakdice.activities.gamematt;

import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.ArrayList;
import java.util.List;

public class SimpleGameMatt implements GameMatt {

    private List<BoardSquare> boardSquares = new ArrayList<>();

    public SimpleGameMatt() {
        boardSquares.add(new BoardSquare(false, 0));
        boardSquares.add(new BoardSquare(false, 0));
        boardSquares.add(new BoardSquare(false, 0));
        boardSquares.add(new BoardSquare(false, 0));

        boardSquares.add(new BoardSquare(false, 0));
        boardSquares.add(new BoardSquare(true, 0));
        boardSquares.add(new BoardSquare(true, 0));
        boardSquares.add(new BoardSquare(true, 0));
    }

    @Override
    public List<BoardSquare> getInitialBoards() {
        return boardSquares;
    }

    // TODO: logic is a bit hacky/could be improved
    @Override
    public List<BoardSquare> updateBoardSquare(BoardSquare boardSquare) {

        int index = boardSquares.indexOf(boardSquare);
        if (index != -1) {
            int columnIndex = index % 4;
            int rowsIndex = (index / 4);

            // try to go up the column
            int rowsIndexAbove = rowsIndex - 1;
            if (rowsIndexAbove >= 0) {
                // convert to real index
                int boardSquareAboveIndex = getIndex(columnIndex, rowsIndexAbove, 4);
                // get from the current list
                BoardSquare boardSquareAbove = boardSquares.get(boardSquareAboveIndex);
                // modify it in the list
                modifySquareAboveTheSelectedSquare(boardSquare);
            }
        }
        return boardSquares;
    }


    private int getIndex(int column, int row, int columnSize) {
        return row * columnSize + column;
    }

    // called after a square below is selected
    private void modifySquareAboveTheSelectedSquare(BoardSquare boardSquare) {
        boardSquare.setIsAvailableForSelecting(true);
    }
}
