package com.example.android.stakdice.activities.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.FirebaseRepo;
import com.example.android.stakdice.repos.StakRepo;

import java.util.List;

public class StakViewModel extends AndroidViewModel {

    private StakRepo repository;
    private FirebaseRepo firebaseRepo;
    private LiveData<List<StakCard>> allNotBeatenStaks;

    public StakViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
        firebaseRepo = new FirebaseRepo();
        allNotBeatenStaks = repository.getAllNotBeatenStaks();

        getStakMonstersFromFirebase();
    }

    public LiveData<List<StakCard>> getAllNotBeatenStaks() {
        return allNotBeatenStaks;
    }

    private void getStakMonstersFromFirebase() {
        // make db call to firebase for stak cards and user profile
        firebaseRepo.setStakCardsFromFirebaseAndSetLiveData();
        firebaseRepo.getStakCardsLiveData()
                .observeForever(new Observer<List<StakCard>>() {
                    @Override
                    public void onChanged(List<StakCard> stakCards) {
                        repository.insert(stakCards.toArray(new StakCard[0]));
                    }
                });
    }
}
