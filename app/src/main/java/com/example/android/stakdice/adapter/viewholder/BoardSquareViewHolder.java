package com.example.android.stakdice.adapter.viewholder;

import android.graphics.Color;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class BoardSquareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public interface Listener {
        void onBoardSquareClicked(int adapterPosition);
    }

    private Listener mListener;


    public BoardSquareViewHolder(@NonNull View itemView, Listener listener) {
        super(itemView);
        mListener = listener;
    }

    public void bind(BoardSquare boardSquare, boolean isSelecting) {
        itemView.setOnClickListener(null);


        if (boardSquare.getIsAvailableForSelecting()) {
            if (isSelecting) {
                itemView.setBackgroundColor(Color.GREEN);
                itemView.setOnClickListener(this);
            } else {
                itemView.setBackgroundColor(Color.BLUE);
            }
        } else {
            itemView.setBackgroundColor(Color.BLACK);
        }


        getBackgroundDiceImage(boardSquare);


    }

    @Override
    public void onClick(View v) {
        mListener.onBoardSquareClicked(getAdapterPosition());
    }

    private void getBackgroundDiceImage(BoardSquare boardSquare) {
        int diceValue = boardSquare.getDiceRollValue();
       int resId= getImageRe(diceValue);
       itemView.findViewById(R.id.board_square_item).setBackgroundResource(resId);
    }

    private int getImageRe(int diceValue) {

        switch (diceValue) {
            case 1:
                return (R.drawable.dice1);
            case 2:
                return (R.drawable.dice2);
            case 3:
                return (R.drawable.dice3);
            case 4:
                return (R.drawable.dice4);
            case 5:
                return (R.drawable.dice5);
            case 6:
                return (R.drawable.dice6);
            default:
                return (R.drawable.ic_face_black_24dp);

        }

    }
}
