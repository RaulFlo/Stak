package com.example.android.stakdice;

import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewState;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.List;
import java.util.Random;

public class ViewStateUtils {
    private static Random rng = new Random();

    public static void disableAbilities(GameMattViewState viewState) {
        viewState.setFlipEnabled(false);
        viewState.setReRollEnabled(false);
        viewState.setSwitchEnabled(false);
        viewState.setUpDownEnabled(false);
    }

    public static void enablePower(GameMattViewState viewState, SimpleGameMatt.StakColumn stakColumn) {
        switch (stakColumn) {
            case STRENGTH:
                viewState.setSwitchEnabled(true);
                break;
            case TOUGHNESS:
                viewState.setUpDownEnabled(true);
                break;
            case AGILITY:
                viewState.setFlipEnabled(true);
                break;
            case KNOWLEDGE:
                viewState.setReRollEnabled(true);
                break;
        }
    }

    public static Integer getDiceImageByRollAmount(int rollValue) {
        switch (rollValue) {
            case 1:
                return R.drawable.dice1;
            case 2:
                return (R.drawable.dice2);
            case 3:
                return (R.drawable.dice3);
            case 4:
                return R.drawable.dice4;
            case 5:
                return R.drawable.dice5;
            case 6:
                return R.drawable.dice6;
        }

        return 0;
    }

    public static int getBoardSquareSum(List<BoardSquare> boardSquares) {
        int total = 0;
        for (BoardSquare boardSquare : boardSquares) {
            total += boardSquare.getDiceRollValue();
        }
        return total;
    }

    public static int getDiceRollValue() {
        return rng.nextInt(6) + 1;
    }
}
