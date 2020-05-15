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
import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.GmAdapterTotalsViewState;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.boardsquare.BoardSquare;
import com.example.android.stakdice.repos.StakRepo;

public class GameMattViewModel extends AndroidViewModel {

    private GameMatt matt = new SimpleGameMatt();
    private MainRollUndoBoardAction rollBoardAction = new MainRollUndoBoardAction();
    private FlipAbilityBoardAction flipBoardAction = new FlipAbilityBoardAction();
    private ReRollBoardAction reRollBoardAction = new ReRollBoardAction();
    public MutableLiveData<GameMattViewStateK> viewState = new MutableLiveData<>(new GameMattViewStateK(matt));

    private StakRepo repository;

    public GameMattViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
    }

    public LiveData<StakCard> getSingleStak(String id) {
        return repository.getSingleStak(id);
    }

    public void validateCard(StakCard stakCard, int sValue, int tValue, int aValue, int kValue) {
        GameMattViewStateK newState = viewState.getValue().getAnExactCopy();
        if (stakCard.isValid(sValue, tValue, aValue, kValue)) {
            onStakCardBeaten(stakCard);
            newState.getValidateViewState().setShowPassDialog(true);
        } else {
            newState.getValidateViewState().setShowFailDialog(true);
        }
        viewState.setValue(newState);
    }


    public void onRollDiceButtonClicked() {
        if (true) {
            rollBoardAction.onButtonClicked(viewState);
        }
    }

    public void onUndoButtonClicked() {
        rollBoardAction.undoSettingSquare(viewState, viewState.getValue());
        updateColumnTotals();
    }

    public void onFlipAbilityClicked() {
        if (viewState.getValue().getAbilityViewState().getFlipEnabled()) {
            flipBoardAction.onButtonClicked(viewState);
            updateColumnTotals();
        }
    }

    public void onReRollClicked() {
        if (viewState.getValue().getAbilityViewState().getReRollEnabled()) {
            reRollBoardAction.onButtonClicked(viewState);
            updateColumnTotals();
        }
    }

    public void onBoardSquareClicked(BoardSquare boardSquare) {
        if (reRollBoardAction.isActive()) {
            reRollBoardAction.onBoardSquareClicked(boardSquare, viewState);
        } else if (flipBoardAction.isActive()) {
            flipBoardAction.onBoardSquareClicked(boardSquare, viewState);
        } else if (rollBoardAction.isActive()) {
            rollBoardAction.onBoardSquareClicked(boardSquare, viewState);
        }

        // always update the correct count
        updateColumnTotals();
    }

    public void onPassDialogShown() {
        GameMattViewStateK newState = viewState.getValue().getAnExactCopy();
        newState.getValidateViewState().setShowPassDialog(false);
        viewState.setValue(newState);
    }

    public void onFailDialogShown() {
        GameMattViewStateK newState = viewState.getValue().getAnExactCopy();
        newState.getValidateViewState().setShowFailDialog(false);
        viewState.setValue(newState);
    }

    private void updateColumnTotals() {
        GameMattViewStateK newState = viewState.getValue();
        GameMatt gameMatt = newState.getGameMatt();
        int sTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getSColumnBoards());
        int tTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getTColumnBoards());
        int aTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getAColumnBoards());
        int kTotal = ViewStateUtils.getBoardSquareSum(gameMatt.getKColumnBoards());
        GmAdapterTotalsViewState adapterTotals = newState.getAdapterTotalsViewState();
        adapterTotals.setSTotal(sTotal);
        adapterTotals.setTTotal(tTotal);
        adapterTotals.setATotal(aTotal);
        adapterTotals.setKTotal(kTotal);

        viewState.setValue(newState);
    }

    private void onStakCardBeaten(StakCard stakCard) {
        stakCard.setBeaten(true);
        repository.update(stakCard);
    }
}
