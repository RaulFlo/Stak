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
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

public class GameMattActivity extends AppCompatActivity implements BoardSquareAdapter.Listener {

    private static String STAK_CARD_ID_EXTRA = "StakCardID";

    private GameMattViewModel viewModel;

    public static Intent newIntent(Context context, StakCard stakCard) {
        Intent intent = new Intent(context, GameMattActivity.class);
        intent.putExtra(STAK_CARD_ID_EXTRA, stakCard.getId());
        return intent;
    }

    private ImageView imageViewDice;
    private TextView roundView;
    private Button rollButton;
    private StakCardView stakCardView;
    private Button validateBtn;
    private Button undoBtn;
    private Button switchBtn;
    private Button upDownBtn;
    private Button flipBtn;
    private Button abilityUpBtn;
    private Button abilityDownBtn;
    private Button reRollBtn;
    private TextView sViewText;
    private TextView tViewText;
    private TextView aViewText;
    private TextView kViewText;

    private BoardSquareAdapter sBoardSquareAdapter;
    private BoardSquareAdapter tBoardSquareAdapter;
    private BoardSquareAdapter aBoardSquareAdapter;
    private BoardSquareAdapter kBoardSquareAdapter;
    GameMatt matt = new SimpleGameMatt(); // different matts for diff creatures
    private int maxClicks = 10;
    private int currentClicks = 0;
    private boolean isFlipBtnClicked;
    private boolean upDownBtnClicked;
    private boolean isReRollBtnClicked;
    private boolean isUpBtnPressed;
    private boolean isDownBtnPressed;
    private int lastDiceRolled = 0;

    private BoardSquare lastBoardSquare; // for undo button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_matt);
        //link views
        stakCardView = findViewById(R.id.stak_card_view);
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
        setupRecyclerAdapterView();

        //get intent
        Intent intent = getIntent();
        final String stakCardId = intent.getStringExtra(STAK_CARD_ID_EXTRA);

        //ask sys for new ViewModel, scopes it to this activity and destroys when this activity destroyed
        viewModel = ViewModelProviders.of(this).get(GameMattViewModel.class);

        observeViewModel(viewModel, stakCardId);

        //buttons
        rollButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.onRollDiceButtonClicked();
            }
        });

        undoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onUndoButtonClicked();
            }
        });

        flipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onFlipAbilityClicked();
            }
        });

        reRollBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onReRollClicked();
                //                undoBtn.setEnabled(false);
//
//                //make boardsquare with dice values able to be selected
//                matt.makeBoardSquareSelectableForAbility();
//
//                //make boardsquare able to be selectable
//                updateColumnAdaptersToSelecting(true);
//
//                //update adapters
//                setAdaptersFromGameMatt(matt);
//
//                //disable all Ability buttons
//                disableAbilityBtns();
//
//                isReRollBtnClicked = true;
//                updateViewTotal();
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        upDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

