package com.example.android.stakdice.activities.trophyroom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.trophydetail.TrophyDetail;
import com.example.android.stakdice.adapter.TrophyAdapter;
import com.example.android.stakdice.models.StakCard;

import java.util.List;

public class TrophyActivity extends AppCompatActivity {

    private TrophyRoomViewModel trophyRoomViewModel;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, TrophyActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy);


        //reference to RecyclerView
        final RecyclerView recyclerView = findViewById(R.id.trophy_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        //Adapter link to RecyclerView
        final TrophyAdapter adapter = new TrophyAdapter(new TrophyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(StakCard stakCard) {
                startActivity(TrophyDetail.newIntent(TrophyActivity.this,stakCard));
            }
        });
        recyclerView.setAdapter(adapter);


         //ask sys for new ViewModel, scopes it to this activity and destroys when this activity destroyed
        trophyRoomViewModel = ViewModelProviders.of(this).get(TrophyRoomViewModel.class);
        trophyRoomViewModel.getAllStaks().observe(this, new Observer<List<StakCard>>() {
            @Override
            public void onChanged(List<StakCard> stakCards) {
                //update RecyclerView. Every time onChanged is triggered which is every time the data in the table changes
                adapter.submitList(stakCards);
            }
        });

    }
}
