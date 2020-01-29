package com.example.android.stakdice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class GameMatt extends AppCompatActivity {

    private ImageView imageViewDice;
    private Random rng = new Random();
    private Button rollButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        Intent intent = getIntent();
        StakCard stakCard = (StakCard) intent.getSerializableExtra("StakCard");

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
        TextView textViewAgility = findViewById(R.id.stak_view_agility);
        TextView textViewKnowledge = findViewById(R.id.stak_view_knowledge);
        imageViewDice = findViewById(R.id.image_view_dice);
        rollButton = findViewById(R.id.button_roll);

        //link extras to views
        imageView.setImageResource(imageExtra);
        textViewName.setText(nameExtra);
        textViewStrength.setText(String.valueOf(strengthExtra));
        textViewToughness.setText(String.valueOf(toughnessExtra));
        textViewAgility.setText(String.valueOf(agilityExtra));
        textViewKnowledge.setText(String.valueOf(knowledgeExtra));

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rollDice();
            }
        });


    }

    private void rollDice() {
        int randomNumber = rng.nextInt(6) + 1;

        switch (randomNumber) {
            case 1:
                imageViewDice.setImageResource(R.drawable.dice1);
                break;
            case 2:
                imageViewDice.setImageResource(R.drawable.dice2);
                break;
            case 3:
                imageViewDice.setImageResource(R.drawable.dice3);
                break;
            case 4:
                imageViewDice.setImageResource(R.drawable.dice4);
                break;
            case 5:
                imageViewDice.setImageResource(R.drawable.dice5);
                break;
            case 6:
                imageViewDice.setImageResource(R.drawable.dice6);
                break;
        }
    }
}
