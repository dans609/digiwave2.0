package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.databinding.FragmentKmapThreeBinding
import io.reactivex.disposables.CompositeDisposable

class KmapThreeFragment : Fragment() {
    private val disposables = CompositeDisposable()

    private var _binding: FragmentKmapThreeBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapThreeBinding.inflate(inflater, container, false).let {
        _binding = it
        binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.apply {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
