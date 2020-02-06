package com.example.android.stakdice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.adapter.viewholder.BoardSquareViewHolder;
import com.example.android.stakdice.models.BoardSquare;

import java.util.ArrayList;
import java.util.List;

public class BoardSquareAdapter extends RecyclerView.Adapter<BoardSquareViewHolder> implements BoardSquareViewHolder.Listener {

    interface Listener {
        void onBoardSquareClicked(BoardSquare boardSquare);
    }

    private List<BoardSquare> boardSquareList = new ArrayList<>();
    private Listener mListener;

    public BoardSquareAdapter(Listener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BoardSquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_square, parent);
        return new BoardSquareViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardSquareViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return boardSquareList.size();
    }

    @Override
    public void onBoardSquareClicked(int adapterPosition) {
        mListener.onBoardSquareClicked(boardSquareList.get(adapterPosition));
    }
}


