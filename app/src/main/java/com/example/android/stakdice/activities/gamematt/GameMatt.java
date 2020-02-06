package com.example.android.stakdice.activities.gamematt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.stakdice.R;
import com.example.android.stakdice.customviews.StakCardView;
import com.example.android.stakdice.dialogs.FailedDialog;
import com.example.android.stakdice.dialogs.PassedDialog;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.Attribute;

import java.util.Random;

public class GameMatt extends AppCompatActivity {

    private static String STAK_CARD_ID_EXTRA = "StakCardID";

    private GameMattViewModel gameMattViewModel;

    public static Intent newIntent(Context context, StakCard stakCard) {
        Intent intent = new Intent(context, GameMatt.class);
        intent.putExtra(STAK_CARD_ID_EXTRA, stakCard.getId());
        return intent;
    }


    private ImageView imageViewDice;
    private TextView roundView;
    private Random rng = new Random();
    private Button rollButton;
    private int maxClicks = 9;
    private int currentClicks = 0;
    private Button validateBtn;
    private EditText sEditText;
    private EditText tEditText;
    private EditText aEditText;
    private EditText kEditText;
    private TextView sTop, sMid, sBot;
    private TextView tTop,tMid, tBot;
    private TextView aTop,aMid, aBot;
    private TextView kTop,kMid, kBot;
    private Button pullButton,tPullButton, aPullButton,kPullButton;

    private int diceResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        final StakCardView cardView = findViewById(R.id.stak_card_view);
        //link views
        validateBtn = findViewById(R.id.debug_validate_btn);
        sEditText = findViewById(R.id.s_debug);
        tEditText = findViewById(R.id.t_debug);
        aEditText = findViewById(R.id.a_debug);
        kEditText = findViewById(R.id.k_debug);
        roundView = findViewById(R.id.game_matt_text_view_round);
        imageViewDice = findViewById(R.id.image_view_dice);
        rollButton = findViewById(R.id.button_roll);
        pullButton = findViewById(R.id.pull_result);
        tPullButton = findViewById(R.id.t_pull_result);
        aPullButton = findViewById(R.id.a_pull_result);
        kPullButton = findViewById(R.id.k_pull_result);
        sTop = findViewById(R.id.s_input_top);
        sMid = findViewById(R.id.s_input_middle);
        sBot = findViewById(R.id.s_input_bottom);

        tTop = findViewById(R.id.t_input_top);
        tMid = findViewById(R.id.t_input_middle);
        tBot = findViewById(R.id.t_input_bottom);

        aTop = findViewById(R.id.a_input_top);
        aMid = findViewById(R.id.a_input_middle);
        aBot = findViewById(R.id.a_input_bottom);

        kTop = findViewById(R.id.k_input_top);
        kMid = findViewById(R.id.k_input_middle);
        kBot = findViewById(R.id.k_input_bottom);



        //get intent
        Intent intent = getIntent();
        final String stakCardId = intent.getStringExtra(STAK_CARD_ID_EXTRA);


        //ask sys for new ViewModel, scopes it to this activity and destroys when this activity destroyed
        gameMattViewModel = ViewModelProviders.of(this).get(GameMattViewModel.class);

