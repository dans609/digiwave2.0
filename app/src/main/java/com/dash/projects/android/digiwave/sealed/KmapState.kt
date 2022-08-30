package com.dash.projects.android.digiwave.sealed

import androidx.annotation.IntegerRes

sealed class KmapState {
    object StateOff : KmapState()
    data class StateOn(@IntegerRes val value: Int) : KmapState()
}
