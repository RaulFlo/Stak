package com.example.android.stakdice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StakAdapter extends ListAdapter<StakCard, StakAdapter.StakHolder> {

    private static OnItemClickListener listener;

    public StakAdapter() {
        super(DIFF_CALLBACK);


    }

    private static final DiffUtil.ItemCallback<StakCard> DIFF_CALLBACK = new DiffUtil.ItemCallback<StakCard>() {
        @Override
        public boolean areItemsTheSame(@NonNull StakCard oldItem, @NonNull StakCard newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StakCard oldItem, @NonNull StakCard newItem) {
            return oldItem.getCardName().equals(newItem.getCardName()) &&
                    oldItem.getImageResource() == (newItem.getImageResource()) &&
                    oldItem.getDifficulty().equals(newItem.getDifficulty());
        }
    };


    @NonNull
    @Override
    public StakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);
        return new StakHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull StakHolder holder, int position) {
        StakCard currentStakCard = getItem(position);
        holder.mainMenuImage.setImageResource(currentStakCard.getImageResource());
        holder.mainMenuCardName.setText(currentStakCard.getCardName());
        holder.mainMenuDifficulty.setText(currentStakCard.getDifficulty());


    }


    public StakCard getStakAt(int position){
        return getItem(position);
    }


    class StakHolder extends RecyclerView.ViewHolder {

        public ImageView mainMenuImage;
        public TextView mainMenuCardName;
        public TextView mainMenuDifficulty;

        public StakHolder(@NonNull View itemView) {
            super(itemView);

            mainMenuImage = itemView.findViewById(R.id.main_menu_image_view);
            mainMenuCardName = itemView.findViewById(R.id.main_menu_text_view_card_name);
            mainMenuDifficulty = itemView.findViewById(R.id.main_menu_text_view_difficulty);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    //interface to be able to click on notes in recycle view
    public interface OnItemClickListener {
        void onItemClick(StakCard stakCard);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}
