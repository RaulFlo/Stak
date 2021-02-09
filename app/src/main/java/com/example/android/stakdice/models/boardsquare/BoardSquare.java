package com.example.android.stakdice.models.boardsquare;

public class BoardSquare {

    private boolean isAvailableForSelecting = false;
    private int diceRollValue = 0;
    private boolean hasDiceValue;

    public BoardSquare(boolean _isAvailableForSelecting, int _diceRollValue, boolean _hasDiceValue) {
        isAvailableForSelecting = _isAvailableForSelecting;
        diceRollValue = _diceRollValue;
        hasDiceValue = _hasDiceValue;
    }

    public void setDiceRollValue(int _diceRoll) {
        diceRollValue = _diceRoll;
        hasDiceValue = _diceRoll > 0;
    }

    public int getDiceRollValue() {
        return diceRollValue;
    }

    public boolean getIsAvailableForSelecting() {
        return isAvailableForSelecting;
    }

    public void setIsAvailableForSelecting(boolean _isAvailableForSelecting) {
        isAvailableForSelecting = _isAvailableForSelecting;
    }

    public boolean getHasDiceValue() {
        return hasDiceValue;
    }

    public void setHasDiceValue(boolean _hasDiceValue) {
        this.hasDiceValue = _hasDiceValue;
    }
}
