package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.sealed.KmapState

class KmapThreeViewModel : ViewModel() {
    private val _state000 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state000: LiveData<KmapState> get() = _state000
    fun setState000() = _state000.setState()

    private val _state001 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state001: LiveData<KmapState> get() = _state001
    fun setState001() = _state001.setState()

    private val _state011 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state011: LiveData<KmapState> get() = _state011
    fun setState011() = _state011.setState()

    private val _state010 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state010: LiveData<KmapState> get() = _state010
    fun setState010() = _state010.setState()

    private val _state100 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state100: LiveData<KmapState> get() = _state100
    fun setState100() = _state100.setState()

    private val _state101 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state101: LiveData<KmapState> get() = _state101
    fun setState101() = _state101.setState()

    private val _state111 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state111: LiveData<KmapState> get() = _state111
    fun setState111() = _state111.setState()

    private val _state110 = MutableLiveData<KmapState>(KmapState.StateOff)
    val state110: LiveData<KmapState> get() = _state110
    fun setState110() = _state110.setState()

    private fun MutableLiveData<KmapState>.setState() {
        value = when (value) {
            is KmapState.StateOff -> KmapState.StateOn(R.integer.high)
            is KmapState.StateOn -> KmapState.StateOff
            else -> KmapState.StateOff
        }
    }
}
