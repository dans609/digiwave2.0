package com.dash.projects.android.digiwave.sealed

sealed class BinaryState(val value: Int) {
    object StateOff : BinaryState(0)
    object StateOn : BinaryState(1)
}
