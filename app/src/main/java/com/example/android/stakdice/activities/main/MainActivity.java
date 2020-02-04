package com.example.android.stakdice.activities.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.gamematt.GameMatt;
import com.example.android.stakdice.activities.trophyroom.TrophyActivity;
import com.example.android.stakdice.adapter.StakAdapter;
import com.example.android.stakdice.firebase.FirebaseUtils;
import com.example.android.stakdice.models.StakCard;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    private StakViewModel stakViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //link view
        ImageView trophyImageView = findViewById(R.id.image_view_trophy_main_menu);
        trophyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TrophyActivity.newIntent(MainActivity.this));
            }
        });

        //reference to the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setHasFixedSize(true);
        //Adapter link to RecyclerView
        final StakAdapter adapter = new StakAdapter(new StakAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StakCard stakCard) {
                startActivity(GameMatt.newIntent(MainActivity.this, stakCard));
            }
        });
        recyclerView.setAdapter(adapter);

        //ask sys for new ViewModel, scopes it to this activity and destroys when this activity destroyed
        stakViewModel = ViewModelProviders.of(this).get(StakViewModel.class);
        stakViewModel.getAllNotBeatenStaks().observe(this, new Observer<List<StakCard>>() {
            @Override
            public void onChanged(List<StakCard> stakCards) {
                //update RecyclerView. Every time onChanged is triggered which is every time the data in the table changes
                adapter.submitList(stakCards);
            }
        });


        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("simple_value_attribute")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        Log.d("jxf", "success!");
                        List<DocumentSnapshot> documents = queryDocumentSnapshots.getDocuments();
                        // convert DocumentSnapshot to our models
                        for (DocumentSnapshot document : documents) {
                            StakCard stakCard = FirebaseUtils.getStakCardFromDocument(document);
                            int imageRes = stakCard.getImageResource();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("jxf", "onFailure: " + e);
                    }
                });
    }

}

