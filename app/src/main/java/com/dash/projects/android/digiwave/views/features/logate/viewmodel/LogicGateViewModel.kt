package com.dash.projects.android.digiwave.views.features.logate.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.sealed.BinaryState

class LogicGateViewModel : ViewModel() {
    private val _inputA = MutableLiveData<BinaryState>(BinaryState.StateOff)
    fun inputA(changeState: Boolean = false): LiveData<BinaryState> = _inputA.apply {
        if (changeState) changeState()
    }

    private val _inputB = MutableLiveData<BinaryState>(BinaryState.StateOff)
    fun inputB(changeState: Boolean = false): LiveData<BinaryState> = _inputB.apply {
        if (changeState) changeState()
    }

    private val mIsPlay = MutableLiveData(false)
    fun play(changeAtEvenClick: Boolean = false): LiveData<Boolean> {
        if (changeAtEvenClick) mIsPlay.value = !(mIsPlay.value ?: false)
        return mIsPlay
    }

    private fun MutableLiveData<BinaryState>.changeState() {
        value = when (value) {
            is BinaryState.StateOff -> BinaryState.StateOn
            is BinaryState.StateOn -> BinaryState.StateOff
            else -> BinaryState.StateOff
        }
    }
}
