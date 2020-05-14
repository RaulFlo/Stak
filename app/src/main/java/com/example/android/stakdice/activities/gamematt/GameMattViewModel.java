package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.R;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;

import org.jetbrains.annotations.Contract;

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

    // dice
    public MutableLiveData<Boolean> diceViewVisible = new MutableLiveData<>(false);
    public MutableLiveData<Integer> diceImageRes = new MutableLiveData<>(0);
    public MutableLiveData<Integer> diceRollValue = new MutableLiveData<>(0);

    // show dialogs
    public MutableLiveData<Boolean> showPassDialog = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> showFailDialog = new MutableLiveData<>(false);


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

    public void rollDice() {
        // get roll number
        int randomNumber = rng.nextInt(6) + 1;
        // set dice roll value
        diceRollValue.setValue(randomNumber);
        // set dice image based on roll
        diceImageRes.setValue(getDiceImageByRollAmount(randomNumber));
    }

    public void onShowDialogShown() {
        showPassDialog.setValue(false);
    }

    public void onFailDialogShown() {
        showFailDialog.setValue(false);
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
