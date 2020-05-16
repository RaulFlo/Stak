package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.boardactions.MainRollUndoBoardAction;
import com.example.android.stakdice.boardactions.FlipAbilityBoardAction;
import com.example.android.stakdice.boardactions.ReRollBoardAction;
import com.example.android.stakdice.boardactions.SwitchBoardAction;
import com.example.android.stakdice.boardactions.UpDownBoardAction;
import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.boardsquare.BoardSquare;
import com.example.android.stakdice.repos.StakRepo;

public class GameMattViewModel extends AndroidViewModel {
    private static final int GAME_OVER_ROUND = 10;

    private GameMatt matt = new SimpleGameMatt();
    private MainRollUndoBoardAction rollBoardAction = new MainRollUndoBoardAction();
    private FlipAbilityBoardAction flipBoardAction = new FlipAbilityBoardAction();
    private ReRollBoardAction reRollBoardAction = new ReRollBoardAction();
    private UpDownBoardAction upDownBoardAction = new UpDownBoardAction();
    private SwitchBoardAction switchBoardAction = new SwitchBoardAction();
    public MutableLiveData<GameMattViewState> viewStateMutableLiveData = new MutableLiveData<>(new GameMattViewState(matt));

    private StakRepo repository;

    public GameMattViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
    }

    public LiveData<StakCard> getSingleStak(String id) {
        return repository.getSingleStak(id);
    }

    public void onRollDiceButtonClicked() {
        if (!isGameOver()) {
            GameMattViewState newState = rollBoardAction.onButtonClicked(getCopyState());
            updateWithState(newState);
        } else {
            GameMattViewState newState = getCopyState();
            newState.setValidateBtnVisible(true);
            newState.setRollButtonEnabled(false);
            updateWithState(newState);
        }
    }

    public void onUndoButtonClicked() {
        GameMattViewState newState = rollBoardAction.undoSettingSquare(getCopyState());
        updateColumnTotals(newState);
        updateWithState(newState);
    }

    public void onFlipAbilityClicked() {
        if (getCurrentState().getFlipEnabled()) {
            GameMattViewState newState = flipBoardAction.onButtonClicked(getCopyState());
            updateColumnTotals(newState);
            updateWithState(newState);
        }
    }

    public void onReRollClicked() {
        if (getCurrentState().getReRollEnabled()) {
            GameMattViewState newState = reRollBoardAction.onButtonClicked(getCopyState());
            updateColumnTotals(newState);
            updateWithState(newState);
        }
    }

    public void onBoardSquareClicked(BoardSquare boardSquare) {
        GameMattViewState newState = null;
        if (switchBoardAction.isActive()) {
            newState = switchBoardAction.onBoardSquareClicked(boardSquare, getCopyState());
        } else if (upDownBoardAction.isActive()) {
            newState = upDownBoardAction.onBoardSquareClicked(boardSquare, getCopyState());
        } else if (reRollBoardAction.isActive()) {
            newState = reRollBoardAction.onBoardSquareClicked(boardSquare, getCopyState());
        } else if (flipBoardAction.isActive()) {
            newState = flipBoardAction.onBoardSquareClicked(boardSquare, getCopyState());
        } else if (rollBoardAction.isActive()) {
            newState = rollBoardAction.onBoardSquareClicked(boardSquare, getCopyState());
        }

        if (newState != null) {
            // always update the correct count
            updateColumnTotals(newState);
            updateWithState(newState);
        }
    }

    public void onPassDialogShown() {
        GameMattViewState newState = getCopyState();
        newState.setShowPassDialog(false);
        updateWithState(newState);
    }

    public void onFailDialogShown() {
        GameMattViewState newState = getCopyState();
        newState.setShowFailDialog(false);
        updateWithState(newState);
    }

    public void validateCard(StakCard stakCard) {
        GameMattViewState newState = getCopyState();
        if (stakCard.isValid(newState.getSTotal(), newState.getTTotal(), newState.getATotal(), newState.getKTotal())) {
            onStakCardBeaten(stakCard);
            newState.setShowPassDialog(true);
        } else {
            newState.setShowFailDialog(true);
        }
        updateWithState(newState);
    }

    private boolean isGameOver() {
        return getCurrentState().getRoundValue() >= GAME_OVER_ROUND;
    }

    private void updateColumnTotals(GameMattViewState currentState) {
        GameMatt gameMatt = currentState.getGameMatt();
        int sTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getSColumnBoards());
        int tTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getTColumnBoards());
        int aTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getAColumnBoards());
        int kTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getKColumnBoards());
        currentState.setSTotal(sTotal);
        currentState.setTTotal(tTotal);
        currentState.setATotal(aTotal);
        currentState.setKTotal(kTotal);
    }

    private void onStakCardBeaten(StakCard stakCard) {
        stakCard.setBeaten(true);
        repository.update(stakCard);
    }

    public void onSwitchAbilityClicked() {
        if (getCurrentState().getSwitchEnabled()) {
            GameMattViewState newState = switchBoardAction.onButtonClicked(getCopyState());
            updateWithState(newState);
        }
    }

    public void onUpDownAbilityClicked() {
        if (getCurrentState().getUpDownEnabled()) {
            GameMattViewState newState = upDownBoardAction.onButtonClicked(getCopyState());
            updateWithState(newState);
        }
    }

    public void onAbilityDownClicked() {
        if (getCurrentState().getDownBtnVisible() && upDownBoardAction.isActive()) {
            GameMattViewState newState = upDownBoardAction.onDownBtnClicked(getCopyState());
            updateWithState(newState);
        }
    }

    public void onAbilityUpClicked() {
        if (getCurrentState().getDownBtnVisible() && upDownBoardAction.isActive()) {
            GameMattViewState newState = upDownBoardAction.onUpBtnClicked(getCopyState());
            updateWithState(newState);
        }
    }

    private GameMattViewState getCopyState() {
        return viewStateMutableLiveData.getValue().getAnExactCopy();
    }

    private GameMattViewState getCurrentState() {
        return viewStateMutableLiveData.getValue();
    }

    private void updateWithState(GameMattViewState newState) {
        viewStateMutableLiveData.setValue(newState);
    }
}
