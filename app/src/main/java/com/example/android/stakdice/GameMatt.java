package com.example.android.stakdice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

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


        StakCardView cardView = findViewById(R.id.stak_card_view);

        cardView.setStakCard(stakCard);


        imageViewDice = findViewById(R.id.image_view_dice);
        imageViewDice.setVisibility(View.INVISIBLE);
        rollButton = findViewById(R.id.button_roll);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewDice.setVisibility(View.VISIBLE);
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
