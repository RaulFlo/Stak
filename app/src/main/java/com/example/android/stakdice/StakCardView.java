package com.example.android.stakdice;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class StakCardView extends LinearLayout {

    private TextView cardName;
    private ImageView imageCard;
    private TextView stakDifficulty;
    private TextView attributeStrength;
    private TextView attributeToughness;
    private TextView attributeAgility;
    private TextView attributeKnowledge;

    public StakCardView(Context context) {
        super(context);
        init(context);
    }

    public StakCardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public StakCardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public StakCardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public void init(Context context) {
        View root = inflate(context, R.layout.stak_card_layout, this);
        cardName = root.findViewById(R.id.stak_view_card_name);
        imageCard = root.findViewById(R.id.image_stak_card);
        stakDifficulty = root.findViewById(R.id.stak_view_difficulty);
        attributeStrength = root.findViewById(R.id.stak_view_strength);
        attributeToughness = root.findViewById(R.id.stak_view_toughness);
        attributeAgility = root.findViewById(R.id.stak_view_agility);
        attributeKnowledge = root.findViewById(R.id.stak_view_knowledge);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setStakCard(StakCard card) {
        cardName.setText(card.getCardName());
        imageCard.setImageResource(card.getImageResource());
        stakDifficulty.setText(card.getDifficulty());

        attributeStrength.setText(card.getStrength().getDisplayString());
        attributeToughness.setText(card.getToughness().getDisplayString());
        attributeAgility.setText(card.getAgility().getDisplayString());
        attributeKnowledge.setText(card.getKnowledge().getDisplayString());


    }
}
