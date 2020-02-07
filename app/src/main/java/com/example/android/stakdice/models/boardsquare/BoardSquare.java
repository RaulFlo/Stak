package com.example.android.stakdice.models.boardsquare;

public class BoardSquare {

    private boolean isAvailableForSelecting = false;
    private int diceRollValue = 0;

    public BoardSquare(boolean _isAvailableForSelecting, int _diceRollValue) {
        isAvailableForSelecting = _isAvailableForSelecting;
        diceRollValue = _diceRollValue;
    }

    public void setDiceRollValue(int _diceRoll) {
        diceRollValue = _diceRoll;
    }

}
