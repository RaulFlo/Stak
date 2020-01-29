package com.example.android.stakdice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    Button btnEasy, btnMedium, btnHard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //bind views
        btnEasy = findViewById(R.id.btnEasy);
        btnMedium = findViewById(R.id.btnMedium);
        btnHard = findViewById(R.id.btnHard);


        //passing intents
        btnEasy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StakCard easyCard = new StakCard(R.drawable.ic_face_black_24dp,
                        "Slime",
                        10,
                        12,
                        14,
                        12,"Easy",false);
                Intent intent = new Intent(MainActivity.this, GameMatt.class);
                intent.putExtra("StakCard", easyCard);
                startActivity(intent);
            }

        });
        btnMedium.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StakCard mediumCard = new StakCard(R.drawable.ic_face_black_24dp,
                        "Skeleton",
                        10,
                        15,
                        14,
                        15,"Medium", false);
                Intent intent = new Intent(MainActivity.this, GameMatt.class);
                intent.putExtra("StakCard", mediumCard);
                startActivity(intent);
            }
        });
        btnHard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StakCard hardCard = new StakCard(R.drawable.ic_face_black_24dp,
                        "Knight",
                        14,
                        16,
                        14,
                        15,"Hard",false);
                Intent intent = new Intent(MainActivity.this, GameMatt.class);
                intent.putExtra("StakCard", hardCard);
                startActivity(intent);
            }
        });


    }


}
