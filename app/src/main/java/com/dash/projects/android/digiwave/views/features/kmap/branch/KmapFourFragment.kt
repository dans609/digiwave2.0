package com.dash.projects.android.digiwave.views.features.kmap.branch

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IntegerRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.clickEvent
import com.dash.projects.android.digiwave.`object`.utils.Utils.combineLatest
import com.dash.projects.android.digiwave.`object`.utils.Utils.disposeAll
import com.dash.projects.android.digiwave.`object`.utils.Utils.observeTextView
import com.dash.projects.android.digiwave.`object`.utils.Utils.str
import com.dash.projects.android.digiwave.`object`.utils.Utils.strIntRes
import com.dash.projects.android.digiwave.databinding.FragmentKmapFourBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap4GraphicBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmap4TilesBinding
import com.dash.projects.android.digiwave.databinding.LayoutKmapRowTilesBinding
import com.dash.projects.android.digiwave.sealed.BinaryState
import com.dash.projects.android.digiwave.views.features.kmap.branch.impl.Kmap4AnswerImpl.answer
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapFourViewModel
import com.dash.projects.android.digiwave.factory.viewmodel.ViewModelFactory
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables

class KmapFourFragment : Fragment() {
    private val disposables = CompositeDisposable()
    private var valueStateOn: ((String) -> Unit)? = null

    private var _binding: FragmentKmapFourBinding? = null
    private val binding
        get() = _binding

    private var _viewModel: KmapFourViewModel? = null
    private val viewModel
        get() = _viewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentKmapFourBinding.inflate(inflater, container, IS_ATTACH).run {
        _binding = this
        _viewModel = getViewModel(this@KmapFourFragment, KmapFourViewModel::class.java)
        binding?.root
    }

    private fun <T : ViewModel> getViewModel(owner: ViewModelStoreOwner, mClass: Class<T>) =
        ViewModelProvider(owner, ViewModelFactory.getInstance())[mClass]

    // noted: context no longer used
    private fun LiveData<BinaryState>.setTextState(fa: FragmentActivity, tv: TextView) =
        fa.applicationContext.run {
            observe(fa) {
                tv.text = when (it) {
                    is BinaryState.StateOn -> str(it.value).apply { valueStateOn?.invoke(this) }
                    is BinaryState.StateOff -> null
                }
            }
        }

    // noted: disposables is no longer used
    private fun <T : LiveData<BinaryState>> LayoutKmapRowTilesBinding.observeCell(
        fa: FragmentActivity, p2: T, p3: T, p4: T, p5: T
    ) = disposables.apply {
        p2.setTextState(fa, tvKmapOption00)
        p3.setTextState(fa, tvKmapOption01)
        p4.setTextState(fa, tvKmapOption11)
        p5.setTextState(fa, tvKmapOption10)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val variable = getString(R.string.kmap_variables, KMAP_VARIABLE)
        val context = requireContext()

        activity?.let { fa ->
            binding?.incKmapGraphic?.let { graph ->
                graph.setAnswer(context)
                graph.incKmapVariable.kmapVariable.text = variable
                graph.incKmapType.kmapType.text = getString(R.string.kmap_type)
                graph.incKmapTiles.apply {
                    setIdentifier(context)
                    viewModel?.apply {
                        incRow1.apply {
                            observeCell(fa, state0000(), state0001(), state0011(), state0010())
                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { state0000(SET) },
                                tvKmapOption01.clickEvent().subscribe { state0001(SET) },
                                tvKmapOption11.clickEvent().subscribe { state0011(SET) },
                                tvKmapOption10.clickEvent().subscribe { state0010(SET) },
                            )
                        }

                        incRow2.apply {
                            observeCell(fa, state0100(), state0101(), state0111(), state0110())
                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { state0100(SET) },
                                tvKmapOption01.clickEvent().subscribe { state0101(SET) },
                                tvKmapOption11.clickEvent().subscribe { state0111(SET) },
                                tvKmapOption10.clickEvent().subscribe { state0110(SET) },
                            )
                        }

                        incRow3.apply {
                            observeCell(fa, state1100(), state1101(), state1111(), state1110())
                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { state1100(SET) },
                                tvKmapOption01.clickEvent().subscribe { state1101(SET) },
                                tvKmapOption11.clickEvent().subscribe { state1111(SET) },
                                tvKmapOption10.clickEvent().subscribe { state1110(SET) },
                            )
                        }

                        incRow4.apply {
                            observeCell(fa, state1000(), state1001(), state1011(), state1010())
                            disposables.addAll(
                                tvKmapOption00.clickEvent().subscribe { state1000(SET) },
                                tvKmapOption01.clickEvent().subscribe { state1001(SET) },
                                tvKmapOption11.clickEvent().subscribe { state1011(SET) },
                                tvKmapOption10.clickEvent().subscribe { state1010(SET) },
                            )
                        }
                    }
                }
            }
        }
    }

    private fun LayoutKmap4TilesBinding.setIdentifier(context: Context) = context.apply {
        kmapVariableIdentifier1.text = getString(R.string.sample_kmap_2variable_cd)
        kmapVariableIdentifier2.text = getString(R.string.sample_kmap_variable_identifier)
        val int11 = R.integer.fifthPrimeNum
        val int13 = R.integer.archimedianSolid
        fun LayoutKmapRowTilesBinding.setValue(
            @IntegerRes p1: Int,
            @IntegerRes p2: Int,
            @IntegerRes p3: Int,
            @IntegerRes p4: Int
        ) {
            identifier00.text = strIntRes(p1)
            identifier01.text = strIntRes(p2)
            identifier11.text = strIntRes(p3)
            identifier10.text = strIntRes(p4)
        }

        incRow1.setValue(R.integer.low, R.integer.high, R.integer.triad, R.integer.bitRadix)
        incRow2.setValue(R.integer.nibble, R.integer.pentad, R.integer.heptad, R.integer.hexad)
        incRow3.setValue(R.integer.slab, int13, R.integer.parcel, R.integer.tetraRadix)
        incRow4.setValue(R.integer.byteRadix, R.integer.nonet, int11, R.integer.decRadix)
    }

    private fun LayoutKmap4GraphicBinding.setAnswer(c: Context) = incKmapAnswer.apply {
        fun TextView.ref() = observeTextView()
        fun observeAll(vararg tv: TextView) = tv.map(TextView::ref)
        fun LayoutKmapRowTilesBinding.onObserve() =
            observeAll(tvKmapOption00, tvKmapOption01, tvKmapOption11, tvKmapOption10).apply {
                disposables.disposeAll(*toTypedArray())
            }

        valueStateOn = { stateOn ->
            val (cell0000, cell0001, cell0011, cell0010) = incKmapTiles.incRow1.onObserve()
            val (cell0100, cell0101, cell0111, cell0110) = incKmapTiles.incRow2.onObserve()
            val (cell1100, cell1101, cell1111, cell1110) = incKmapTiles.incRow3.onObserve()
            val (cell1000, cell1001, cell1011, cell1010) = incKmapTiles.incRow4.onObserve()

            disposables.add(Observables.combineLatest(
                cell0000, cell0001, cell0011, cell0010, cell0100, cell0101, cell0111, cell0110,
                cell1100, cell1101, cell1111, cell1110, cell1000, cell1001, cell1011, cell1010,
                answer(c, stateOn)
            ).subscribe { kmapAnswer.text = it })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
        ::_viewModel.set(null)
    }

    companion object {
        private const val IS_ATTACH = false
        private const val SET = true
        private const val KMAP_VARIABLE = 4
    }
}
