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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.adapter.BoardSquareAdapter;
import com.example.android.stakdice.customviews.StakCardView;
import com.example.android.stakdice.dialogs.FailedDialog;
import com.example.android.stakdice.dialogs.PassedDialog;
import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.attribute.Attribute;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.Random;

public class GameMattActivity extends AppCompatActivity implements BoardSquareAdapter.Listener {

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

    private BoardSquareAdapter sBoardSquareAdapter;
    private BoardSquareAdapter tBoardSquareAdapter;
    private BoardSquareAdapter aBoardSquareAdapter;
    private BoardSquareAdapter kBoardSquareAdapter;
    GameMatt matt = new SimpleGameMatt(); // different matts for diff creatures
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
        RecyclerView sColumnsRv = findViewById(R.id.s_column_rv);
        RecyclerView tColumnsRv = findViewById(R.id.t_column_rv);
        RecyclerView aColumnsRv = findViewById(R.id.a_column_rv);
        RecyclerView kColumnsRv = findViewById(R.id.k_column_rv);


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
                        validateCard(stakCard);
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

        sBoardSquareAdapter = new BoardSquareAdapter(this);
        tBoardSquareAdapter = new BoardSquareAdapter(this);
        aBoardSquareAdapter = new BoardSquareAdapter(this);
        kBoardSquareAdapter = new BoardSquareAdapter(this);


        sColumnsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        sColumnsRv.setNestedScrollingEnabled(false);

        tColumnsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        tColumnsRv.setNestedScrollingEnabled(false);

        aColumnsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        aColumnsRv.setNestedScrollingEnabled(false);

        kColumnsRv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        kColumnsRv.setNestedScrollingEnabled(false);

        sColumnsRv.setAdapter(sBoardSquareAdapter);
        tColumnsRv.setAdapter(tBoardSquareAdapter);
        aColumnsRv.setAdapter(aBoardSquareAdapter);
        kColumnsRv.setAdapter(kBoardSquareAdapter);

        setAdaptersFromGameMatt(matt);

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
            hideDiceImage();
        }
    }


    private void isClicked() {

        //update textview
        updateViewTotal();

        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            revealValidateButton();
        } else {

            roundView.setText("Round: " + (currentClicks + 1));


            // update the dice roll
            lastDiceRolled = rollDice();
            // tell adapters we're selecting now
            updateColumnAdaptersToSelecting(true);

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


    @Override
    public void onBoardSquareClicked(BoardSquare boardSquare) {
        // after they click, set the adapter to not selecting
        updateColumnAdaptersToSelecting(false);

        //hide Image
        hideDiceImage();

        // update the board
        matt.updateBoardSquare(boardSquare, lastDiceRolled);

        // update the adapters
        setAdaptersFromGameMatt(matt);
    }

    private void updateColumnAdaptersToSelecting(boolean isSelecting) {
        sBoardSquareAdapter.setSelecting(isSelecting);
        tBoardSquareAdapter.setSelecting(isSelecting);
        aBoardSquareAdapter.setSelecting(isSelecting);
        kBoardSquareAdapter.setSelecting(isSelecting);
    }

    private void setAdaptersFromGameMatt(GameMatt gameMatt) {
        sBoardSquareAdapter.setBoardSquares(gameMatt.getSColumnBoards());
        tBoardSquareAdapter.setBoardSquares(gameMatt.getTColumnBoards());
        aBoardSquareAdapter.setBoardSquares(gameMatt.getAColumnBoards());
        kBoardSquareAdapter.setBoardSquares(gameMatt.getKColumnBoards());
    }

    private void validateCard(StakCard stakCard) {
        updateViewTotal();

        Attribute stakCardStrength = stakCard.getStrength();
        Attribute stakCardToughness = stakCard.getToughness();
        Attribute stakCardAgility = stakCard.getAgility();
        Attribute stakCardKnowledge = stakCard.getKnowledge();

        int sValue = sBoardSquareAdapter.getColumnSum();
        int tValue = tBoardSquareAdapter.getColumnSum();
        int aValue = aBoardSquareAdapter.getColumnSum();
        int kValue = kBoardSquareAdapter.getColumnSum();


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

    private void updateViewTotal(){
        int sSumTotal = sBoardSquareAdapter.getColumnSum();
        int tSumTotal = tBoardSquareAdapter.getColumnSum();
        int aSumTotal = aBoardSquareAdapter.getColumnSum();
        int kSumTotal = kBoardSquareAdapter.getColumnSum();

        sEditText.setText(String.valueOf(sSumTotal));
        tEditText.setText(String.valueOf(tSumTotal));
        aEditText.setText(String.valueOf(aSumTotal));
        kEditText.setText(String.valueOf(kSumTotal));
    }

    private void hideDiceImage(){
        imageViewDice.setVisibility(View.INVISIBLE);
    }
}