//        switchBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                undoBtn.setEnabled(false);
//
//                //make boardsquare with dice values able to be selected
//                matt.makeBoardSquareSelectableForAbility();
//
//                //make boardsquare able to be selectable
//                updateColumnAdaptersToSelecting(true);
//
//                //disable all Ability buttons
//                disableAbilityBtns();
//
//                switchAbility();
//                updateViewTotal();
//
//            }
//        });
//
//        upDownBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                undoBtn.setEnabled(false);
//
//                //make boardsquare with dice values able to be selected
//                matt.makeBoardSquareSelectableForAbility();
//
//                //make boardsquare able to be selectable
//                updateColumnAdaptersToSelecting(true);
//
//                //disable all Ability buttons
//                disableAbilityBtns();
//
//
//                abilityUpBtn.setVisibility(View.VISIBLE);
//                abilityDownBtn.setVisibility(View.VISIBLE);
//
//
//                abilityUpBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        isUpBtnPressed = true;
//                    }
//                });
//
//                abilityDownBtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        isDownBtnPressed = true;
//                    }
//                });
//
//                upDownBtnClicked = true;
//                updateViewTotal();
//            }
//        });

    }

    private void observeViewModel(final GameMattViewModel gameMattViewModel, String stakCardId) {
        // observe main stak card
        gameMattViewModel.getSingleStak(stakCardId).observe(this, new Observer<StakCard>() {
            @Override
            public void onChanged(final StakCard stakCard) {
                //set current stakCard to view
                stakCardView.setStakCard(stakCard);

                validateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        updateViewTotal();

                        int sValue = sBoardSquareAdapter.getColumnSum();
                        int tValue = tBoardSquareAdapter.getColumnSum();
                        int aValue = aBoardSquareAdapter.getColumnSum();
                        int kValue = kBoardSquareAdapter.getColumnSum();
                        gameMattViewModel.validateCard(stakCard, sValue, tValue, aValue, kValue);
                    }
                });
            }
        });

        gameMattViewModel.viewState.observe(this, new Observer<GameMattViewStateK>() {
            @Override
            public void onChanged(GameMattViewStateK gameMattViewState) {
                sViewText.setText(String.valueOf(gameMattViewState.getAdapterTotalsViewState().getSTotal()));
                tViewText.setText(String.valueOf(gameMattViewState.getAdapterTotalsViewState().getTTotal()));
                aViewText.setText(String.valueOf(gameMattViewState.getAdapterTotalsViewState().getATotal()));
                kViewText.setText(String.valueOf(gameMattViewState.getAdapterTotalsViewState().getKTotal()));
                switchBtn.setEnabled(gameMattViewState.getAbilityViewState().getSwitchEnabled());
                upDownBtn.setEnabled(gameMattViewState.getAbilityViewState().getUpDownEnabled());
                flipBtn.setEnabled(gameMattViewState.getAbilityViewState().getFlipEnabled());
                reRollBtn.setEnabled(gameMattViewState.getAbilityViewState().getReRollEnabled());
                rollButton.setEnabled(gameMattViewState.getRollButtonEnabled());
                undoBtn.setEnabled(gameMattViewState.getUndoButtonEnabled());
                if (gameMattViewState.getDiceImageViewState().getDiceImageVisibility()) {
                    imageViewDice.setVisibility(View.VISIBLE);
                } else {
                    imageViewDice.setVisibility(View.INVISIBLE);
                }

                imageViewDice.setImageResource(gameMattViewState.getDiceImageViewState().getDiceImageRes());
                roundView.setText("Round: " + gameMattViewState.getRoundValue());
                if (gameMattViewState.getValidateViewState().getShowFailDialog()) {
                    showFailDialog();
                    gameMattViewModel.onFailDialogShown();
                }
                if (gameMattViewState.getValidateViewState().getShowPassDialog()) {
                    showPassDialog();
                    gameMattViewModel.onPassDialogShown();
                }
                setAdaptersFromGameMatt(gameMattViewState.getGameMatt());
                updateColumnAdaptersToSelecting(gameMattViewState.getSetAdapterToSelecting());
                if (gameMattViewState.getValidateViewState().getValidateBtnVisible()) {
                    validateBtn.setVisibility(View.VISIBLE);
                } else {
                    validateBtn.setVisibility(View.INVISIBLE);
                }
            }
        });

    }

    private void showFailDialog() {
        FailedDialog failedDialog = new FailedDialog();
        failedDialog.show(getSupportFragmentManager(), "failed dialog");
    }

    private void showPassDialog() {
        PassedDialog passedDialog = new PassedDialog();
        passedDialog.show(getSupportFragmentManager(), "passed dialog");
    }

