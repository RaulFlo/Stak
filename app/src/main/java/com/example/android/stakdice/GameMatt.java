package com.example.android.stakdice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class GameMatt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        Intent intent = getIntent();
      StakCard stakCard= (StakCard) intent.getSerializableExtra("StakCard");

        int imageExtra = stakCard.getImageResource();
        String nameExtra = stakCard.getCardName();
        int strengthExtra = stakCard.getStrength();
        int toughnessExtra = stakCard.getToughness();
        int agilityExtra = stakCard.getAgility();
        int knowledgeExtra = stakCard.getKnowledge();
        String difficultyExtra = stakCard.getDifficulty();
        boolean isBeatenExtra = stakCard.isBeaten();


        //link views
        ImageView imageView = findViewById(R.id.image_stak_card);
        TextView textViewName = findViewById(R.id.stak_view_card_name);
        TextView textViewStrength = findViewById(R.id.stak_view_strength);
        TextView textViewToughness = findViewById(R.id.stak_view_toughness);
        TextView textViewAgility= findViewById(R.id.stak_view_agility);
        TextView textViewKnowledge = findViewById(R.id.stak_view_knowledge);

        //link extras to views
        imageView.setImageResource(imageExtra);
        textViewName.setText(nameExtra);
        textViewStrength.setText(String.valueOf(strengthExtra));
        textViewToughness.setText(String.valueOf(toughnessExtra));
        textViewAgility.setText(String.valueOf(agilityExtra));
        textViewKnowledge.setText(String.valueOf(knowledgeExtra));


    }
}
