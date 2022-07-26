package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.databinding.FragmentKmapTwoBinding

class KmapTwoFragment : Fragment() {
    private var _binding: FragmentKmapTwoBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapTwoBinding.inflate(inflater, container, IS_ATTACH).let {
        ::_binding.set(it)
        binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    companion object {
        const val IS_ATTACH = false
    }
}
