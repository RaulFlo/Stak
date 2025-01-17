package com.example.android.stakdice.customviews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.android.stakdice.R;
import com.example.android.stakdice.models.StakCard;

public class TrophyView extends LinearLayout {

    private TextView cardName;
    private ImageView imageView;
    private TextView difficulty;
    private TextView attributeStrength;
    private TextView attributeToughness;
    private TextView attributeAgility;
    private TextView attributeKnowledge;
    private TextView description;

    public TrophyView(Context context) {
        super(context);
        init(context);
    }


    public TrophyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TrophyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public TrophyView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        View root = inflate(context, R.layout.trophy_detail_custom_view, this);
        cardName = root.findViewById(R.id.trophy_room_detail_name);
        difficulty = root.findViewById(R.id.difficulty_detail_page);
        imageView = root.findViewById(R.id.trophy_room_detail_image);
        attributeStrength = findViewById(R.id.trophy_detail_strength);
        attributeToughness = findViewById(R.id.trophy_detail_toughness);
        attributeAgility = findViewById(R.id.trophy_detail_agility);
        attributeKnowledge = findViewById(R.id.trophy_detail_knowledge);
        description = findViewById(R.id.description_detail_page);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setStakCard(StakCard card) {
        cardName.setText(card.getCardName());

        Glide.with(cardName.getContext())
                .load(card.getImageResourceUrl())
                .into(imageView);

        difficulty.setText(card.getDifficulty());
        attributeStrength.setText(card.getStrength().getDisplayString());
        attributeToughness.setText(card.getToughness().getDisplayString());
        attributeAgility.setText(card.getAgility().getDisplayString());
        attributeKnowledge.setText(card.getKnowledge().getDisplayString());
        description.setText(card.getDescription());

    }


}
