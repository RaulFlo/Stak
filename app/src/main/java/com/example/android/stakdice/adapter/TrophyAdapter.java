package com.example.android.stakdice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.android.stakdice.R;
import com.example.android.stakdice.models.StakCard;

public class TrophyAdapter extends ListAdapter<StakCard, TrophyHolder> implements TrophyHolder.TrophyHolderListener {


    //interface to be able to click on notes in recycle view
    public interface OnItemClickListener {
        void onItemClick(StakCard stakCard);
    }

    private OnItemClickListener listener;

    public TrophyAdapter(OnItemClickListener listener) {
        super(DIFF_CALLBACK);
        this.listener = listener;
    }


    private static final DiffUtil.ItemCallback<StakCard> DIFF_CALLBACK = new DiffUtil.ItemCallback<StakCard>() {
        @Override
        public boolean areItemsTheSame(@NonNull StakCard oldItem, @NonNull StakCard newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull StakCard oldItem, @NonNull StakCard newItem) {
            return oldItem.getCardName().equals(newItem.getCardName()) &&
                    oldItem.getImageResourceUrl() == (newItem.getImageResourceUrl());

        }
    };


    @NonNull
    @Override
    public TrophyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trophy_item, parent, false);
        return new TrophyHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull TrophyHolder holder, int position) {
        StakCard currentStakCard = getItem(position);
        Glide.with(holder.itemView.getContext())
                .load(currentStakCard.getImageResourceUrl())
                .into(holder.trophyRoomImage);
        holder.trophyRoomName.setText(currentStakCard.getCardName());

    }

    public StakCard getStakAt(int position) {
        return getItem(position);
    }

    @Override
    public void onItemClicked(int adapterPosition) {
        listener.onItemClick(getStakAt(adapterPosition));
    }
}
