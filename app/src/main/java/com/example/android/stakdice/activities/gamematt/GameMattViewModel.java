package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.ViewStateUtils;
import com.example.android.stakdice.boardactions.MainRollUndoBoardAction;
import com.example.android.stakdice.boardactions.FlipAbilityBoardAction;
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
    public MutableLiveData<GameMattViewStateK> gameMattViewState = new MutableLiveData<>(new GameMattViewStateK(matt));

    private StakRepo repository;

    public GameMattViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
    }

    public LiveData<StakCard> getSingleStak(String id) {
        return repository.getSingleStak(id);
    }

    public void validateCard(StakCard stakCard, int sValue, int tValue, int aValue, int kValue) {
        GameMattViewStateK newState = gameMattViewState.getValue().getAnExactCopy();
        if (stakCard.isValid(sValue, tValue, aValue, kValue)) {
            onStakCardBeaten(stakCard);
            newState.getValidateViewState().setShowPassDialog(true);
        } else {
            newState.getValidateViewState().setShowFailDialog(true);
        }
        gameMattViewState.setValue(newState);
    }


    public void onRollDiceButtonClicked() {
        if (true) {
            rollBoardAction.onButtonClicked(gameMattViewState, gameMattViewState.getValue());
        }
    }

    public void onUndoButtonClicked() {
        rollBoardAction.undoSettingSquare(gameMattViewState, gameMattViewState.getValue());
        updateColumnTotals();
    }

    public void onFlipAbilityClicked() {
        if (true) {
            flipBoardAction.onButtonClicked(gameMattViewState, gameMattViewState.getValue());
        }
        updateColumnTotals();
    }

    public void onBoardSquareClicked(BoardSquare boardSquare) {
        if (flipBoardAction.isActive()) {
            flipBoardAction.onBoardSquareClicked(boardSquare, gameMattViewState, gameMattViewState.getValue());
        } else if (rollBoardAction.isActive()) {
            rollBoardAction.onBoardSquareClicked(boardSquare, gameMattViewState, gameMattViewState.getValue());
        }

        // always update the correct count
        updateColumnTotals();
    }

    private void updateColumnTotals() {
        GameMattViewStateK newState = gameMattViewState.getValue();
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

        gameMattViewState.setValue(newState);
    }

    public void onPassDialogShown() {
        GameMattViewStateK newState = gameMattViewState.getValue().getAnExactCopy();
        newState.getValidateViewState().setShowPassDialog(false);
        gameMattViewState.setValue(newState);
    }

    public void onFailDialogShown() {
        GameMattViewStateK newState = gameMattViewState.getValue().getAnExactCopy();
        newState.getValidateViewState().setShowFailDialog(false);
        gameMattViewState.setValue(newState);
    }

    private void onStakCardBeaten(StakCard stakCard) {
        stakCard.setBeaten(true);
        repository.update(stakCard);
    }
}
