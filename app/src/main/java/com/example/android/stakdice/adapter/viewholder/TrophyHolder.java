package com.example.android.stakdice.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;

public class TrophyHolder extends RecyclerView.ViewHolder {

    public interface TrophyHolderListener {
        void onItemClicked(int adapterPosition);
    }

    private TrophyHolderListener listener;
    public ImageView trophyRoomImage;
    public TextView trophyRoomName;


    public TrophyHolder(@NonNull View itemView, TrophyHolderListener constructorListener) {
        super(itemView);
        listener = constructorListener;

        trophyRoomImage = itemView.findViewById(R.id.trophy_room_item_image);
        trophyRoomName = itemView.findViewById(R.id.trophy_room_item_name);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClicked(position);
                }
            }
        });
    }
}
