package com.dash.projects.android.digiwave.views.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.FeatureData.generateFeatures
import com.dash.projects.android.digiwave.adapter.home.FeatureAdapter
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

    override fun onFeatureClicked(featureName: FeatureName, itemView: View) = itemView.nav {
        when (featureName) {
            FeatureName.NumberSystem -> R.id.action_homeFragment_to_numberSystemActivity
            // the id bellow will changed later if task above is finish
            FeatureName.KarnaughMap -> R.id.action_homeFragment_to_numberSystemActivity
            FeatureName.LogicGate -> R.id.action_homeFragment_to_numberSystemActivity
        }
    }

    private fun View.nav(actionId: (View) -> Int) =
        findNavController().navigate(actionId(this))
}
