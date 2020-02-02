package com.example.android.stakdice.activities.trophyroom;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;

import java.util.List;

public class TrophyRoomViewModel extends AndroidViewModel {


    private StakRepo repository;
    private LiveData<List<StakCard>> allStaks;


    public TrophyRoomViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
        allStaks = repository.getAllStaks();
    }

    public LiveData<List<StakCard>> getAllStaks() {
        return allStaks;
    }

}
