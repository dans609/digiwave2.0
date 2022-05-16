package com.dash.projects.android.digiwave.views.features.numsys.branch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.databinding.FragmentNumberSystemCategoryBinding

class NumberSystemCategoryFragment : Fragment() {
    private var _binding: FragmentNumberSystemCategoryBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentNumberSystemCategoryBinding.inflate(inflater, container, IS_ATTACH).let {
        ::_binding.set(it)
        binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.run {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    companion object {
        private const val IS_ATTACH = false
    }
}
