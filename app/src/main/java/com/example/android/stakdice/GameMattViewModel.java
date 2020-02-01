package com.example.android.stakdice;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GameMattViewModel extends AndroidViewModel {

    private StakRepo repository;

    public GameMattViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
    }


    public LiveData<StakCard> getSingleStak(int id){
        return repository.getSingleStak(id);
    }

}
