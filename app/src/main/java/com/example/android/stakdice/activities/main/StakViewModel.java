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
    private LiveData<List<StakCard>> allStaks;

    public StakViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
        allStaks = repository.getAllStaks();
    }

    public void insert(StakCard stakCard) {
        repository.insert(stakCard);
    }

    public void update(StakCard stakCard) {
        repository.update(stakCard);
    }

    public void delete(StakCard stakCard) {
        repository.delete(stakCard);
    }

    public void deleteAllStaks() {
        repository.deleteAllStaks();
    }

    public LiveData<List<StakCard>> getAllStaks() {
        return allStaks;
    }


}
