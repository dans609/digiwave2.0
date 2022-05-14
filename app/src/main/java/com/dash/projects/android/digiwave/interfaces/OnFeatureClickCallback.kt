package com.dash.projects.android.digiwave.interfaces

import android.view.View
import com.dash.projects.android.digiwave.enum.FeatureName

interface OnFeatureClickCallback {
    fun onFeatureClicked(featureName: FeatureName, itemView: View)
}
