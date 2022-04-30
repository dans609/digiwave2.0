package com.dash.projects.android.digiwave.interfaces

import com.dash.projects.android.digiwave.enum.FeatureName

interface OnFeatureClickCallback {
    fun onFeatureClicked(featureName: FeatureName)
}