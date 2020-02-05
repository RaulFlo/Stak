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

    public interface StakCardsListener {
        void onStakCardsRetrieved(List<StakCard> stakCards);

        void onError(Exception e, String errorString);
    }

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private MutableLiveData<UserProfile> userProfileLiveData = new MutableLiveData<>();

    public FirebaseRepo() {
        setUserProfileLiveData();
    }

    public LiveData<UserProfile> getUserProfileLiveData() {
        return userProfileLiveData;
    }

    public void getStakCards(final StakCardsListener listener) {
        db.collection(FirebaseUtils.COLLECTION_STAK_CARD_MONSTER)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        onSuccessGettingStakMonsters(queryDocumentSnapshots, listener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onError(e, "Getting stak cards failure" + e.toString());
                    }
                });
    }

    private void onSuccessGettingStakMonsters(QuerySnapshot queryDocumentSnapshots, final StakCardsListener listener) {
        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
        Log.d(StakViewModel.class.getSimpleName(), "Getting stak monsters: On Success, docs: " + documents.size());

        // convert DocumentSnapshot to our models
        new ConvertDocumentsToModelsAsyncTask(new ConvertDocumentsToModelsAsyncTask.Listener() {
            @Override
            public void onStakCardsConverted(List<StakCard> stakCards) {
                listener.onStakCardsRetrieved(stakCards);
            }
        }).execute(documents.toArray(new DocumentSnapshot[0]));
    }

    private void setUserProfileLiveData() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userPath = "users/" + user.getUid();

        db.document(userPath)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        userProfileLiveData.postValue(new UserProfile(documentSnapshot.getString("display_name")));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(FirebaseRepo.class.getSimpleName(), "Getting user table error: " + e.toString());
                    }
                });

    }
}
