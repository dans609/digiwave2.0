package com.dash.projects.android.digiwave.views.features.numsys.branch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.databinding.FragmentCodeSystemCategoryBinding

class CodeSystemCategoryFragment : Fragment() {
    private var _binding: FragmentCodeSystemCategoryBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCodeSystemCategoryBinding.inflate(inflater, container, IS_ATTACH).let {
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
