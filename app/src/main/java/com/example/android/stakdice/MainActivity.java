package com.example.android.stakdice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity  {


    private StakViewModel stakViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //reference to the RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.recycler_view);

        LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(horizontalLayoutManager);
        recyclerView.setHasFixedSize(true);
        //Adapter link to RecyclerView
        final StakAdapter adapter = new StakAdapter();
        recyclerView.setAdapter(adapter);

        //ask sys for new ViewModel, scopes it to this activity and destroys when this activity destroyed
        stakViewModel = ViewModelProviders.of(this).get(StakViewModel.class);
        stakViewModel.getAllStaks().observe(this, new Observer<List<StakCard>>() {
            @Override
            public void onChanged(List<StakCard> stakCards) {
                //update RecyclerView. Every time onChanged is triggered which is every time the data in the table changes
                adapter.submitList(stakCards);
            }
        });



        adapter.setOnItemClickListener(new StakAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StakCard stakCard) {
                Intent intent = new Intent(MainActivity.this, GameMatt.class);
                intent.putExtra("StakCard", stakCard);
                startActivity(intent);
            }
        });



    }

}
