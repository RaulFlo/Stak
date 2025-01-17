package com.example.android.stakdice.activities.gamematt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
    private Button undoBtn;
    private Button switchBtn;
    private Button upDownBtn;
    private Button flipBtn;
    private Button abilityUpBtn;
    private Button abilityDownBtn;
    private boolean isFlipBtnClicked;
    private boolean isReRollBtnClicked;
    private boolean upDownBtnClicked;
    private Button reRollBtn;

    private boolean isUpBtnPressed;
    private boolean isDownBtnPressed;


    private TextView sViewText;
    private TextView tViewText;
    private TextView aViewText;
    private TextView kViewText;


    private BoardSquareAdapter sBoardSquareAdapter;
    private BoardSquareAdapter tBoardSquareAdapter;
    private BoardSquareAdapter aBoardSquareAdapter;
    private BoardSquareAdapter kBoardSquareAdapter;
    GameMatt matt = new SimpleGameMatt(); // different matts for diff creatures
    private int lastDiceRolled = 0;

    private BoardSquare lastBoardSquare; // for undo button


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);

        final StakCardView cardView = findViewById(R.id.stak_card_view);
        //link views
        validateBtn = findViewById(R.id.debug_validate_btn);
        undoBtn = findViewById(R.id.button_undo);
        switchBtn = findViewById(R.id.btn_view_switch);
        upDownBtn = findViewById(R.id.btn_view_up_down);
        flipBtn = findViewById(R.id.btn_view_flip);
        reRollBtn = findViewById(R.id.btn_view_reroll);
        abilityUpBtn = findViewById(R.id.ability_up_button);
        abilityDownBtn = findViewById(R.id.ability_down_button);

        sViewText = findViewById(R.id.s_debug);
        tViewText = findViewById(R.id.t_debug);
        aViewText = findViewById(R.id.a_debug);
        kViewText = findViewById(R.id.k_debug);
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

                //disable ability buttons
                disableAbilityBtns();

                rollBtnClicked();
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //set rollBtn to false
                rollButton.setEnabled(false);

                // disable ability btn
                disableAbilityBtns();

                //make dice image reappear with last dice shown
                imageViewDice.setVisibility(View.VISIBLE);

                //set last boardsquare to 0 and make it able to be available for selecting
                matt.undoLastBoardSquare(lastBoardSquare);


                // update the adapters
                setAdaptersFromGameMatt(matt);

                // make boardsquare able to be selected
                updateColumnAdaptersToSelecting(true);

                //update boolean that boardsquare does not have a dice value anymore
                lastBoardSquare.setHasDiceValue(false);
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoBtn.setEnabled(false);

                //make boardsquare with dice values able to be selected
                matt.makeBoardSquareSelectableForAbility();

                //make boardsquare able to be selectable
                updateColumnAdaptersToSelecting(true);

                //disable all Ability buttons
                disableAbilityBtns();

                switchAbility();
            }
        });

        upDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoBtn.setEnabled(false);

                //make boardsquare with dice values able to be selected
                matt.makeBoardSquareSelectableForAbility();

                //make boardsquare able to be selectable
                updateColumnAdaptersToSelecting(true);

                //disable all Ability buttons
                disableAbilityBtns();


                abilityUpBtn.setVisibility(View.VISIBLE);
                abilityDownBtn.setVisibility(View.VISIBLE);


                abilityUpBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isUpBtnPressed = true;
                    }
                });

                abilityDownBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isDownBtnPressed = true;
                    }
                });

                upDownBtnClicked = true;

            }
        });

        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoBtn.setEnabled(false);

                //make boardsquare with dice values able to be selected
                matt.makeBoardSquareSelectableForAbility();

                //make boardsquare able to be selectable
                updateColumnAdaptersToSelecting(true);

                //update adapters
                setAdaptersFromGameMatt(matt);

                //disable all Ability buttons
                disableAbilityBtns();

                isFlipBtnClicked = true;

            }
        });

        reRollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                undoBtn.setEnabled(false);

                //make boardsquare with dice values able to be selected
                matt.makeBoardSquareSelectableForAbility();

                //make boardsquare able to be selectable
                updateColumnAdaptersToSelecting(true);

                //update adapters
                setAdaptersFromGameMatt(matt);

                //disable all Ability buttons
                disableAbilityBtns();

                isReRollBtnClicked = true;
            }
        });


        //link adapters
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


    private void rollBtnClicked() {

        //update textview
        updateViewTotal();

        matt.enableAllSelectableSquares();
        setAdaptersFromGameMatt(matt);

        if (currentClicks == maxClicks) {
            rollButton.setEnabled(false);
            rollButton.setVisibility(View.GONE);
            revealValidateButton();
        } else {

            roundView.setText("Round: " + (currentClicks + 1));


            // update the dice roll
            lastDiceRolled = rollDice();
            // tell adapters we're selecting now
            updateColumnAdaptersToSelecting(true);

            currentClicks++;
        }

        //undo btn set to false after every round
        undoBtn.setEnabled(false);

        //set false cant go to next round till a boardsquare is selcted
        rollButton.setEnabled(false);

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

    private void columnPicked() {

        SimpleGameMatt.StakColumn lastColumn = matt.returnLastColumn(lastBoardSquare);

        switch (lastColumn) {
            case STRENGTH:
                switchBtn.setEnabled(true);
                break;
            case TOUGHNESS:
                upDownBtn.setEnabled(true);
                break;
            case AGILITY:
                flipBtn.setEnabled(true);
                break;
            case KNOWLEDGE:
                reRollBtn.setEnabled(true);
                break;
        }
    }


    @Override
    public void onBoardSquareClicked(BoardSquare boardSquare) {

        if (isFlipBtnClicked == true) {


            Toast.makeText(this, "FLIP!", Toast.LENGTH_SHORT).show();

            //save value of clicked boardsquare
            int intValueOfBs = boardSquare.getDiceRollValue();

            //input int to method and receive new flipped int
            int flippedValue = flipAbility(intValueOfBs);

            // after they click, set the adapter to not selecting
            updateColumnAdaptersToSelecting(false);

            // update the adapters
            setAdaptersFromGameMatt(matt);

            //update boardsquare with new flipped int
            matt.updateBoardSquare(boardSquare, flippedValue);


            isFlipBtnClicked = false;

        } else if (isReRollBtnClicked == true) {

            Toast.makeText(this, "REROLL!!", Toast.LENGTH_SHORT).show();


            //input int to method and receive new reroll int
            int reRolledValue = rollDice();

            // after they click, set the adapter to not selecting
            updateColumnAdaptersToSelecting(false);

            // update the adapters
            setAdaptersFromGameMatt(matt);

            //update boardsquare with new flipped int
            matt.updateBoardSquare(boardSquare, reRolledValue);


            isReRollBtnClicked = false;


        } else if (upDownBtnClicked == true) {

            if(isUpBtnPressed == true || isDownBtnPressed == true) {
                //save value of clicked boardsquare
                int intValueOfBs = boardSquare.getDiceRollValue();

                //input int to method and receive new flipped int
                int upOrDownValue = upDownAbility(intValueOfBs);


                // after they click, set the adapter to not selecting
                updateColumnAdaptersToSelecting(false);

                // update the adapters
                setAdaptersFromGameMatt(matt);

                //update boardsquare with new flipped int
                matt.updateBoardSquare(boardSquare, upOrDownValue);

                abilityUpBtn.setVisibility(View.GONE);
                abilityDownBtn.setVisibility(View.GONE);


                upDownBtnClicked = false;
                isUpBtnPressed = false;
                isDownBtnPressed = false;
            }

        } else {


            //make rollBtn able to go to next round
            rollButton.setEnabled(true);

            //enable undo btn
            undoBtn.setEnabled(true);

            // after they click, set the adapter to not selecting
            updateColumnAdaptersToSelecting(false);

            //update boolean that boardsquare has a dice value
            boardSquare.setHasDiceValue(true);

            //hide Image
            hideDiceImage();

            lastBoardSquare = boardSquare;

            // update the board
            matt.updateBoardSquare(boardSquare, lastDiceRolled);

            //column picked
            columnPicked();

            // update the adapters
            setAdaptersFromGameMatt(matt);
        }


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

    private void switchAbility() {

        //TODO: SWITCH
        Toast.makeText(GameMattActivity.this, "SWITCH", Toast.LENGTH_SHORT).show();
        disableAbilityBtns();
    }

    private int upDownAbility( int diceValueForAbility) {


        if (isUpBtnPressed == true) {
            if (diceValueForAbility == 6) {
                Toast.makeText(this, "Not Allowed", Toast.LENGTH_SHORT).show();
                return diceValueForAbility;
            }
            return diceValueForAbility + 1;
        } else if (isDownBtnPressed == true) {
            if (diceValueForAbility == 1) {
                Toast.makeText(this, "Not Allowed", Toast.LENGTH_SHORT).show();
                return diceValueForAbility;
            }
            return diceValueForAbility - 1;
        }


        return diceValueForAbility;
    }

    private int flipAbility(int diceValueForAbility) {


        switch (diceValueForAbility) {
            case 6:
                return 1;
            case 5:
                return 2;

            case 4:
                return 3;

            case 3:
                return 4;

            case 2:
                return 5;

            case 1:
                return 6;


        }
        return 0;

    }


    private void updateViewTotal() {
        int sSumTotal = sBoardSquareAdapter.getColumnSum();
        int tSumTotal = tBoardSquareAdapter.getColumnSum();
        int aSumTotal = aBoardSquareAdapter.getColumnSum();
        int kSumTotal = kBoardSquareAdapter.getColumnSum();

        sViewText.setText(String.valueOf(sSumTotal));
        tViewText.setText(String.valueOf(tSumTotal));
        aViewText.setText(String.valueOf(aSumTotal));
        kViewText.setText(String.valueOf(kSumTotal));
    }

    private void hideDiceImage() {
        imageViewDice.setVisibility(View.INVISIBLE);
    }

    private void disableAbilityBtns() {
        switchBtn.setEnabled(false);
        upDownBtn.setEnabled(false);
        flipBtn.setEnabled(false);
        reRollBtn.setEnabled(false);
    }
}
