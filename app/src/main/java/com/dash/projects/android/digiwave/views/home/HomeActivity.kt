package com.dash.projects.android.digiwave.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dash.projects.android.digiwave.databinding.ActivityHomeBinding
import com.dash.projects.android.digiwave.enum.FeatureName
import com.dash.projects.android.digiwave.interfaces.OnFeatureClickCallback

class HomeActivity : AppCompatActivity(), OnFeatureClickCallback {
    private var _binding: ActivityHomeBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ::_binding.set(ActivityHomeBinding.inflate(layoutInflater))
        setContentView(binding?.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    override fun onFeatureClicked(featureName: FeatureName) {
        when (featureName) {
            FeatureName.KarnaughMap -> {}
            FeatureName.NumberSystem -> {}
            FeatureName.LogicGate -> {}
        }
    }
}