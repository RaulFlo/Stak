package com.example.android.stakdice.activities.gamematt;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;

public class GameMattViewModel extends AndroidViewModel {

    private StakRepo repository;

    public GameMattViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
    }


    public LiveData<StakCard> getSingleStak(String id){
        return repository.getSingleStak(id);
    }

}
