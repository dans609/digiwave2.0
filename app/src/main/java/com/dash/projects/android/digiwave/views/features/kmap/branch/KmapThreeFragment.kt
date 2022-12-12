package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.adds
import com.dash.projects.android.digiwave.`object`.utils.Utils.clickEvent
import com.dash.projects.android.digiwave.`object`.utils.Utils.disposeAll
import com.dash.projects.android.digiwave.`object`.utils.Utils.eachIsNotEmpty
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.notContainedIn
import com.dash.projects.android.digiwave.`object`.utils.Utils.observeTextView
import com.dash.projects.android.digiwave.`object`.utils.Utils.str
import com.dash.projects.android.digiwave.`object`.utils.Utils.strIntRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringRes
import com.dash.projects.android.digiwave.databinding.FragmentKmapThreeBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap3GraphicBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap3TilesBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmapRowTilesBinding
import com.dash.projects.android.digiwave.sealed.BinaryState
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapThreeViewModel
import com.dash.projects.android.digiwave.factory.viewmodel.ViewModelFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class KmapThreeFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private var valueStateOn: ((String) -> Unit)? = null

    private var _binding: FragmentKmapThreeBinding? = null
    private val binding
        get() = _binding

    private var _viewModel: KmapThreeViewModel? = null
    private val viewModel
        get() = _viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapThreeBinding.inflate(inflater, container, false).let {
        ::_binding.set(it)
        ::_viewModel.set(
            ViewModelProvider(
                this, ViewModelFactory.getInstance()
            )[KmapThreeViewModel::class.java]
        )
        binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val variable = getString(R.string.kmap_variables, KMAP_VARIABLE)
        val context = requireContext()

        activity?.also { fa ->
            binding?.incKmapGraphic?.let { graph ->
                graph.setAnswer(context)
                graph.incKmapVariable.kmapVariable.text = variable
                graph.incKmapType.kmapType.text = getString(R.string.kmap_type)
                graph.incKmapTiles.apply {
                    setIdentifier(context)
                    viewModel?.apply {
                        incRowTiles1.apply {
                            state000.observeCell(fa, tvKmapOption00)
                            state001.observeCell(fa, tvKmapOption01)
                            state011.observeCell(fa, tvKmapOption11)
                            state010.observeCell(fa, tvKmapOption10)

                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { setState000() },
                                tvKmapOption01.clickEvent().subscribe { setState001() },
                                tvKmapOption11.clickEvent().subscribe { setState011() },
                                tvKmapOption10.clickEvent().subscribe { setState010() },
                            )
                        }

                        incRowTiles2.apply {
                            state100.observeCell(fa, tvKmapOption00)
                            state101.observeCell(fa, tvKmapOption01)
                            state111.observeCell(fa, tvKmapOption11)
                            state110.observeCell(fa, tvKmapOption10)

                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { setState100() },
                                tvKmapOption01.clickEvent().subscribe { setState101() },
                                tvKmapOption11.clickEvent().subscribe { setState111() },
                                tvKmapOption10.clickEvent().subscribe { setState110() },
                            )
                        }
                    }
                }
            }
        }
    }

    private fun LiveData<BinaryState>.observeCell(fa: FragmentActivity, tv: TextView) =
        fa.applicationContext.run {
            observe(fa) {
                tv.text = when (it) {
                    is BinaryState.StateOn -> str(it.value).apply { valueStateOn?.invoke(this) }
                    is BinaryState.StateOff -> null
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


    private fun LayoutKmap3GraphicBinding.setAnswer(c: Context) = incKmapAnswer.apply {
        fun TextView.ref() = observeTextView()
        fun observeAll(vararg tvs: TextView) = tvs.map(TextView::ref)
        fun LayoutKmapRowTilesBinding.getValue() =
            observeAll(tvKmapOption00, tvKmapOption01, tvKmapOption11, tvKmapOption10).apply {
                disposables.disposeAll(*toTypedArray())
            }

        valueStateOn = { high ->
            val (cell000, cell001, cell011, cell010) = incKmapTiles.incRowTiles1.getValue()
            val (cell100, cell101, cell111, cell110) = incKmapTiles.incRowTiles2.getValue()

            disposables.add(
                Observable.combineLatest(
                    cell000, cell001, cell011, cell010, cell100, cell101, cell111, cell110
                ) { p000, p001, p011, p010, p100, p101, p111, p110 ->
                    when {
                        high.notContainedIn(p000, p001, p011, p010, p100, p101, p111, p110) ->
                            c.intRes(R.integer.low)
                        high.eachIsNotEmpty(p110) {
                            adds(p000, p001, p011, p010, p100, p101, p111)
                        } -> "A.B.!C"
                        high.eachIsNotEmpty(p111) {
                            adds(p000, p001, p011, p010, p100, p101, p110)
                        } -> "A.B.C"
                        high.eachIsNotEmpty(p111, p110) {
                            adds(p000, p001, p011, p010, p100, p101)
                        } -> "A.B"
                        high.eachIsNotEmpty(p101) {
                            adds(p000, p001, p011, p010, p100, p111, p110)
                        } -> "A.!B.C"
                        high.eachIsNotEmpty(p101, p110) {
                            adds(p000, p001, p011, p010, p100, p111)
                        } -> "(A.!B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p101, p111) {
                            adds(p000, p001, p011, p010, p100, p110)
                        } -> "A.C"
                        high.eachIsNotEmpty(p101, p111, p110) {
                            adds(p000, p001, p011, p010, p100)
                        } -> "(A.C) + (A.B)"
                        high.eachIsNotEmpty(p100) {
                            adds(p000, p001, p011, p010, p101, p111, p110)
                        } -> "A.!B.!C"
                        high.eachIsNotEmpty(p100, p110) {
                            adds(p000, p001, p011, p010, p101, p111)
                        } -> "A.!C"
                        high.eachIsNotEmpty(p100, p111) {
                            adds(p000, p001, p011, p010, p101, p110)
                        } -> "(A.!B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p100, p111, p110) {
                            adds(p000, p001, p011, p010, p101)
                        } -> "(A.!C) + (A.B)"
                        high.eachIsNotEmpty(p100, p101) {
                            adds(p000, p001, p011, p010, p111, p110)
                        } -> "A.!B"
                        high.eachIsNotEmpty(p100, p101, p110) {
                            adds(p000, p001, p011, p010, p111)
                        } -> "(A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p100, p101, p111) {
                            adds(p000, p001, p011, p010, p110)
                        } -> "(A.!B) + (A.C)"
                        high.eachIsNotEmpty(p100, p101, p111, p110) {
                            adds(p000, p001, p011, p010)
                        } -> "A"
                        high.eachIsNotEmpty(p010) {
                            adds(p000, p001, p011, p100, p101, p111, p110)
                        } -> "!A.B.!C"
                        high.eachIsNotEmpty(p010, p110) {
                            adds(p000, p001, p011, p100, p101, p111)
                        } -> "B.!C"
                        high.eachIsNotEmpty(p010, p111) {
                            adds(p000, p001, p011, p100, p101, p110)
                        } -> "(!A.B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p010, p111, p110) {
                            adds(p000, p001, p011, p100, p101)
                        } -> "(B.!C) + (A.B)"
                        high.eachIsNotEmpty(p010, p101) {
                            adds(p000, p001, p011, p100, p111, p110)
                        } -> "(!A.B.!C) + (A.!B.C)"
                        high.eachIsNotEmpty(p010, p101, p110) {
                            adds(p000, p001, p011, p100, p111)
                        } -> "(A.!B.C) + (B.!C)"
                        high.eachIsNotEmpty(p010, p101, p111) {
                            adds(p000, p001, p011, p100, p110)
                        } -> "(!A.B.!C) + (A.C)"
                        high.eachIsNotEmpty(p010, p101, p111, p110) {
                            adds(p000, p001, p011, p100)
                        } -> "(B.!C) + (A.C)"
                        high.eachIsNotEmpty(p010, p100) {
                            adds(p000, p001, p011, p101, p111, p110)
                        } -> "(!A.B.!C) + (A.!B.!C)"
                        high.eachIsNotEmpty(p010, p100, p110) {
                            adds(p000, p001, p011, p101, p111)
                        } -> "(B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p010, p100, p111) {
                            adds(p000, p001, p011, p101, p110)
                        } -> "(!A.B.!C) + (A.!B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p010, p100, p111, p110) {
                            adds(p000, p001, p011, p101)
                        } -> "(B.!C) + (A.!C) + (A.B)"
                        high.eachIsNotEmpty(p010, p100, p101) {
                            adds(p000, p001, p011, p111, p110)
                        } -> "(!A.B.!C) + (A.!B)"
                        high.eachIsNotEmpty(p010, p100, p101, p110) {
                            adds(p000, p001, p011, p111)
                        } -> "(B.!C) + (A.!B)"
                        high.eachIsNotEmpty(p010, p100, p101, p111) {
                            adds(p000, p001, p011, p110)
                        } -> "(!A.B.!C) + (A.!B) + (A.C)"
                        high.eachIsNotEmpty(p010, p100, p101, p111, p110) {
                            adds(p000, p001, p011)
                        } -> "(B.!C) + (A)"
                        high.eachIsNotEmpty(p011) {
                            adds(p000, p001, p010, p100, p101, p111, p110)
                        } -> "!A.B.C"
                        high.eachIsNotEmpty(p011, p110) {
                            adds(p000, p001, p010, p100, p101, p111)
                        } -> "(!A.B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p011, p111) {
                            adds(p000, p001, p010, p100, p101, p110)
                        } -> "B.C"
                        high.eachIsNotEmpty(p011, p111, p110) {
                            adds(p000, p001, p010, p100, p101)
                        } -> "(B.C) + (A.B)"
                        high.eachIsNotEmpty(p011, p101) {
                            adds(p000, p001, p010, p100, p111, p110)
                        } -> "(!A.B.C) + (A.!B.C)"
                        high.eachIsNotEmpty(p011, p101, p110) {
                            adds(p000, p001, p010, p100, p111)
                        } -> "(!A.B.C) + (A.!B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p011, p101, p111) {
                            adds(p000, p001, p010, p100, p110)
                        } -> "(B.C) + (A.C)"
                        high.eachIsNotEmpty(p011, p101, p111, p110) {
                            adds(p000, p001, p010, p100)
                        } -> "(B.C) + (A.C) + (A.B)"
                        high.eachIsNotEmpty(p011, p100) {
                            adds(p000, p001, p010, p101, p111, p110)
                        } -> "(A.!B.!C) + (!A.B.C)"
                        high.eachIsNotEmpty(p011, p100, p110) {
                            adds(p000, p001, p010, p101, p111)
                        } -> "(!A.B.C) + (A.!C)"
                        high.eachIsNotEmpty(p011, p100, p111) {
                            adds(p000, p001, p010, p101, p110)
                        } -> "(A.!B.!C) + (B.C)"
                        high.eachIsNotEmpty(p011, p100, p111, p110) {
                            adds(p000, p001, p010, p101)
                        } -> "(A.!C) + (B.C)"
                        high.eachIsNotEmpty(p011, p100, p101) {
                            adds(p000, p001, p010, p111, p110)
                        } -> "(!A.B.C) + (A.!B)"
                        high.eachIsNotEmpty(p011, p100, p101, p110) {
                            adds(p000, p001, p010, p111)
                        } -> "(!A.B.C) + (A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p011, p100, p101, p111) {
                            adds(p000, p001, p010, p110)
                        } -> "(A.!B) + (B.C)"
                        high.eachIsNotEmpty(p011, p100, p101, p111, p110) {
                            adds(p000, p001, p010)
                        } -> "(B.C) + (A)"
                        high.eachIsNotEmpty(p011, p010) {
                            adds(p000, p001, p100, p101, p111, p110)
                        } -> "!A.B"
                        high.eachIsNotEmpty(p011, p010, p110) {
                            adds(p000, p001, p100, p101, p111)
                        } -> "(!A.B) + (B.!C)"
                        high.eachIsNotEmpty(p011, p010, p111) {
                            adds(p000, p001, p100, p101, p110)
                        } -> "(!A.B) + (B.C)"
                        high.eachIsNotEmpty(p011, p010, p111, p110) {
                            adds(p000, p001, p100, p101)
                        } -> "B"
                        high.eachIsNotEmpty(p011, p010, p101) {
                            adds(p000, p001, p100, p111, p110)
                        } -> "(A.!B.C) + (!A.B)"
                        high.eachIsNotEmpty(p011, p010, p101, p110) {
                            adds(p000, p001, p100, p111)
                        } -> "(A.!B.C) + (!A.B) + (B.!C)"
                        high.eachIsNotEmpty(p011, p010, p101, p111) {
                            adds(p000, p001, p100, p110)
                        } -> "(!A.B) + (A.C)"
                        high.eachIsNotEmpty(p011, p010, p101, p111, p110) {
                            adds(p000, p001, p100)
                        } -> "(A.C) + (B)"
                        high.eachIsNotEmpty(p011, p010, p100) {
                            adds(p000, p001, p101, p111, p110)
                        } -> "(A.!B.!C) + (!A.B)"
                        high.eachIsNotEmpty(p011, p010, p100, p110) {
                            adds(p000, p001, p101, p111)
                        } -> "(!A.B) + (A.!C)"
                        high.eachIsNotEmpty(p011, p010, p100, p111) {
                            adds(p000, p001, p101, p110)
                        } -> "(A.!B.!C) + (!A.B) + (B.C)"
                        high.eachIsNotEmpty(p011, p010, p100, p111, p110) {
                            adds(p000, p001, p101)
                        } -> "(A.!C) + (B)"
                        high.eachIsNotEmpty(p011, p010, p100, p101) {
                            adds(p000, p001, p111, p110)
                        } -> "(!A.B) + (A.!B)"
                        high.eachIsNotEmpty(p011, p010, p100, p101, p110) {
                            adds(p000, p001, p111)
                        } -> "(!A.B) + (A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p011, p010, p100, p101, p111) {
                            adds(p000, p001, p110)
                        } -> "(!A.B) + (A.!B) + (A.C)"
                        high.eachIsNotEmpty(p011, p010, p100, p101, p111, p110) {
                            adds(p000, p001)
                        } -> "(B) + (A)"
                        high.eachIsNotEmpty(p001) {
                            adds(p000, p011, p010, p100, p101, p111, p110)
                        } -> "!A.!B.C"
                        high.eachIsNotEmpty(p001, p110) {
                            adds(p000, p011, p010, p100, p101, p111)
                        } -> "(!A.!B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p001, p111) {
                            adds(p000, p011, p010, p100, p101, p110)
                        } -> "(!A.!B.C) + (A.B.C)"
                        high.eachIsNotEmpty(p001, p111, p110) {
                            adds(p000, p011, p010, p100, p101)
                        } -> "(!A.!B.C) + (A.B)"
                        high.eachIsNotEmpty(p001, p101) {
                            adds(p000, p011, p010, p100, p111, p110)
                        } -> "!B.C"
                        high.eachIsNotEmpty(p001, p101, p110) {
                            adds(p000, p011, p010, p100, p111)
                        } -> "(A.B.!C) + (!B.C)"
                        high.eachIsNotEmpty(p001, p101, p111) {
                            adds(p000, p011, p010, p100, p110)
                        } -> "(!B.C) + (A.C)"
                        high.eachIsNotEmpty(p001, p101, p111, p110) {
                            adds(p000, p011, p010, p100)
                        } -> "(!B.C) + (A.B)"
                        high.eachIsNotEmpty(p001, p100) {
                            adds(p000, p011, p010, p101, p111, p110)
                        } -> "(!A.!B.C) + (A.!B.!C)"
                        high.eachIsNotEmpty(p001, p100, p110) {
                            adds(p000, p011, p010, p101, p111)
                        } -> "(!A.!B.C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p100, p111) {
                            adds(p000, p011, p010, p101, p110)
                        } -> "(!A.!B.C) + (A.!B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p001, p100, p111, p110) {
                            adds(p000, p011, p010, p101)
                        } -> "(!A.!B.C) + (A.!C) + (A.B)"
                        high.eachIsNotEmpty(p001, p100, p101) {
                            adds(p000, p011, p010, p111, p110)
                        } -> "(!B.C) + (A.!B)"
                        high.eachIsNotEmpty(p001, p100, p101, p110) {
                            adds(p000, p011, p010, p111)
                        } -> "(!B.C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p100, p101, p111) {
                            adds(p000, p011, p010, p110)
                        } -> "(!B.C) + (A.!B) + (A.C)"
                        high.eachIsNotEmpty(p001, p100, p101, p111, p110) {
                            adds(p000, p011, p010)
                        } -> "(!B.C) + (A)"
                        high.eachIsNotEmpty(p001, p010) {
                            adds(p000, p011, p100, p101, p111, p110)
                        } -> "(!A.!B.C) + (!A.B.!C)"
                        high.eachIsNotEmpty(p001, p010, p110) {
                            adds(p000, p011, p100, p101, p111)
                        } -> "(!A.!B.C) + (B.!C)"
                        high.eachIsNotEmpty(p001, p010, p111) {
                            adds(p000, p011, p100, p101, p110)
                        } -> "(!A.!B.C) + (!A.B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p001, p010, p111, p110) {
                            adds(p000, p011, p100, p101)
                        } -> "(!A.!B.C) + (B.!C) + (A.B)"
                        high.eachIsNotEmpty(p001, p010, p101) {
                            adds(p000, p011, p100, p111, p110)
                        } -> "(!A.B.!C) + (!B.C)"
                        high.eachIsNotEmpty(p001, p010, p101, p110) {
                            adds(p000, p011, p100, p111)
                        } -> "(!B.C) + (B.!C)"
                        high.eachIsNotEmpty(p001, p010, p101, p111) {
                            adds(p000, p011, p100, p110)
                        } -> "(!A.B.!C) + (!B.C) + (A.C)"
                        high.eachIsNotEmpty(p001, p010, p101, p111, p110) {
                            adds(p000, p011, p100)
                        } -> "(!B.C) + (B.!C) + (A.B)"
                        high.eachIsNotEmpty(p001, p010, p100) {
                            adds(p000, p011, p101, p111, p110)
                        } -> "(!A.!B.C) + (!A.B.!C) + (A.!B.!C)"
                        high.eachIsNotEmpty(p001, p010, p100, p110) {
                            adds(p000, p011, p101, p111)
                        } -> "(!A.!B.C) + (B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p010, p100, p111) {
                            adds(p000, p011, p101, p110)
                        } -> "(!A.!B.C) + (!A.B.!C) + (A.!B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p001, p010, p100, p111, p110) {
                            adds(p000, p011, p101)
                        } -> "(!A.!B.C) + (B.!C) + (A.!C) + (A.B)"
                        high.eachIsNotEmpty(p001, p010, p100, p101) {
                            adds(p000, p011, p111, p110)
                        } -> "(!A.B.!C) + (!B.C) + (A.!B)"
                        high.eachIsNotEmpty(p001, p010, p100, p101, p110) {
                            adds(p000, p011, p111)
                        } -> "(!B.C) + (B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p010, p100, p101, p111) {
                            adds(p000, p011, p110)
                        } -> "(!A.B.!C) + (!B.C) + (A.!B) + (AC)"
                        high.eachIsNotEmpty(p001, p010, p100, p101, p111, p110) {
                            adds(p000, p011)
                        } -> "(!B.C) + (B.!C) + (A)"
                        high.eachIsNotEmpty(p001, p011) {
                            adds(p000, p010, p100, p101, p111, p110)
                        } -> "!A.C"
                        high.eachIsNotEmpty(p001, p011, p110) {
                            adds(p000, p010, p100, p101, p111)
                        } -> "(A.B.!C) + (!A.C)"
                        high.eachIsNotEmpty(p001, p011, p111) {
                            adds(p000, p010, p100, p101, p110)
                        } -> "(!A.C) + (B.C)"
                        high.eachIsNotEmpty(p001, p011, p111, p110) {
                            adds(p000, p010, p100, p101)
                        } -> "(!A.C) + (A.B)"
                        high.eachIsNotEmpty(p001, p011, p101) {
                            adds(p000, p010, p100, p111, p110)
                        } -> "(!A.C) + (!B.C)"
                        high.eachIsNotEmpty(p001, p011, p101, p110) {
                            adds(p000, p010, p100, p111)
                        } -> "(A.B.!C) + (!A.C) + (!B.C)"
                        high.eachIsNotEmpty(p001, p011, p101, p111) {
                            adds(p000, p010, p100, p110)
                        } -> "C"
                        high.eachIsNotEmpty(p001, p011, p101, p111, p110) {
                            adds(p000, p010, p100)
                        } -> "(A.B) + (C)"
                        high.eachIsNotEmpty(p001, p011, p100) {
                            adds(p000, p010, p101, p111, p110)
                        } -> "(A.!B.!C) + (!A.C)"
                        high.eachIsNotEmpty(p001, p011, p100, p110) {
                            adds(p000, p010, p101, p111)
                        } -> "(!A.C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p011, p100, p111) {
                            adds(p000, p010, p101, p110)
                        } -> "(A.!B.!C) + (!A.C) + (B.C)"
                        high.eachIsNotEmpty(p001, p011, p100, p111, p110) {
                            adds(p000, p010, p101)
                        } -> "(!A.C) + (A.!C) + (A.B)"
                        high.eachIsNotEmpty(p001, p011, p100, p101) {
                            adds(p000, p010, p111, p110)
                        } -> "(!A.C) + (A.!B)"
                        high.eachIsNotEmpty(p001, p011, p100, p101, p110) {
                            adds(p000, p010, p111)
                        } -> "(!A.C) + (A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p001, p011, p100, p101, p111) {
                            adds(p000, p010, p110)
                        } -> "(A.!B) + (C)"
                        high.eachIsNotEmpty(p001, p011, p100, p101, p111, p110) {
                            adds(p000, p010)
                        } -> "(C) + (A)"
                        high.eachIsNotEmpty(p001, p011, p010) {
                            adds(p000, p100, p101, p111, p110)
                        } -> "(!A.C) + (!A.B)"
                        high.eachIsNotEmpty(p001, p011, p010, p110) {
                            adds(p000, p100, p101, p111)
                        } -> "(!A.C) + (B.!C)"
                        high.eachIsNotEmpty(p001, p011, p010, p111) {
                            adds(p000, p100, p101, p110)
                        } -> "(!A.C) + (!A.B) + (B.C)"
                        high.eachIsNotEmpty(p001, p011, p010, p111, p110) {
                            adds(p000, p100, p101)
                        } -> "(!A.C) + (B)"
                        high.eachIsNotEmpty(p001, p011, p010, p101) {
                            adds(p000, p100, p111, p110)
                        } -> "(!B.C) + (!A.B)"
                        high.eachIsNotEmpty(p001, p011, p010, p101, p110) {
                            adds(p000, p100, p111)
                        } -> "(!B.C) + (!A.B) + (B.!C)"
                        high.eachIsNotEmpty(p001, p011, p010, p101, p111) {
                            adds(p000, p100, p110)
                        } -> "(!A.B) + (C)"
                        high.eachIsNotEmpty(p001, p011, p010, p101, p111, p110) {
                            adds(p000, p100)
                        } -> "(C) + (B)"
                        high.eachIsNotEmpty(p001, p011, p010, p100) {
                            adds(p000, p101, p111, p110)
                        } -> "(A.!B.!C) + (!A.C) + (!A.B)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p110) {
                            adds(p000, p101, p111)
                        } -> "(!A.C) + (B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p111) {
                            adds(p000, p101, p110)
                        } -> "(A.!B.!C) + (!A.C) + (!A.B) + (B.C)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p111, p110) {
                            adds(p000, p101)
                        } -> "(!A.C) + (A.!C) + (B)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p101) {
                            adds(p000, p111, p110)
                        } -> "(!B.C) + (!A.B) + (A.!B)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p101, p110) {
                            adds(p000, p111)
                        } -> "(!B.C) + (!A.B) + (A.!C)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p101, p111) {
                            adds(p000, p110)
                        } -> "(!A.B) + (A.!B) + (C)"
                        high.eachIsNotEmpty(p001, p011, p010, p100, p101, p111, p110) {
                            adds(p000)
                        } -> "(C) + (B) + (A)"
                        high.eachIsNotEmpty(p000) {
                            adds(p001, p011, p010, p100, p101, p111, p110)
                        } -> "!A.!B.!C"
                        high.eachIsNotEmpty(p000, p110) {
                            adds(p001, p011, p010, p100, p101, p111)
                        } -> "(!A.!B.!C) + (A.B.!C)"
                        high.eachIsNotEmpty(p000, p111) {
                            adds(p001, p011, p010, p100, p101, p110)
                        } -> "(!A.!B.!C) + (A.B.C)"
                        high.eachIsNotEmpty(p000, p111, p110) {
                            adds(p001, p011, p010, p100, p101)
                        } -> "(!A.!B.!C) + (A.B)"
                        high.eachIsNotEmpty(p000, p101) {
                            adds(p001, p011, p010, p100, p111, p110)
                        } -> "(!A.!B.!C) + (A.!B.C)"
                        high.eachIsNotEmpty(p000, p101, p110) {
                            adds(p001, p011, p010, p100, p111)
                        } -> "(!A.!B.!C) + (A.!B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p000, p101, p111) {
                            adds(p001, p011, p010, p100, p110)
                        } -> "(!A.!B.!C) + (A.C)"
                        high.eachIsNotEmpty(p000, p101, p111, p110) {
                            adds(p001, p011, p010, p100)
                        } -> "(!A.!B.!C) + (A.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p100) {
                            adds(p001, p011, p010, p101, p111, p110)
                        } -> "!B.!C"
                        high.eachIsNotEmpty(p000, p100, p110) {
                            adds(p001, p011, p010, p101, p111)
                        } -> "(!B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p000, p100, p111) {
                            adds(p001, p011, p010, p101, p110)
                        } -> "(A.B.C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p100, p111, p110) {
                            adds(p001, p011, p010, p101)
                        } -> "(!B.!C) + (A.B)"
                        high.eachIsNotEmpty(p000, p100, p101) {
                            adds(p001, p011, p010, p111, p110)
                        } -> "(!B.!C) + (A.!B)"
                        high.eachIsNotEmpty(p000, p100, p101, p110) {
                            adds(p001, p011, p010, p111)
                        } -> "(!B.!C) + (A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p000, p100, p101, p111) {
                            adds(p001, p011, p010, p110)
                        } -> "(!B.!C) + (A.C)"
                        high.eachIsNotEmpty(p000, p100, p101, p111, p110) {
                            adds(p001, p011, p010)
                        } -> "(!B.!C) + (A)"
                        high.eachIsNotEmpty(p000, p010) {
                            adds(p001, p011, p100, p101, p111, p110)
                        } -> "!A.!C"
                        high.eachIsNotEmpty(p000, p010, p110) {
                            adds(p001, p011, p100, p101, p111)
                        } -> "(!A.!C) + (B.!C)"
                        high.eachIsNotEmpty(p000, p010, p111) {
                            adds(p001, p011, p100, p101, p110)
                        } -> "(A.B.C) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p010, p111, p110) {
                            adds(p001, p011, p100, p101)
                        } -> "(!A.!C) + (A.B)"
                        high.eachIsNotEmpty(p000, p010, p101) {
                            adds(p001, p011, p100, p111, p110)
                        } -> "(A.!B.C) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p010, p101, p110) {
                            adds(p001, p011, p100, p111)
                        } -> "(A.!B.C) + (!A.!C) + (B.!C)"
                        high.eachIsNotEmpty(p000, p010, p101, p111) {
                            adds(p001, p011, p100, p110)
                        } -> "(!A.!C) + (A.C)"
                        high.eachIsNotEmpty(p000, p010, p101, p111, p110) {
                            adds(p001, p011, p100)
                        } -> "(!A.!C) + (A.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p010, p100) {
                            adds(p001, p011, p101, p111, p110)
                        } -> "(!A.!C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p010, p100, p110) {
                            adds(p001, p011, p101, p111)
                        } -> "!C"
                        high.eachIsNotEmpty(p000, p010, p100, p111) {
                            adds(p001, p011, p101, p110)
                        } -> "(A.B.C) + (!A.!C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p010, p100, p111, p110) {
                            adds(p001, p011, p101)
                        } -> "(A.B) + (!C)"
                        high.eachIsNotEmpty(p000, p010, p100, p101) {
                            adds(p001, p011, p111, p110)
                        } -> "(A.!B) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p010, p100, p101, p110) {
                            adds(p001, p011, p111)
                        } -> "(A.!B) + (!C)"
                        high.eachIsNotEmpty(p000, p010, p100, p101, p111) {
                            adds(p001, p011, p110)
                        } -> "(!A.!C) + (!B.!C) + (A.C)"
                        high.eachIsNotEmpty(p000, p010, p100, p101, p111, p110) {
                            adds(p001, p011)
                        } -> "(!C) + (A)"
                        high.eachIsNotEmpty(p000, p011) {
                            adds(p001, p010, p100, p101, p111, p110)
                        } -> "(!A.!B.!C) + (!A.B.C)"
                        high.eachIsNotEmpty(p000, p011, p110) {
                            adds(p001, p010, p100, p101, p111)
                        } -> "(!A.!B.!C) + (!A.B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p000, p011, p111) {
                            adds(p001, p010, p100, p101, p110)
                        } -> "(!A.!B.!C) + (B.C)"
                        high.eachIsNotEmpty(p000, p011, p111, p110) {
                            adds(p001, p010, p100, p101)
                        } -> "(!A.!B.!C) + (B.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p011, p101) {
                            adds(p001, p010, p100, p111, p110)
                        } -> "(!A.!B.!C) + (!A.B.C) + (A.!B.C)"
                        high.eachIsNotEmpty(p000, p011, p101, p110) {
                            adds(p001, p010, p100, p111)
                        } -> "(!A.!B.!C) + (!A.B.C) + (A.!B.C) + (A.B.!C)"
                        high.eachIsNotEmpty(p000, p011, p101, p111) {
                            adds(p001, p010, p100, p110)
                        } -> "(!A.!B.!C) + (B.C) + (A.C)"
                        high.eachIsNotEmpty(p000, p011, p101, p111, p110) {
                            adds(p001, p010, p100)
                        } -> "(!A.!B.!C) + (B.C) + (A.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p011, p100) {
                            adds(p001, p010, p101, p111, p110)
                        } -> "(!A.B.C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p011, p100, p110) {
                            adds(p001, p010, p101, p111)
                        } -> "(!A.B.C) + (!B.!C) + (A.!C)"
                        high.eachIsNotEmpty(p000, p011, p100, p111) {
                            adds(p001, p010, p101, p110)
                        } -> "(!B.!C) + (B.C)"
                        high.eachIsNotEmpty(p000, p011, p100, p111, p110) {
                            adds(p001, p010, p101)
                        } -> "(!B.!C) + (B.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p011, p100, p101) {
                            adds(p001, p010, p111, p110)
                        } -> "(!A.B.C) + (!B.!C) + (A.!B)"
                        high.eachIsNotEmpty(p000, p011, p100, p101, p110) {
                            adds(p001, p010, p111)
                        } -> "(!A.B.C) + (!B.!C) + (A.!B) + (A.!C)"
                        high.eachIsNotEmpty(p000, p011, p100, p101, p111) {
                            adds(p001, p010, p110)
                        } -> "(!B.!C) + (B.C) + (A.C)"
                        high.eachIsNotEmpty(p000, p011, p100, p101, p111, p110) {
                            adds(p001, p010)
                        } -> "(!B.!C) + (B.C) + (A)"
                        high.eachIsNotEmpty(p000, p011, p010) {
                            adds(p001, p100, p101, p111, p110)
                        } -> "(!A.!C) + (!A.B)"
                        high.eachIsNotEmpty(p000, p011, p010, p110) {
                            adds(p001, p100, p101, p111)
                        } -> "(!A.!C) + (!A.B) + (B.!C)"
                        high.eachIsNotEmpty(p000, p011, p010, p111) {
                            adds(p001, p100, p101, p110)
                        } -> "(!A.!C) + (B.C)"
                        high.eachIsNotEmpty(p000, p011, p010, p111, p110) {
                            adds(p001, p100, p101)
                        } -> "(!A.!C) + (B)"
                        high.eachIsNotEmpty(p000, p011, p010, p101) {
                            adds(p001, p100, p111, p110)
                        } -> "(A.!B.C) + (!A.!C) + (!A.B)"
                        high.eachIsNotEmpty(p000, p011, p010, p101, p110) {
                            adds(p001, p100, p111)
                        } -> "(A.!B.C) + (!A.!C) + (!A.B) + (B.!C)"
                        high.eachIsNotEmpty(p000, p011, p010, p101, p111) {
                            adds(p001, p100, p110)
                        } -> "(!A.!C) + (B.C) + (A.C)"
                        high.eachIsNotEmpty(p000, p011, p010, p101, p111, p110) {
                            adds(p001, p100)
                        } -> "(!A.!C) + (A.C) + (B)"
                        high.eachIsNotEmpty(p000, p011, p010, p100) {
                            adds(p001, p101, p111, p110)
                        } -> "(!A.B) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p110) {
                            adds(p001, p101, p111)
                        } -> "(!A.B) + (!C)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p111) {
                            adds(p001, p101, p110)
                        } -> "(!A.!C) + (!B.!C) + (B.C)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p111, p110) {
                            adds(p001, p101)
                        } -> "(!C) + (B)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p101) {
                            adds(p001, p111, p110)
                        } -> "(!B.!C) + (!A.B) + (A.!B)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p101, p110) {
                            adds(p001, p111)
                        } -> "(!A.B) + (A.!B) + (!C)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p101, p111) {
                            adds(p001, p110)
                        } -> "(!B.!C) + (!A.B) + (A.C)"
                        high.eachIsNotEmpty(p000, p011, p010, p100, p101, p111, p110) {
                            adds(p001)
                        } -> "(!C) + (B) + (A)"
                        high.eachIsNotEmpty(p000, p001) {
                            adds(p011, p010, p100, p101, p111, p110)
                        } -> "!A.!B"
                        high.eachIsNotEmpty(p000, p001, p110) {
                            adds(p011, p010, p100, p101, p111)
                        } -> "(A.B.!C) + (!A.!B)"
                        high.eachIsNotEmpty(p000, p001, p111) {
                            adds(p011, p010, p100, p101, p110)
                        } -> "(A.B.C) + (!A.!B)"
                        high.eachIsNotEmpty(p000, p001, p111, p110) {
                            adds(p011, p010, p100, p101)
                        } -> "(!A.!B) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p101) {
                            adds(p011, p010, p100, p111, p110)
                        } -> "(!A.!B) + (!B.C)"
                        high.eachIsNotEmpty(p000, p001, p101, p110) {
                            adds(p011, p010, p100, p111)
                        } -> "(A.B.!C) + (!A.!B) + (!B.C)"
                        high.eachIsNotEmpty(p000, p001, p101, p111) {
                            adds(p011, p010, p100, p110)
                        } -> "(!A.!B) + (A.C)"
                        high.eachIsNotEmpty(p000, p001, p101, p111, p110) {
                            adds(p011, p010, p100)
                        } -> "(!A.!B) + (A.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p100) {
                            adds(p011, p010, p101, p111, p110)
                        } -> "(!A.!B) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p001, p100, p110) {
                            adds(p011, p010, p101, p111)
                        } -> "(A.!C) + (!A.!B)"
                        high.eachIsNotEmpty(p000, p001, p100, p111) {
                            adds(p011, p010, p101, p110)
                        } -> "(A.B.C) + (!A.!B) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p001, p100, p111, p110) {
                            adds(p011, p010, p101)
                        } -> "(!A.!B) + (!B.!C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p100, p101) {
                            adds(p011, p010, p111, p110)
                        } -> "!B"
                        high.eachIsNotEmpty(p000, p001, p100, p101, p110) {
                            adds(p011, p010, p111)
                        } -> "(A.!C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p100, p101, p111) {
                            adds(p011, p010, p110)
                        } -> "(A.C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p100, p101, p111, p110) {
                            adds(p011, p010)
                        } -> "(!B) + (A)"
                        high.eachIsNotEmpty(p000, p001, p010) {
                            adds(p011, p100, p101, p111, p110)
                        } -> "(!A.!B) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p110) {
                            adds(p011, p100, p101, p111)
                        } -> "(B.!C) + (!A.!B)"
                        high.eachIsNotEmpty(p000, p001, p010, p111) {
                            adds(p011, p100, p101, p110)
                        } -> "(A.B.C) + (!A.!B) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p111, p110) {
                            adds(p011, p100, p101)
                        } -> "(!A.!B) + (!A.!C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p010, p101) {
                            adds(p011, p100, p111, p110)
                        } -> "(!B.C) + (!A.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p101, p110) {
                            adds(p011, p100, p111)
                        } -> "(!A.!C) + (!B.C) + (B.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p101, p111) {
                            adds(p011, p100, p110)
                        } -> "(!A.!B) + (!A.!C) + (A.C)"
                        high.eachIsNotEmpty(p000, p001, p010, p101, p111, p110) {
                            adds(p011, p100)
                        } -> "(!A.!C) + (!B.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p010, p100) {
                            adds(p011, p101, p111, p110)
                        } -> "(!A.!B) + (!A.!C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p110) {
                            adds(p011, p101, p111)
                        } -> "(!A.!B) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p111) {
                            adds(p011, p101, p110)
                        } -> "(A.B.C) + (!A.!B) + (!A.!C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p111, p110) {
                            adds(p011, p101)
                        } -> "(!A.!B) + (A.B) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p101) {
                            adds(p011, p111, p110)
                        } -> "(!A.!C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p101, p110) {
                            adds(p011, p111)
                        } -> "(!B) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p101, p111) {
                            adds(p011, p110)
                        } -> "(!A.!C) + (A.C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p010, p100, p101, p111, p110) {
                            adds(p011)
                        } -> "(A) + (!B) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p011) {
                            adds(p010, p100, p101, p111, p110)
                        } -> "(!A.!B) + (!A.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p110) {
                            adds(p010, p100, p101, p111)
                        } -> "(A.B.!C) + (!A.!B) + (!A.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p111) {
                            adds(p010, p100, p101, p110)
                        } -> "(!A.!B) + (B.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p111, p110) {
                            adds(p010, p100, p101)
                        } -> "(!A.!B) + (B.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p011, p101) {
                            adds(p010, p100, p111, p110)
                        } -> "(!A.!B) + (!A.C) + (!B.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p101, p110) {
                            adds(p010, p100, p111)
                        } -> "(A.B.!C) + (!A.!B) + (!A.C) + (!B.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p101, p111) {
                            adds(p010, p100, p110)
                        } -> "(!A.!B) + (C)"
                        high.eachIsNotEmpty(p000, p001, p011, p101, p111, p110) {
                            adds(p010, p100)
                        } -> "(!A.!B) + (A.B) + (C)"
                        high.eachIsNotEmpty(p000, p001, p011, p100) {
                            adds(p010, p101, p111, p110)
                        } -> "(!A.C) + (!B.!C)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p110) {
                            adds(p010, p101, p111)
                        } -> "(!B.!C) + (!A.C) + (A.!C)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p111) {
                            adds(p010, p101, p110)
                        } -> "(!A.!B) + (!B.!C) + (B.C)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p111, p110) {
                            adds(p010, p101)
                        } -> "(!B.!C) + (!A.C) + (A.B)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p101) {
                            adds(p010, p111, p110)
                        } -> "(!A.C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p101, p110) {
                            adds(p010, p111)
                        } -> "(!A.C) + (A.!C) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p101, p111) {
                            adds(p010, p110)
                        } -> "(!B) + (C)"
                        high.eachIsNotEmpty(p000, p001, p011, p100, p101, p111, p110) {
                            adds(p010)
                        } -> "(!B) + (C) + (A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010) {
                            adds(p100, p101, p111, p110)
                        } -> "!A"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p110) {
                            adds(p100, p101, p111)
                        } -> "(B.!C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p111) {
                            adds(p100, p101, p110)
                        } -> "(B.C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p111, p110) {
                            adds(p100, p101)
                        } -> "(!A) + (B)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p101) {
                            adds(p100, p111, p110)
                        } -> "(!B.C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p101, p110) {
                            adds(p100, p111)
                        } -> "(!B.C) + (B.!C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p101, p111) {
                            adds(p100, p110)
                        } -> "(!A) + (C)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p101, p111, p110) {
                            adds(p100)
                        } -> "(!A) + (C) + (B)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100) {
                            adds(p101, p111, p110)
                        } -> "(!B.!C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p110) {
                            adds(p101, p111)
                        } -> "(!A) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p111) {
                            adds(p101, p110)
                        } -> "(!B.!C) + (B.C) + (!A)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p111, p110) {
                            adds(p101)
                        } -> "(B) + (!A) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p101) {
                            adds(p111, p110)
                        } -> "(!A) + (!B)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p101, p110) {
                            adds(p111)
                        } -> "(!A) + (!B) + (!C)"
                        high.eachIsNotEmpty(p000, p001, p011, p010, p100, p101, p111) {
                            adds(p110)
                        } -> "(C) + (!A) + (!B)"
                        eachIsNotEmpty(p000, p001, p011, p010, p100, p101, p111, p110) -> high
                        else -> c.stringRes(R.string.kmap_answer_not_defined)
                    }.toString()
                }.subscribe { kmapAnswer.text = it })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        _binding = null
        _viewModel = null
    }

    companion object {
        private const val KMAP_VARIABLE = 3
    }
}
