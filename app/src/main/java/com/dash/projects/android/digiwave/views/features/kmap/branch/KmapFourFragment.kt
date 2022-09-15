package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dash.projects.android.digiwave.databinding.FragmentKmapFourBinding

class KmapFourFragment : Fragment() {
    private var _binding: FragmentKmapFourBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapFourBinding.inflate(inflater, container, IS_ATTACH).run {
        ::_binding.set(this)
        binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    companion object {
        private const val IS_ATTACH = false
    }
}
