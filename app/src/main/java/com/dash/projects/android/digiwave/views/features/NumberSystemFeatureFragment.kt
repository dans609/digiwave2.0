package com.dash.projects.android.digiwave.views.features

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.databinding.FragmentNumberSystemFeatureBinding

class NumberSystemFeatureFragment : Fragment() {
    private var _binding: FragmentNumberSystemFeatureBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ::_binding.set(FragmentNumberSystemFeatureBinding.inflate(inflater, container, false))
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}