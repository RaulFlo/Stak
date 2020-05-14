package com.example.android.stakdice.models


data class GameMattViewStateK @JvmOverloads constructor(
        var gameMatt: GameMatt,
        var rollButtonEnabled: Boolean = true, var undoButtonEnabled: Boolean = false,
        var diceRollValue: Int = 0, var roundValue: Int = 0, var setAdapterToSelecting: Boolean = false,
        var adapterTotalsViewState: GmAdapterTotalsViewState = GmAdapterTotalsViewState(),
        var powerUpsViewState: GmPowerUpsViewState = GmPowerUpsViewState(),
        var diceImageViewState: GmDiceImageViewState = GmDiceImageViewState(),
        var validateViewState: GmValidateViewState = GmValidateViewState()) {
    
    fun getAnExactCopy(): GameMattViewStateK {
        return copy()
    }
}


data class GmAdapterTotalsViewState(var sTotal: Int = 0, var tTotal: Int = 0,
                                    var aTotal: Int = 0, var kTotal: Int = 0)

data class GmPowerUpsViewState(var switchEnabled: Boolean = false, var upDownEnabled: Boolean = false,
                               var flipEnabled: Boolean = false, var reRollEnabled: Boolean = false)

data class GmDiceImageViewState(var diceImageRes: Int = 0,
                                var diceImageVisibility: Boolean = false)


data class GmValidateViewState(var validateBtnVisible: Boolean = false,
                               var showPassDialog: Boolean = false, var showFailDialog: Boolean = false)