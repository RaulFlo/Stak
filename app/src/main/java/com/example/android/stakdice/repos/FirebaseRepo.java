package com.example.android.stakdice.repos;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.stakdice.activities.main.StakViewModel;
import com.example.android.stakdice.firebase.ConvertDocumentsToModelsAsyncTask;
import com.example.android.stakdice.firebase.FirebaseUtils;
import com.example.android.stakdice.models.StakCard;
import com.example.android.stakdice.models.UserProfile;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepo {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();
    private MutableLiveData<List<StakCard>> stakCardsLiveData = new MutableLiveData<>();

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    public LiveData<List<StakCard>> getStakCardsLiveData() {
        return stakCardsLiveData;
    }

    public void setStakCardsFromFirebaseAndSetLiveData() {
        // make db call to "stak_monsters" table asynchronously (not-blocking/listeners)
        db.collection(FirebaseUtils.COLLECTION_STAK_CARD_MONSTER)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // on success handle in method
                        onSuccessGettingStakMonsters(queryDocumentSnapshots);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(FirebaseRepo.class.getSimpleName(), "Getting stak cards failure" + e.toString());
                    }
                });
    }

    // handles the response from the "stak_monsters" db call
    private void onSuccessGettingStakMonsters(QuerySnapshot queryDocumentSnapshots) {
        // get documents
        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
        Log.d(StakViewModel.class.getSimpleName(), "Getting stak monsters: On Success, docs: " + documents.size());

        // convert documents to our models
        new ConvertDocumentsToModelsAsyncTask(new ConvertDocumentsToModelsAsyncTask.Listener() {
            @Override
            public void onStakCardsConverted(List<StakCard> stakCards) {
                // once converted set/post value to the live data
                stakCardsLiveData.postValue(stakCards);
            }
        }).execute(documents.toArray(new DocumentSnapshot[0]));
    }

    // will make a firebase db call to get the user document
    public void setUserProfileLiveData() {
        // get current user signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userPath = "users/" + user.getUid();

        // make db call to get specific user document
        db.document(userPath)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        // post/set the UserProfile after converting
                        userProfileLiveData.postValue(convertDocumentSnapshotToUserProfile(documentSnapshot));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(FirebaseRepo.class.getSimpleName(), "Getting user table error: " + e.toString());
                    }
                });

    }

    private UserProfile convertDocumentSnapshotToUserProfile(DocumentSnapshot documentSnapshot) {
        return new UserProfile(documentSnapshot.getString("display_name"));
    }
}
