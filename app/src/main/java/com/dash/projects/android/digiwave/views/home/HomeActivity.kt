package com.dash.projects.android.digiwave.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.`object`.FeatureData.generateFeatures
import com.dash.projects.android.digiwave.adapter.FeatureAdapter
import com.dash.projects.android.digiwave.databinding.ActivityHomeBinding
import com.dash.projects.android.digiwave.enum.FeatureName
import com.dash.projects.android.digiwave.interfaces.OnFeatureClickCallback


class HomeActivity : AppCompatActivity(), OnFeatureClickCallback {
    private var _binding: ActivityHomeBinding? = null
    private val binding
        get() = _binding

    private var _featureAdapter: FeatureAdapter? = null
    private val featureAdapter
        get() = _featureAdapter

    private val getListFeature = generateFeatures()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        ::_binding.set(ActivityHomeBinding.inflate(layoutInflater))
        setContentView(binding?.root)

        ::_featureAdapter.set(FeatureAdapter(this))
        binding?.run {
            rvFeatures.setHasFixedSize(true)
            showRecyclerView(rvFeatures)
        }
    }

    private fun showRecyclerView(rv: RecyclerView) = featureAdapter?.also { adapter ->
        adapter.addFeatures(getListFeature)
        // rv.isVerticalFadingEdgeEnabled = true
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
        ::_featureAdapter.set(null)
    }

    override fun onFeatureClicked(featureName: FeatureName) {
        when (featureName) {
            FeatureName.KarnaughMap -> {}
            FeatureName.NumberSystem -> {}
            FeatureName.LogicGate -> {}
        }
    }
}