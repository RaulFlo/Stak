package com.example.android.stakdice.activities.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.UserProfile;
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

    public LiveData<UserProfile> getUserProfile() {
        return firebaseRepo.getUserProfileLiveData();
    }

    private void getStakMonstersFromFirebase() {
        firebaseRepo.getStakCards(new FirebaseRepo.StakCardsListener() {
            @Override
            public void onStakCardsRetrieved(List<StakCard> stakCards) {
                // on success insert it to our repository
                repository.insert(stakCards.toArray(new StakCard[0]));
            }

            @Override
            public void onError(Exception e, String errorString) {
                Log.d(StakViewModel.class.getSimpleName(), errorString);
            }
        });
    }
}
