package com.example.android.stakdice.activities.launcher;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.main.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.List;

public class SplashActivty extends AppCompatActivity {

    public static Intent newIntent(Context context) {
        return new Intent(context, SplashActivty.class);
    }

    private static final String AUTH_TAG = "AUTH_TAG";

    private final static int RC_SIGN_IN = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // check if the user is signed in
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            finish();
            startActivity(MainActivity.newIntent(this));
        } else {
            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setAndroidPackageName("com.example.android.stakdice", true, null)
                    .setHandleCodeInApp(true)
                    .setUrl("https://stakdice.page.link/signedIn") // This URL needs to be whitelisted
                    .build();

            List<AuthUI.IdpConfig> providers = Arrays.asList(
                    new AuthUI.IdpConfig.EmailBuilder()
                            .enableEmailLinkSignIn()
                            .setActionCodeSettings(actionCodeSettings)
                            .build(),
                    new AuthUI.IdpConfig.AnonymousBuilder().build());

            AuthUI.SignInIntentBuilder authBuilder = AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers);

            // after the user signs in with email link it comes back here
            Intent intent = getIntent();
            Uri emailLink = intent.getData();
            if (emailLink != null) {
                authBuilder.setEmailLink(emailLink.toString());
                Log.d(AUTH_TAG, "email link: " + emailLink.toString());
            }

            startActivityForResult(authBuilder.build(), RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                finish();
                startActivity(MainActivity.newIntent(this));
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}

