package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.gamematt.SimpleGameMatt.StakColumn;
import com.example.android.stakdice.models.GameMatt;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.boardsquare.BoardSquare;
import com.example.android.stakdice.repos.StakRepo;

import java.util.Random;

public class GameMattViewModel extends AndroidViewModel {

    // total counts
    public MutableLiveData<Integer> sTotal = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> tTotal = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> aTotal = new MutableLiveData<Integer>(0);
    public MutableLiveData<Integer> kTotal = new MutableLiveData<Integer>(0);

    // powers enabled
    public MutableLiveData<Boolean> switchEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> upDownEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> flipEnabled = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> reRollEnabled = new MutableLiveData<>(false);

    public MutableLiveData<Boolean> rollButtonEnabled = new MutableLiveData<>(true);
    public MutableLiveData<Boolean> undoButtonEnabled = new MutableLiveData<>(false);

    // dice
    public MutableLiveData<Boolean> diceViewVisible = new MutableLiveData<>(false);
    public MutableLiveData<Integer> diceImageRes = new MutableLiveData<>(0);
    public MutableLiveData<Integer> diceRollValue = new MutableLiveData<>(0);

    // validate
    public MutableLiveData<Boolean> validateBtnVisible = new MutableLiveData<>(false);

    // show dialogs
    public MutableLiveData<Boolean> showPassDialog = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> showFailDialog = new MutableLiveData<>(false);

    // round
    public MutableLiveData<Integer> roundLv = new MutableLiveData<>(0);

    private GameMatt matt = new SimpleGameMatt();
    // game matt rows
    public MutableLiveData<GameMatt> gameMattMutableLiveData = new MutableLiveData<>(matt);
    public MutableLiveData<Boolean> setAdaptersToSelecting = new MutableLiveData<>(false);

    private int round = 0;
    private int lastDiceRoll = 0;


    private Random rng = new Random();

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
            showPassDialog.setValue(true);
        } else {
            showFailDialog.setValue(true);
        }
    }

    public void onRollDiceButtonClicked() {
        // increment round
        round++;
        roundLv.setValue(round);
        // disable abilities
        disableAbilities();
        // disable undo
        undoButtonEnabled.setValue(false);

        // tell the matt to enable the selectable squares
        matt.enableAllSelectableSquares();
        // set the live data so view gets updated
        gameMattMutableLiveData.setValue(matt);

        updateDiceValuesToNewRollAndShowDiceImage();

        // set adapters to selecting
        setAdaptersToSelecting.setValue(true);
    }

    public void onBoardSquareClicked(BoardSquare boardSquare) {
        rollButtonEnabled.setValue(true);
        undoButtonEnabled.setValue(true);
        setAdaptersToSelecting.setValue(false);
        diceViewVisible.setValue(false);

        matt.updateBoardSquare(boardSquare, lastDiceRoll);
        StakColumn stakColumn = matt.returnStakColumn(boardSquare);
        enablePower(stakColumn);
        gameMattMutableLiveData.setValue(matt);
    }

    private void enablePower(StakColumn stakColumn) {
        switch (stakColumn) {
            case STRENGTH:
                switchEnabled.setValue(true);
                break;
            case TOUGHNESS:
                upDownEnabled.setValue(true);
                break;
            case AGILITY:
                flipEnabled.setValue(true);
                break;
            case KNOWLEDGE:
                reRollEnabled.setValue(true);
                break;
        }
    }

    public int getDiceRollValue() {
        // get roll number
        return rng.nextInt(6) + 1;
    }

    public void onPassDialogShown() {
        showPassDialog.setValue(false);
    }

    public void onFailDialogShown() {
        showFailDialog.setValue(false);
    }

    private void updateDiceValuesToNewRollAndShowDiceImage() {
        // get new dice roll value
        lastDiceRoll = getDiceRollValue();
        // set dice roll value
        diceRollValue.setValue(lastDiceRoll);
        // set dice image based on roll
        diceImageRes.setValue(getDiceImageByRollAmount(lastDiceRoll));
        // show dice image
        diceViewVisible.setValue(true);
    }

    private void disableAbilities() {
        switchEnabled.setValue(false);
        upDownEnabled.setValue(false);
        flipEnabled.setValue(false);
        reRollEnabled.setValue(false);
    }

    private void onStakCardBeaten(StakCard stakCard) {
        stakCard.setBeaten(true);
        repository.update(stakCard);
    }

    private Integer getDiceImageByRollAmount(int rollValue) {
        switch (rollValue) {
            case 1:
                return R.drawable.dice1;
            case 2:
                return (R.drawable.dice2);
            case 3:
                return (R.drawable.dice3);
            case 4:
                return R.drawable.dice4;
            case 5:
                return R.drawable.dice5;
            case 6:
                return R.drawable.dice6;
        }

        return 0;
    }

}
