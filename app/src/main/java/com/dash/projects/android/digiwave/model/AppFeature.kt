package com.dash.projects.android.digiwave.model

import android.os.Parcelable
import com.dash.projects.android.digiwave.enum.FeatureName
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppFeature(
    val featureImage: Int,
    val featureName: Int,
    val featureDescription: Int,
    val featureType: FeatureName,
) : Parcelable
