package com.example.android.stakdice.activities.trophydetail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;

public class TrophyDetailViewModel extends AndroidViewModel {

    private StakRepo repo;

    public TrophyDetailViewModel(@NonNull Application application) {
        super(application);
        repo = new StakRepo(application);
    }

    public LiveData<StakCard> getSingleStak(String id) {
        return repo.getSingleStak(id);
    }
}
