package com.example.android.stakdice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.adapter.viewholder.BoardSquareViewHolder;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.ArrayList;
import java.util.List;

public class BoardSquareAdapter extends RecyclerView.Adapter<BoardSquareViewHolder> implements BoardSquareViewHolder.Listener {

    public interface Listener {
        void onBoardSquareClicked(BoardSquare boardSquare);
    }

    private Listener mListener;
    private List<BoardSquare> boardSquareList = new ArrayList<>();
    private boolean isSelecting = false;

    public BoardSquareAdapter(Listener listener) {
        mListener = listener;
    }

    public void setBoardSquares(List<BoardSquare> newBoardSquares) {
        boardSquareList.addAll(newBoardSquares);
        notifyDataSetChanged();
    }

    public void setSelecting(boolean isSelecting) {
        this.isSelecting = isSelecting;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BoardSquareViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_board_square, parent, false);
        return new BoardSquareViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull BoardSquareViewHolder holder, int position) {
        BoardSquare boardSquare = boardSquareList.get(position);
        holder.bind(boardSquare, isSelecting);

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


