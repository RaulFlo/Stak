package com.example.android.stakdice.adapter.viewholder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BoardSquareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public interface Listener {
        void onBoardSquareClicked(int adapterPosition);
    }

    private Listener mListener;

    public BoardSquareViewHolder(@NonNull View itemView, Listener listener) {
        super(itemView);
        mListener = listener;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        mListener.onBoardSquareClicked(getAdapterPosition());
    }
}
