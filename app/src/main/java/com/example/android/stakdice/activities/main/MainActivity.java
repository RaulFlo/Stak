package com.example.android.stakdice.activities.main;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.gamematt.GameMatt;
import com.example.android.stakdice.activities.trophyroom.TrophyActivity;
import com.example.android.stakdice.adapter.StakAdapter;
import com.example.android.stakdice.models.StakCard;

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


    }

}
