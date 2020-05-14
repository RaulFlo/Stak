package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt.StakColumn;
import com.example.android.stakdice.boardactions.BasicRollBoardAction;
import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.boardsquare.BoardSquare;
import com.example.android.stakdice.repos.StakRepo;

import java.util.Random;

public class GameMattViewModel extends AndroidViewModel {

    private GameMatt matt = new SimpleGameMatt();
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
        if (stakCard.isValid(sValue, tValue, aValue, kValue)) {
            onStakCardBeaten(stakCard);
//            showPassDialog.setValue(true);
        } else {
//            showFailDialog.setValue(true);
        }
    }

    BasicRollBoardAction rollBoardAction = new BasicRollBoardAction();

    public void onRollDiceButtonClicked() {
        if (true) {
            rollBoardAction.onButtonClicked(gameMattViewState, gameMattViewState.getValue());
        }
    }

    public void onBoardSquareClicked(BoardSquare boardSquare) {
        if (rollBoardAction.isActive()) {
            rollBoardAction.onBoardSquareClicked(boardSquare, gameMattViewState, gameMattViewState.getValue());
        }
    }

    public void onPassDialogShown() {
//        showPassDialog.setValue(false);
    }

    public void onFailDialogShown() {
//        showFailDialog.setValue(false);
    }

    private void onStakCardBeaten(StakCard stakCard) {
        stakCard.setBeaten(true);
        repository.update(stakCard);
    }

}
//* validate
//        * first power up

