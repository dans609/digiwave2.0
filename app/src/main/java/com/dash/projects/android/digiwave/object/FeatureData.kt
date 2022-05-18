package com.dash.projects.android.digiwave.`object`

import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableResListOf
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringResListOf
import com.dash.projects.android.digiwave.enum.FeatureName
import com.dash.projects.android.digiwave.model.AppFeature

object FeatureData {
    private val featureNames = stringResListOf(
        R.string.numberSystemFeatureName,
        R.string.karnaughMapFeatureName,
        R.string.logicGateFeatureName,
    )

    private val featureDescriptions = stringResListOf(
        R.string.numberSystemFeatureDesc,
        R.string.karnaughMapFeatureDesc,
        R.string.logicGateFeatureDesc,
    )

    private val featureImages = drawableResListOf(
        R.drawable.ic_feature_numsys,
        R.drawable.ic_feature_kmap,
        R.drawable.ic_feature_logate,
    )

    private val featureTypes = listOf(
        FeatureName.NumberSystem,
        FeatureName.KarnaughMap,
        FeatureName.LogicGate,
    )

    fun generateFeatures(): List<AppFeature> = ArrayList<AppFeature>().apply {
        featureNames.forEachIndexed { index, featureName ->
            add(
                AppFeature(
                    featureImage = featureImages[index],
                    featureName = featureName,
                    featureDescription = featureDescriptions[index],
                    featureType = featureTypes[index],
                )
            )
        }
    }
}