        gameMattViewModel.getSingleStak(stakCardId).observe(this, new Observer<StakCard>() {
            @Override
            public void onChanged(final StakCard stakCard) {
                stakCard.getCardName();
                //set current stakCard to view
                cardView.setStakCard(stakCard);

                validateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isValid(stakCard);
                    }
                });
            }
        });

        //Visibility
        imageViewDice.setVisibility(View.INVISIBLE);
        validateBtn.setVisibility(View.INVISIBLE);


        //buttons
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageViewDice.setVisibility(View.VISIBLE);
                isClicked();
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
            openPassDialog();

            //Change the isBeaten on the card to true, commented out for now
            stakCard.setBeaten(true);
            gameMattViewModel.update(stakCard);
        } else {
            openFailDialog();
        }

    }

    private void openFailDialog() {
        FailedDialog failedDialog = new FailedDialog();
        failedDialog.show(getSupportFragmentManager(), "failed dialog");
    }

    private void openPassDialog() {
        PassedDialog passedDialog = new PassedDialog();
        passedDialog.show(getSupportFragmentManager(), "passed dialog");

    }

    public void revealValidateButton() {
        if (currentClicks == 9) {
            validateBtn.setVisibility(View.VISIBLE);
        }
    }


    private void isClicked() {
        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            revealValidateButton();
        } else {

            roundView.setText("Round: " + (currentClicks + 1));
            diceResult = rollDice();
            currentClicks++;
            pullStrengthResult(diceResult);
            pullToughnessResult(diceResult);
            pullAgilityResult(diceResult);
            pullKnowledgeResult(diceResult);

        }

    }

    private void pullKnowledgeResult(final int diceResult) {
        kPullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (kBot.getText().toString().equals("")) {
                    kBot.setText("" + diceResult);
                    int value = (Integer.parseInt(kBot.getText().toString()));
                    kEditText.setText(String.valueOf(value));
                } else if (kMid.getText().toString().equals("")) {
                    kMid.setText("" + diceResult);
                    int value = (Integer.parseInt(kBot.getText().toString()) + Integer.parseInt(kMid.getText().toString()));
                    kEditText.setText(String.valueOf(value));
                } else if (kTop.getText().toString().equals("")) {
                    kTop.setText("" + diceResult);
                    int value = (Integer.parseInt(kBot.getText().toString()) + Integer.parseInt(kMid.getText().toString())
                            + Integer.parseInt(kTop.getText().toString()));
                    kEditText.setText(String.valueOf(value));

                }
            }
        });
    }

    private void pullAgilityResult(final int diceResult) {
        aPullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aBot.getText().toString().equals("")) {
                    aBot.setText("" + diceResult);
                    int value = (Integer.parseInt(aBot.getText().toString()));
                    aEditText.setText(String.valueOf(value));
                } else if (aMid.getText().toString().equals("")) {
                    aMid.setText("" + diceResult);
                    int value = (Integer.parseInt(aBot.getText().toString()) + Integer.parseInt(aMid.getText().toString()));
                    aEditText.setText(String.valueOf(value));
                } else if (aTop.getText().toString().equals("")) {
                    aTop.setText("" + diceResult);
                    int value = (Integer.parseInt(aBot.getText().toString()) + Integer.parseInt(aMid.getText().toString())
                            + Integer.parseInt(aTop.getText().toString()));
                    aEditText.setText(String.valueOf(value));

                }
            }
        });
    }

    private void pullToughnessResult(final int diceResult) {
        tPullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tBot.getText().toString().equals("")) {
                    tBot.setText("" + diceResult);
                    int value = (Integer.parseInt(tBot.getText().toString()));
                    tEditText.setText(String.valueOf(value));
                } else if (tMid.getText().toString().equals("")) {
                    tMid.setText("" + diceResult);
                    int value = (Integer.parseInt(tBot.getText().toString()) + Integer.parseInt(tMid.getText().toString()));
                    tEditText.setText(String.valueOf(value));
                } else if (tTop.getText().toString().equals("")) {
                    tTop.setText("" + diceResult);
                    int value = (Integer.parseInt(tBot.getText().toString()) + Integer.parseInt(tMid.getText().toString())
                            + Integer.parseInt(tTop.getText().toString()));
                    tEditText.setText(String.valueOf(value));

                }
            }
        });
    }


    private void pullStrengthResult(final int diceResult) {
        pullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sBot.getText().toString().equals("")) {
                    sBot.setText("" + diceResult);
                    int value = (Integer.parseInt(sBot.getText().toString()));
                    sEditText.setText(String.valueOf(value));
                } else if (sMid.getText().toString().equals("")) {
                    sMid.setText("" + diceResult);
                    int value = (Integer.parseInt(sBot.getText().toString()) + Integer.parseInt(sMid.getText().toString()));
                    sEditText.setText(String.valueOf(value));
                } else if (sTop.getText().toString().equals("")) {
                    sTop.setText("" + diceResult);
                    int value = (Integer.parseInt(sBot.getText().toString()) + Integer.parseInt(sMid.getText().toString())
                            + Integer.parseInt(sTop.getText().toString()));
                    sEditText.setText(String.valueOf(value));

                }
            }
        });
    }


    private int rollDice() {
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
        return randomNumber;
    }
}
