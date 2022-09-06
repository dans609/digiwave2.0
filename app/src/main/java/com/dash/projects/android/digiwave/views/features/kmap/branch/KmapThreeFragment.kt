package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.strIntRes
import com.dash.projects.android.digiwave.databinding.FragmentKmapThreeBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap3TilesBinding

class KmapThreeFragment : Fragment() {
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
        val variable = getString(R.string.kmap_variables, KMAP_VARIABLE)

        activity?.run {
            binding?.incKmapGraphic?.let { graph ->
                graph.incKmapVariable.kmapVariable.text = variable
                graph.incKmapType.kmapType.text = getString(R.string.kmap_type)
                graph.incKmapTiles.apply {
                    setIdentifier(requireContext())
                }
            }
        }
    }

    private fun LayoutKmap3TilesBinding.setIdentifier(context: Context) = context.apply {
        kmapVariableIdentifier1.text = getString(R.string.sample_kmap_2variable)
        kmapVariableIdentifier2.text = getString(R.string.sample_kmap_1variable)
        incRowTiles1.let { r1 ->
            r1.identifier00.text = strIntRes(R.integer.low)
            r1.identifier01.text = strIntRes(R.integer.high)
            r1.identifier11.text = strIntRes(R.integer.triad)
            r1.identifier10.text = strIntRes(R.integer.bitRadix)
        }

        incRowTiles2.let { r2 ->
            r2.identifier00.text = strIntRes(R.integer.nibble)
            r2.identifier01.text = strIntRes(R.integer.pentad)
            r2.identifier11.text = strIntRes(R.integer.heptad)
            r2.identifier10.text = strIntRes(R.integer.hexad)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val KMAP_VARIABLE = 3
    }
}
