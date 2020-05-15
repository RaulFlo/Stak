package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

abstract public class BaseBoardAction implements BoardAction {

    boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void onButtonClicked(MutableLiveData<GameMattViewState> mutableLiveData) {
        isActive = true;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewState newState = mutableLiveData.getValue().getAnExactCopy();
        modifyViewStateOnButtonClick(newState);
        mutableLiveData.postValue(newState);
    }

    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewState> mutableLiveData) {
        isActive = false;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewState newState = mutableLiveData.getValue().getAnExactCopy();
        modifyViewStateOnBoardSquareClicked(boardSquareClicked, newState);
        mutableLiveData.postValue(newState);
    }

    abstract void modifyViewStateOnButtonClick(GameMattViewState newState);

    abstract void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewState newState);
}
