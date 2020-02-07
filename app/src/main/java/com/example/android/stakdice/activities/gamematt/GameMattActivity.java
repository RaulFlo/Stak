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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.adapter.BoardSquareAdapter;
import com.example.android.stakdice.dialogs.FailedDialog;
import com.example.android.stakdice.dialogs.PassedDialog;
import com.example.android.stakdice.R;
import com.example.android.stakdice.customviews.StakCardView;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.Attribute;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameMattActivity extends AppCompatActivity {

    private static String STAK_CARD_ID_EXTRA = "StakCardID";

    private GameMattViewModel gameMattViewModel;

    public static Intent newIntent(Context context, StakCard stakCard) {
        Intent intent = new Intent(context, GameMattActivity.class);
        intent.putExtra(STAK_CARD_ID_EXTRA, stakCard.getId());
        return intent;
    }


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

    private BoardSquareAdapter boardSquareAdapter;
    private List<BoardSquare> fakeBoardSquares = new ArrayList<>();
    private int lastDiceRolled = 0;


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
        RecyclerView columnsRv = findViewById(R.id.s_column_rv);


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

        boardSquareAdapter = new BoardSquareAdapter(new BoardSquareAdapter.Listener() {
            @Override
            public void onBoardSquareClicked(BoardSquare boardSquare) {
                // after they click, set the adapter to not selecting
                boardSquareAdapter.setSelecting(false);

                // do something with last dice rolled
                boardSquare.setDiceRollValue(lastDiceRolled);
                // update board square
                // update list given
            }
        });

        columnsRv.setAdapter(boardSquareAdapter);
        columnsRv.setLayoutManager(new GridLayoutManager(this, 4));

        // fake data -> should be defined in a class (class SimpleGameMatt(List<BoardSquare> boardSquares))
        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(false, 0));

        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(false, 0));
        fakeBoardSquares.add(new BoardSquare(true, 0));

        boardSquareAdapter.setBoardSquares(fakeBoardSquares);
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
        if (currentClicks == 10) {
            validateBtn.setVisibility(View.VISIBLE);
        }
    }


    private void isClicked() {

        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            revealValidateButton();
        } else {

            roundView.setText("Round: " + (currentClicks + 1));
            // update the dice roll
            lastDiceRolled = rollDice();
            // tell adapter we're selecting now
            boardSquareAdapter.setSelecting(true);

            currentClicks++;
        }

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
