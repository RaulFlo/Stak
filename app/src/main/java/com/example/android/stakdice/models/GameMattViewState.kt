package com.example.android.stakdice.models


data class GameMattViewState @JvmOverloads constructor(
        var gameMatt: GameMatt,
        var rollButtonEnabled: Boolean = true,
        var undoButtonEnabled: Boolean = false,
        var diceRollValue: Int = 0,
        var roundValue: Int = 0,
        var setAdapterToSelecting: Boolean = false,
        var upBtnVisible: Boolean = false,
        var downBtnVisible: Boolean = false,
        var sTotal: Int = 0, var tTotal: Int = 0, var aTotal: Int = 0, var kTotal: Int = 0,
        var switchEnabled: Boolean = false,
        var upDownEnabled: Boolean = false,
        var flipEnabled: Boolean = false,
        var reRollEnabled: Boolean = false,
        var diceImageRes: Int = 0,
        var diceImageVisibility: Boolean = false,
        var validateBtnVisible: Boolean = false,
        var showPassDialog: Boolean = false,
        var showFailDialog: Boolean = false) {

    fun getAnExactCopy(): GameMattViewState {
        return copy()
    }
}