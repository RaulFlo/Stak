package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

abstract public class BaseBoardAction implements BoardAction {

    private boolean isActive = false;

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void onButtonClicked(MutableLiveData<GameMattViewStateK> mutableLiveData) {
        isActive = true;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = mutableLiveData.getValue().getAnExactCopy();
        modifyViewStateOnButtonClick(newState);
        mutableLiveData.postValue(newState);
    }

    @Override
    public void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData) {
        isActive = false;
        // not really needed but it's a bit more immutable so hopefully easier to debug
        GameMattViewStateK newState = mutableLiveData.getValue().getAnExactCopy();
        modifyViewStateOnBoardSquareClicked(boardSquareClicked, newState);
        mutableLiveData.postValue(newState);
    }

    abstract void modifyViewStateOnButtonClick(GameMattViewStateK newState);
    abstract void modifyViewStateOnBoardSquareClicked(BoardSquare boardSquareClicked, GameMattViewStateK newState);
}
