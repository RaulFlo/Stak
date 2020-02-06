package com.example.android.stakdice.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.android.stakdice.R;
import com.example.android.stakdice.activities.main.MainActivity;
import com.example.android.stakdice.activities.trophyroom.TrophyActivity;

public class PassedDialog extends DialogFragment {

    private Button mainMenuButton;
    private Button stakCardButton;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.pass_dialog, null);

        builder.setView(view);

        mainMenuButton = view.findViewById(R.id.game_matt_main_menu_button);
        stakCardButton = view.findViewById(R.id.game_matt_stack_card_button);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.newIntent(view.getContext()));
            }
        });

        stakCardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TrophyActivity.newIntent(view.getContext()));
            }
        });

        return builder.create();
    }
}
