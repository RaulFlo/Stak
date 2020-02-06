package com.example.android.stakdice.activities.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

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

        // make db call to firebase for stak cards and user profile
        firebaseRepo.setStakCardsFromFirebaseAndSetLiveData();
        firebaseRepo.setUserProfileLiveData();

        getStakMonstersFromFirebase();
    }

    public LiveData<List<StakCard>> getAllNotBeatenStaks() {
        return allNotBeatenStaks;
    }

    public LiveData<UserProfile> getUserProfile() {
        return firebaseRepo.getUserProfileLiveData();
    }

    private void getStakMonstersFromFirebase() {
        firebaseRepo.getStakCardsLiveData()
                .observeForever(new Observer<List<StakCard>>() {
                    @Override
                    public void onChanged(List<StakCard> stakCards) {
                        repository.insert(stakCards.toArray(new StakCard[0]));
                    }
                });
    }
}
