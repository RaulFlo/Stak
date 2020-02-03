package com.example.android.stakdice.activities.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;

import java.util.List;

public class StakViewModel extends AndroidViewModel {

    private StakRepo repository;
    private LiveData<List<StakCard>> allNotBeatenStaks;

    public StakViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
        allNotBeatenStaks = repository.getAllNotBeatenStaks();
    }



    public LiveData<List<StakCard>>getAllNotBeatenStaks(){
        return allNotBeatenStaks;
    }


}
