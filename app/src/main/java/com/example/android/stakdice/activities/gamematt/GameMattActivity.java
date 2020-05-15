package com.example.android.stakdice.activities.gamematt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.android.stakdice.models.GameMattViewState;
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
            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onSwitchAbilityClicked();
            }
        });

        upDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onUpDownAbilityClicked();
            }
        });

        abilityDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onAbilityDownClicked();
            }
        });
        abilityUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.onAbilityUpClicked();
            }
        });
    }

    private void observeViewModel(final GameMattViewModel viewModel, String stakCardId) {
        // observe main stak card
        viewModel.getSingleStak(stakCardId).observe(this, new Observer<StakCard>() {
            @Override
            public void onChanged(final StakCard stakCard) {
                //set current stakCard to view
                stakCardView.setStakCard(stakCard);

                validateBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.validateCard(stakCard);
                    }
                });
            }
        });

        viewModel.viewState.observe(this, new Observer<GameMattViewState>() {
            @Override
            public void onChanged(GameMattViewState viewState) {
                sViewText.setText(String.valueOf(viewState.getAdapterTotalsViewState().getSTotal()));
                tViewText.setText(String.valueOf(viewState.getAdapterTotalsViewState().getTTotal()));
                aViewText.setText(String.valueOf(viewState.getAdapterTotalsViewState().getATotal()));
                kViewText.setText(String.valueOf(viewState.getAdapterTotalsViewState().getKTotal()));
                switchBtn.setEnabled(viewState.getAbilityViewState().getSwitchEnabled());
                upDownBtn.setEnabled(viewState.getAbilityViewState().getUpDownEnabled());
                flipBtn.setEnabled(viewState.getAbilityViewState().getFlipEnabled());
                reRollBtn.setEnabled(viewState.getAbilityViewState().getReRollEnabled());
                rollButton.setEnabled(viewState.getRollButtonEnabled());
                undoBtn.setEnabled(viewState.getUndoButtonEnabled());
                if (viewState.getDiceImageViewState().getDiceImageVisibility()) {
                    imageViewDice.setVisibility(View.VISIBLE);
                } else {
                    imageViewDice.setVisibility(View.INVISIBLE);
                }

                imageViewDice.setImageResource(viewState.getDiceImageViewState().getDiceImageRes());
                roundView.setText("Round: " + viewState.getRoundValue());
                if (viewState.getValidateViewState().getShowFailDialog()) {
                    showFailDialog();
                    viewModel.onFailDialogShown();
                }
                if (viewState.getValidateViewState().getShowPassDialog()) {
                    showPassDialog();
                    viewModel.onPassDialogShown();
                }
                setAdaptersFromGameMatt(viewState.getGameMatt());
                updateColumnAdaptersToSelecting(viewState.getSetAdapterToSelecting());
                if (viewState.getValidateViewState().getValidateBtnVisible()) {
                    validateBtn.setVisibility(View.VISIBLE);
                } else {
                    validateBtn.setVisibility(View.INVISIBLE);
                }
                setViewVisibleBasedOnBoolean(abilityUpBtn, viewState.getUpBtnVisible());
                setViewVisibleBasedOnBoolean(abilityDownBtn, viewState.getDownBtnVisible());
            }
        });

    }

    private void setViewVisibleBasedOnBoolean(View view, boolean visible) {
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
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
        viewModel.onBoardSquareClicked(boardSquare);
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
