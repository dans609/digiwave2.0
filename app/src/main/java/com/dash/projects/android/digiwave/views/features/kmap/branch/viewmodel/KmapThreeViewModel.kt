package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.sealed.BinaryState

class KmapThreeViewModel : ViewModel() {
    private val _state000 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state000: LiveData<BinaryState> get() = _state000
    fun setState000() = _state000.setState()

    private val _state001 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state001: LiveData<BinaryState> get() = _state001
    fun setState001() = _state001.setState()

    private val _state011 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state011: LiveData<BinaryState> get() = _state011
    fun setState011() = _state011.setState()

    private val _state010 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state010: LiveData<BinaryState> get() = _state010
    fun setState010() = _state010.setState()

    private val _state100 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state100: LiveData<BinaryState> get() = _state100
    fun setState100() = _state100.setState()

    private val _state101 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state101: LiveData<BinaryState> get() = _state101
    fun setState101() = _state101.setState()

    private val _state111 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state111: LiveData<BinaryState> get() = _state111
    fun setState111() = _state111.setState()

    private val _state110 = MutableLiveData<BinaryState>(BinaryState.StateOff)
    val state110: LiveData<BinaryState> get() = _state110
    fun setState110() = _state110.setState()

    private fun MutableLiveData<BinaryState>.setState() {
        value = when (value) {
            is BinaryState.StateOff -> BinaryState.StateOn
            is BinaryState.StateOn -> BinaryState.StateOff
            else -> BinaryState.StateOff
        }
    }
}
