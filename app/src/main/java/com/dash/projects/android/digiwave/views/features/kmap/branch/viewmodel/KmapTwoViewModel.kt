package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.sealed.BinaryState

class KmapTwoViewModel : ViewModel() {
    private val _state00 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state00: LiveData<BinaryState> get() = _state00
    fun setState00() = _state00.setState()

    private val _state01 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state01: LiveData<BinaryState> get() = _state01
    fun setState01() = _state01.setState()

    private val _state11 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state11: LiveData<BinaryState> get() = _state11
    fun setState11() = _state11.setState()

    private val _state10 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state10: LiveData<BinaryState> get() = _state10
    fun setState10() = _state10.setState()

    private fun MutableLiveData<BinaryState>.setState() {
        value = when (value) {
            is BinaryState.StateOff -> BinaryState.StateOn
            is BinaryState.StateOn -> BinaryState.StateOff
            else -> BinaryState.StateOff
        }
    }
}
