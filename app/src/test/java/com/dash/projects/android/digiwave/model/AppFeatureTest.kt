package com.dash.projects.android.digiwave.model

import com.dash.projects.android.digiwave.enum.FeatureName
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AppFeatureTest {
    private val appFeature by lazy(LazyThreadSafetyMode.NONE) {
        AppFeature(120, 1101319319, 19210411, FeatureName.LogicGate)
    }

    @Test
    fun getFeatureImageId_validatingImageId_returnTrue() {
        val expectedImageId = 120
        assertEquals(expectedImageId, appFeature.featureImage)
    }

    @Test
    fun getFeatureNameId_validatingNameId_returnTrue() {
        val expectedNameId = 1101319319
        assertEquals(expectedNameId, appFeature.featureName)
    }

    @Test
    fun getFeatureDescId_validatingDescId_returnTrue() {
        val expectedDescId = 19210411
        assertEquals(expectedDescId, appFeature.featureDescription)
    }

    @Test
    fun getFeatureType_validatingFeatureType_returnTrue() {
        val expectedFeatureType = FeatureName.LogicGate
        assertEquals(expectedFeatureType, appFeature.featureType)
    }
}