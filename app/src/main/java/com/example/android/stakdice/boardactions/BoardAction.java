package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public interface BoardAction {

    boolean isActive();

    void onButtonClicked(MutableLiveData<GameMattViewState> mutableLiveData);

    void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewState> mutableLiveData);
}

