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

        int imageExtra = stakCard.getmImageResource();
        String nameExtra = stakCard.getmCardName();
        int strengthExtra = stakCard.getmStrength();
        int toughnessExtra = stakCard.getmToughness();
        int agilityExtra = stakCard.getmAgility();
        int knowledgeExtra = stakCard.getmKnowledge();


        //link views
        ImageView imageView = findViewById(R.id.image_Card);
        TextView textViewName = findViewById(R.id.text_view_card_name);
        TextView textViewStrength = findViewById(R.id.text_view_strength);
        TextView textViewToughness = findViewById(R.id.text_view_toughness);
        TextView textViewAgility= findViewById(R.id.text_view_agility);
        TextView textViewKnowledge = findViewById(R.id.text_view_knowledge);

        //link extras to views
        imageView.setImageResource(imageExtra);
        textViewName.setText(nameExtra);
        textViewStrength.setText(String.valueOf(strengthExtra));
        textViewToughness.setText(String.valueOf(toughnessExtra));
        textViewAgility.setText(String.valueOf(agilityExtra));
        textViewKnowledge.setText(String.valueOf(knowledgeExtra));


    }
}
