package com.example.android.stakdice.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.stakdice.R;
import com.example.android.stakdice.StakCard;
import com.example.android.stakdice.StakCardView;
import com.example.android.stakdice.models.attribute.Attribute;

import java.util.Random;

public class GameMatt extends AppCompatActivity {


    private ImageView imageViewDice;
    private TextView roundView;
    private Random rng = new Random();
    private Button rollButton;
    private int maxClicks = 10;
    private int currentClicks = 0;
    private Button validateBtn;
    private EditText sEditText;
    private EditText tEditText;
    private EditText aEditText;
    private EditText kEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        Intent intent = getIntent();
        final StakCard stakCard = (StakCard) intent.getSerializableExtra("StakCard");


        StakCardView cardView = findViewById(R.id.stak_card_view);

        cardView.setStakCard(stakCard);

        //link views
        validateBtn = findViewById(R.id.debug_validate_btn);
        sEditText = findViewById(R.id.s_debug);
        tEditText = findViewById(R.id.t_debug);
        aEditText = findViewById(R.id.a_debug);
        kEditText = findViewById(R.id.k_debug);
        roundView = findViewById(R.id.game_matt_text_view_round);
        imageViewDice = findViewById(R.id.image_view_dice);
        imageViewDice.setVisibility(View.INVISIBLE);
        rollButton = findViewById(R.id.button_roll);

        validateBtn.setVisibility(View.INVISIBLE);

        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewDice.setVisibility(View.VISIBLE);
                isClicked();
            }
        });


        validateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isValid(stakCard);
            }
        });


    }


    private void isValid(StakCard stakCard) {
        Attribute stakCardStrength = stakCard.getStrength();
        Attribute stakCardToughness = stakCard.getToughness();
        Attribute stakCardAgility = stakCard.getAgility();
        Attribute stakCardKnowledge = stakCard.getKnowledge();

        int sValue = Integer.parseInt(sEditText.getText().toString());
        int tValue = Integer.parseInt(tEditText.getText().toString());
        int aValue = Integer.parseInt(aEditText.getText().toString());
        int kValue = Integer.parseInt(kEditText.getText().toString());


        if (stakCardStrength.isValid(sValue) && stakCardToughness.isValid(tValue)
                && stakCardAgility.isValid(aValue) && stakCardKnowledge.isValid(kValue)) {

            Toast.makeText(this, "Passed", Toast.LENGTH_SHORT).show();

            //Change the isBeaten on the card to true, commented out for now
            //stakCard.setBeaten(true);
        } else {
            Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show();
        }

    }

    public void revealValidateButton() {
        if (currentClicks == 10) {
            validateBtn.setVisibility(View.VISIBLE);
        }
    }


    private void isClicked() {

        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            revealValidateButton();
        } else {

            roundView.setText(String.valueOf(currentClicks + 1));
            rollDice();
            currentClicks++;
        }

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
