package com.example.android.stakdice;

import com.example.android.stakdice.activities.gamematt.SimpleGameMatt;
import com.example.android.stakdice.models.GameMattViewStateK;
import com.example.android.stakdice.models.boardsquare.BoardSquare;

import java.util.List;

public class ViewStateUtils {
    public static void disableAbilities(GameMattViewStateK viewState) {
        viewState.getPowerUpsViewState().setFlipEnabled(false);
        viewState.getPowerUpsViewState().setReRollEnabled(false);
        viewState.getPowerUpsViewState().setSwitchEnabled(false);
        viewState.getPowerUpsViewState().setUpDownEnabled(false);
    }

    public static void enablePower(GameMattViewStateK viewState, SimpleGameMatt.StakColumn stakColumn) {
        switch (stakColumn) {
            case STRENGTH:
                viewState.getPowerUpsViewState().setSwitchEnabled(true);
                break;
            case TOUGHNESS:
                viewState.getPowerUpsViewState().setUpDownEnabled(true);
                break;
            case AGILITY:
                viewState.getPowerUpsViewState().setFlipEnabled(true);
                break;
            case KNOWLEDGE:
                viewState.getPowerUpsViewState().setReRollEnabled(true);
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
}
