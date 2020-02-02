package com.example.android.stakdice.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.android.stakdice.R;
import com.example.android.stakdice.models.StakCard;

public class StakAdapter extends ListAdapter<StakCard, StakHolder> implements StakHolder.StakHolderListener {

    //interface to be able to click on notes in recycle view
    public interface OnItemClickListener {
        void onItemClick(StakCard stakCard);
    }

    private OnItemClickListener listener;

    public StakAdapter(OnItemClickListener listener) {
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
                    oldItem.getImageResource() == (newItem.getImageResource()) &&
                    oldItem.getDifficulty().equals(newItem.getDifficulty());
        }
    };


    @NonNull
    @Override
    public StakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_listitem, parent, false);
        return new StakHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull StakHolder holder, int position) {
        StakCard currentStakCard = getItem(position);
        holder.mainMenuImage.setImageResource(currentStakCard.getImageResource());
        holder.mainMenuCardName.setText(currentStakCard.getCardName());
        holder.mainMenuDifficulty.setText(currentStakCard.getDifficulty());
    }


    public StakCard getStakAt(int position) {
        return getItem(position);
    }


    @Override
    public void onItemClicked(int adapterPosition) {
        listener.onItemClick(getStakAt(adapterPosition));
    }


}
