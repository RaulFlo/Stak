package com.example.android.stakdice.activities.profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.launcher.SplashActivty;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileActivity extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, ProfileActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();


        TextView emailText = findViewById(R.id.profile_email_text_view);
        emailText.setText(user.getEmail());




        View signOut = findViewById(R.id.profile_logout_button);
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignOut();
            }
        });

    }




    private void onSignOut() {
        AuthUI.getInstance()
                .signOut(ProfileActivity.this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        finish();
                        startActivity(SplashActivty.newIntent(ProfileActivity.this));
                    }
                });
    }
}