//    public void revealValidateButton() {
//        if (currentClicks == 10) {
//            validateBtn.setVisibility(View.VISIBLE);
//            hideDiceImage();
//        }
//    }


    @Override
    public void onBoardSquareClicked(BoardSquare boardSquare) {

//        if (isFlipBtnClicked) {
//
//        } else if (isReRollBtnClicked) {
//
//            Toast.makeText(this, "REROLL!!", Toast.LENGTH_SHORT).show();
//
//
//            //input int to method and receive new reroll int
//            int reRolledValue = 0; //FIXME: rollDice();
//
//            // after they click, set the adapter to not selecting
//            updateColumnAdaptersToSelecting(false);
//
//            // update the adapters
//            setAdaptersFromGameMatt(matt);
//
//            //update boardsquare with new flipped int
//            matt.updateBoardSquare(boardSquare, reRolledValue);
//
//
//            isReRollBtnClicked = false;
//
//
//        } else if (upDownBtnClicked) {
//
//            if (isUpBtnPressed || isDownBtnPressed) {
//                //save value of clicked boardsquare
//                int intValueOfBs = boardSquare.getDiceRollValue();
//
//                //input int to method and receive new flipped int
//                int upOrDownValue = upDownAbility(intValueOfBs);
//
//
//                // after they click, set the adapter to not selecting
//                updateColumnAdaptersToSelecting(false);
//
//                // update the adapters
//                setAdaptersFromGameMatt(matt);
//
//                //update boardsquare with new flipped int
//                matt.updateBoardSquare(boardSquare, upOrDownValue);
//
//                abilityUpBtn.setVisibility(View.GONE);
//                abilityDownBtn.setVisibility(View.GONE);
//
//
//                upDownBtnClicked = false;
//                isUpBtnPressed = false;
//                isDownBtnPressed = false;
//            }
//
//        } else {
//
//
//            //make rollBtn able to go to next round
//            rollButton.setEnabled(true);
//
//            //enable undo btn
//            undoBtn.setEnabled(true);
//
//            // after they click, set the adapter to not selecting
//            updateColumnAdaptersToSelecting(false);
//
//            //update boolean that boardsquare has a dice value
//            boardSquare.setHasDiceValue(true);
//
//            //hide Image
//            hideDiceImage();
//
//            lastBoardSquare = boardSquare;
//
//            // update the board
//            matt.updateBoardSquare(boardSquare, lastDiceRolled);
//
//            //column picked
//            columnPicked();
//
//            // update the adapters
//            setAdaptersFromGameMatt(matt);
//        }

        viewModel.onBoardSquareClicked(boardSquare);
//        updateViewTotal();
    }

    private void switchAbility() {

        //TODO: SWITCH
        Toast.makeText(GameMattActivity.this, "SWITCH", Toast.LENGTH_SHORT).show();
        disableAbilityBtns();
    }

    private int upDownAbility(int diceValueForAbility) {


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

    private void disableAbilityBtns() {
        switchBtn.setEnabled(false);
        upDownBtn.setEnabled(false);
        flipBtn.setEnabled(false);
        reRollBtn.setEnabled(false);
    }

    private void setupRecyclerAdapterView() {
        RecyclerView sColumnsRv = findViewById(R.id.s_column_rv);
        RecyclerView tColumnsRv = findViewById(R.id.t_column_rv);
        RecyclerView aColumnsRv = findViewById(R.id.a_column_rv);
        RecyclerView kColumnsRv = findViewById(R.id.k_column_rv);

        //link adapters
        sBoardSquareAdapter = new BoardSquareAdapter(this);
        tBoardSquareAdapter = new BoardSquareAdapter(this);
        aBoardSquareAdapter = new BoardSquareAdapter(this);
        kBoardSquareAdapter = new BoardSquareAdapter(this);

        setupRecyclerViewSettings(sColumnsRv, sBoardSquareAdapter);
        setupRecyclerViewSettings(tColumnsRv, tBoardSquareAdapter);
        setupRecyclerViewSettings(aColumnsRv, aBoardSquareAdapter);
        setupRecyclerViewSettings(kColumnsRv, kBoardSquareAdapter);
    }

    private void setupRecyclerViewSettings(RecyclerView rv, BoardSquareAdapter adapter) {
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rv.setNestedScrollingEnabled(false);
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
}
