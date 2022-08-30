package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.sealed.KmapState

class KmapTwoViewModel : ViewModel() {
    private val _state00 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state00: LiveData<KmapState> get() = _state00
    fun setState00() = _state00.setState()

    private val _state01 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state01: LiveData<KmapState> get() = _state01
    fun setState01() = _state01.setState()

    private val _state11 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state11: LiveData<KmapState> get() = _state11
    fun setState11() = _state11.setState()

    private val _state10 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state10: LiveData<KmapState> get() = _state10
    fun setState10() = _state10.setState()

    private fun MutableLiveData<KmapState>.setState() {
        value = when (value) {
            is KmapState.StateOff -> KmapState.StateOn(R.integer.high)
            is KmapState.StateOn -> KmapState.StateOff
            else -> KmapState.StateOff
        }
    }
}
