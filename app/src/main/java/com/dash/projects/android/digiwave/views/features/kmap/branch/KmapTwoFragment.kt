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
import com.dash.projects.android.digiwave.`object`.utils.Utils.eachIsNotEmpty
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.notContainedIn
import com.dash.projects.android.digiwave.`object`.utils.Utils.observeTextView
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringRes
import com.dash.projects.android.digiwave.databinding.FragmentKmapTwoBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap2GraphicBinding
import com.dash.projects.android.digiwave.sealed.KmapState
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapTwoViewModel
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.factory.ViewModelFactory
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable

class KmapTwoFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private var valueStateOn: ((String) -> Unit)? = null

    private var _binding: FragmentKmapTwoBinding? = null
    private val binding
        get() = _binding

    private var _viewModel: KmapTwoViewModel? = null
    private val viewModel
        get() = _viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapTwoBinding.inflate(inflater, container, IS_ATTACH).let {
        ::_binding.set(it)
        ::_viewModel.set(
            ViewModelProvider(
                this, ViewModelFactory.getInstance()
            )[KmapTwoViewModel::class.java]
        )
        binding?.root
    }

    fun <T : TextView> T.text(text: String) =
        setText(text)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.run {
            binding?.incKmapGraphic?.let { graph ->
                graph.setAnswer(requireContext())
                graph.incKmapVariable.kmapVariable.text(getString(R.string.kmap_variables, KMAP_VARIABLE))
                graph.incKmapType.kmapType.text(getString(R.string.kmap_type))
                graph.incKmapTiles.apply {
                    kmapVariableIdentifier.text(getString(R.string.sample_kmap_variable_identifier))
                    viewModel?.apply {
                        requireActivity().apply {
                            state00.observeCell(this, tvKmapOption00)
                            state01.observeCell(this, tvKmapOption01)
                            state11.observeCell(this, tvKmapOption11)
                            state10.observeCell(this, tvKmapOption10)
                        }

                        disposables.addAll(tvKmapOption00.clickEvent().subscribe { setState00() },
                            tvKmapOption01.clickEvent().subscribe { setState01() },
                            tvKmapOption11.clickEvent().subscribe { setState11() },
                            tvKmapOption10.clickEvent().subscribe { setState10() })
                    }
                }
            }
        }
    }

    private fun LiveData<KmapState>.observeCell(fa: FragmentActivity, tv: TextView) = context.run {
        observe(fa) {
            tv.text = when (it) {
                is KmapState.StateOn -> getString(it.value).apply { valueStateOn?.invoke(this) }
                is KmapState.StateOff -> null
            }
        }
    }

    private fun LayoutKmap2GraphicBinding.setAnswer(c: Context) = incKmapAnswer.apply {
        fun TextView.ref() = observeTextView()
        fun observes(vararg tvs: TextView) = tvs.map(TextView::ref)
        fun disposes(vararg observables: Observable<String>) =
            disposables.addAll(*observables.map { it.subscribe() }.toTypedArray())

        valueStateOn = { so ->
            incKmapTiles.apply {
                val (cell00, cell01, cell11, cell10) = observes(
                    tvKmapOption00,
                    tvKmapOption01,
                    tvKmapOption11,
                    tvKmapOption10
                )

                disposes(cell00, cell01, cell11, cell10)
                disposables.add(Observable.combineLatest(
                    cell00, cell01, cell11, cell10,
                ) { p00: String, p01: String, p11: String, p10: String ->
                    when {
                        so.notContainedIn(p00, p01, p11, p10) -> c.intRes(R.integer.low)
                        so.eachIsNotEmpty(p00) { adds(p01, p11, p10) } -> "!A.!B"
                        so.eachIsNotEmpty(p01) { adds(p00, p11, p10) } -> "!A.B"
                        so.eachIsNotEmpty(p11) { adds(p00, p01, p10) } -> "A.B"
                        so.eachIsNotEmpty(p10) { adds(p00, p01, p11) } -> "A.!B"
                        so.eachIsNotEmpty(p00, p01) { adds(p11, p10) } -> "!A"
                        so.eachIsNotEmpty(p00, p11) { adds(p01, p10) } -> "(!A.!B) + (A.B)"
                        so.eachIsNotEmpty(p00, p10) { adds(p01, p11) } -> "!B"
                        so.eachIsNotEmpty(p11, p10) { adds(p00, p01) } -> "A"
                        so.eachIsNotEmpty(p01, p11) { adds(p00, p10) } -> "B"
                        so.eachIsNotEmpty(p01, p10) { adds(p00, p11) } -> "(!A.B) + (A.!B)"
                        so.eachIsNotEmpty(p00, p01, p11) { adds(p10) } -> "(!A) + (B)"
                        so.eachIsNotEmpty(p00, p11, p10) { adds(p01) } -> "(!B) + (A)"
                        so.eachIsNotEmpty(p00, p01, p10) { adds(p11) } -> "(!A) + (!B)"
                        so.eachIsNotEmpty(p01, p11, p10) { adds(p00) } -> "(B) + (A)"
                        eachIsNotEmpty(p00, p01, p11, p10) -> c.intRes(R.integer.high)
                        else -> c.stringRes(R.string.kmap_answer_not_defined)
                    }.toString()
                }.subscribe { kmapAnswer.text = it })
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        ::_binding.set(null)
    }

    companion object {
        private const val IS_ATTACH = false
        private const val KMAP_VARIABLE = 2
    }
}
