package com.example.android.stakdice.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;

public class StakHolder extends RecyclerView.ViewHolder {

    public interface StakHolderListener {
        void onItemClicked(int adapterPosition);
    }

    private StakHolderListener listener;
    public ImageView mainMenuImage;
    public TextView mainMenuCardName;
    public TextView mainMenuDifficulty;

    public StakHolder(@NonNull View itemView, StakHolderListener constructorListener) {
        super(itemView);
        listener = constructorListener;

        mainMenuImage = itemView.findViewById(R.id.main_menu_image_view);
        mainMenuCardName = itemView.findViewById(R.id.main_menu_text_view_card_name);
        mainMenuDifficulty = itemView.findViewById(R.id.main_menu_text_view_difficulty);

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
