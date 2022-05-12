package com.dash.projects.android.digiwave.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.`object`.FeatureData.generateFeatures
import com.dash.projects.android.digiwave.adapter.FeatureAdapter
import com.dash.projects.android.digiwave.databinding.FragmentHomeBinding
import com.dash.projects.android.digiwave.enum.FeatureName
import com.dash.projects.android.digiwave.interfaces.OnFeatureClickCallback

class HomeFragment : Fragment(), OnFeatureClickCallback {
    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding

    private var _featureAdapter: FeatureAdapter? = null
    private val featureAdapter
        get() = _featureAdapter

    private val getListFeature
        get() = generateFeatures()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ::_binding.set(FragmentHomeBinding.inflate(inflater, container, false))
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ::_featureAdapter.set(FeatureAdapter(this))

        binding?.run {
            rvFeatures.setHasFixedSize(true)
            showRecyclerView(rvFeatures)
        }
    }

    private fun showRecyclerView(rv: RecyclerView) = featureAdapter?.also { adapter ->
        adapter.addFeatures(getListFeature)
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    override fun onFeatureClicked(featureName: FeatureName) = when (featureName) {
        FeatureName.NumberSystem -> {}
        FeatureName.KarnaughMap -> {}
        FeatureName.LogicGate -> {}
    }
}