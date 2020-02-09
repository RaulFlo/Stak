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


        int diceValue = boardSquare.getDiceRollValue();
        setImageResource(diceValue,boardSquare);

    }

    @Override
    public void onClick(View v) {
        mListener.onBoardSquareClicked(getAdapterPosition());
    }

    private void setImageResource(int diceValue,BoardSquare boardSquare) {

        switch (diceValue) {
            case 1:
                boardSquare.setImageResource(R.drawable.dice1);
                break;
            case 2:
                boardSquare.setImageResource(R.drawable.dice2);
                break;
            case 3:
                boardSquare.setImageResource(R.drawable.dice3);
                break;
            case 4:
                boardSquare.setImageResource(R.drawable.dice4);
                break;
            case 5:
                boardSquare.setImageResource(R.drawable.dice5);
                break;
            case 6:
                boardSquare.setImageResource(R.drawable.dice6);
                break;
        }
    }
}
