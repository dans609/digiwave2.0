package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.sealed.KmapState

class KmapFourViewModel : ViewModel() {
    private val _state0000 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0000(setState: Boolean = false): LiveData<KmapState> = _state0000.apply {
        if (setState) setState()
    }

    private val _state0001 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0001(setState: Boolean = false): LiveData<KmapState> = _state0001.apply {
        if (setState) setState()
    }

    private val _state0011 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0011(setState: Boolean = false): LiveData<KmapState> = _state0011.apply {
        if (setState) setState()
    }

    private val _state0010 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0010(setState: Boolean = false): LiveData<KmapState> = _state0010.apply {
        if (setState) setState()
    }

    private val _state0100 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0100(setState: Boolean = false): LiveData<KmapState> = _state0100.apply {
        if (setState) setState()
    }

    private val _state0101 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0101(setState: Boolean = false): LiveData<KmapState> = _state0101.apply {
        if (setState) setState()
    }

    private val _state0111 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0111(setState: Boolean = false): LiveData<KmapState> = _state0111.apply {
        if (setState) setState()
    }

    private val _state0110 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state0110(setState: Boolean = false): LiveData<KmapState> = _state0110.apply {
        if (setState) setState()
    }

    private val _state1100 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1100(setState: Boolean = false): LiveData<KmapState> = _state1100.apply {
        if (setState) setState()
    }

    private val _state1101 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1101(setState: Boolean = false): LiveData<KmapState> = _state1101.apply {
        if (setState) setState()
    }

    private val _state1111 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1111(setState: Boolean = false): LiveData<KmapState> = _state1111.apply {
        if (setState) setState()
    }

    private val _state1110 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1110(setState: Boolean = false): LiveData<KmapState> = _state1110.apply {
        if (setState) setState()
    }

    private val _state1000 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1000(setState: Boolean = false): LiveData<KmapState> = _state1000.apply {
        if (setState) setState()
    }

    private val _state1001 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1001(setState: Boolean = false): LiveData<KmapState> = _state1001.apply {
        if (setState) setState()
    }

    private val _state1011 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1011(setState: Boolean = false): LiveData<KmapState> = _state1011.apply {
        if (setState) setState()
    }

    private val _state1010 = MutableLiveData<KmapState>(KmapState.StateOff)
    fun state1010(setState: Boolean = false): LiveData<KmapState> = _state1010.apply {
        if (setState) setState()
    }

    private fun MutableLiveData<KmapState>.setState() {
        value = when (value) {
            is KmapState.StateOff -> KmapState.StateOn(R.integer.high)
            is KmapState.StateOn -> KmapState.StateOff
            else -> KmapState.StateOff
        }
    }
}
