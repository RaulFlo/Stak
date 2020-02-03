package com.example.android.stakdice.activities.trophydetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.stakdice.R;
import com.example.android.stakdice.customviews.TrophyView;
import com.example.android.stakdice.models.StakCard;

public class TrophyDetail extends AppCompatActivity {

    private static String STAK_CARD_ID_EXTRA = "StakCardID";

    private TrophyDetailViewModel trophyDetailViewModel;

    public static Intent newIntent(Context context, StakCard stakCard) {
        Intent intent = new Intent(context, TrophyDetail.class);
        intent.putExtra(STAK_CARD_ID_EXTRA, stakCard.getId());
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trophy_detail);

        final TrophyView trophyView = findViewById(R.id.trophy_detail_custom_view);

        //get intent
        Intent intent = getIntent();
        final int stakcardId = intent.getIntExtra(STAK_CARD_ID_EXTRA, -1);

        trophyDetailViewModel = ViewModelProviders.of(this).get(TrophyDetailViewModel.class);

        trophyDetailViewModel.getSingleStak(stakcardId).observe(this, new Observer<StakCard>() {
            @Override
            public void onChanged(final StakCard stakCard) {
                stakCard.getCardName();
                //set current stakCard to view
                trophyView.setStakCard(stakCard);



            }
        });



    }
}
