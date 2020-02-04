package com.example.android.stakdice.activities.main;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.android.stakdice.firebase.ConvertDocumentsToModelsAsyncTask;
import com.example.android.stakdice.firebase.FirebaseUtils;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.repos.StakRepo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class StakViewModel extends AndroidViewModel {

    private StakRepo repository;
    private LiveData<List<StakCard>> allNotBeatenStaks;

    public StakViewModel(@NonNull Application application) {
        super(application);
        repository = new StakRepo(application);
        allNotBeatenStaks = repository.getAllNotBeatenStaks();


        getStakMonstersFromFirebase();
    }

    public LiveData<List<StakCard>> getAllNotBeatenStaks() {
        return allNotBeatenStaks;
    }

    private void getStakMonstersFromFirebase() {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection(FirebaseUtils.COLLECTION_STAK_CARD_MONSTER)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        onSuccessGettingStakMonsters(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(StakViewModel.class.getSimpleName(), "Getting stak monsters: onFailure: " + e);
                    }
                });
    }

    private void onSuccessGettingStakMonsters(QuerySnapshot queryDocumentSnapshots) {
        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
        Log.d(StakViewModel.class.getSimpleName(), "Getting stak monsters: On Success, docs: " + documents.size());

        // convert DocumentSnapshot to our models
        new ConvertDocumentsToModelsAsyncTask(new ConvertDocumentsToModelsAsyncTask.Listener() {
            @Override
            public void onStakCardsConverted(List<StakCard> stakCards) {
                // on success insert it to our repository
                repository.insert(stakCards.toArray(new StakCard[0]));
            }
        }).execute(documents.toArray(new DocumentSnapshot[0]));
    }
}
