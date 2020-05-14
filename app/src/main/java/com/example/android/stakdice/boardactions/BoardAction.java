package com.example.android.stakdice.boardactions;

import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public interface BoardAction {
    void onButtonClicked(MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState);

    void onBoardSquareClicked(BoardSquare boardSquareClicked, MutableLiveData<GameMattViewStateK> mutableLiveData, GameMattViewStateK gameMattViewState);
}
