package com.dash.projects.android.digiwave.views.features.numsys.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.databinding.FragmentDialogFormulationBinding
import com.jakewharton.rxbinding2.widget.RxRadioGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

class NumberSystemDialog : DialogFragment() {
    private var _binding: FragmentDialogFormulationBinding? = null
    private val binding
        get() = _binding

    private val compositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentDialogFormulationBinding.inflate(inflater, container, false).let {
        _binding = it
        binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments != null) {
            val binText = arguments?.getString(BIN_TEXT)
            val octText = arguments?.getString(OCT_TEXT)
            val hexText = arguments?.getString(HEX_TEXT)

            binding?.apply {
                val observable = RxRadioGroup.checkedChanges(rgConversionType)
                    .observeOn(AndroidSchedulers.mainThread())
                    .publish()
                    .refCount()

                compositeDisposable.add(observable.subscribe {
                    when (it) {
                        R.id.rb_bin_dec -> {
                            val radix = 2
                            tvSelectedNumsysTitle.setText(R.string.bin_formulation_title)
                            tvSelectedNumsysDigit.text = takesOnlyDigits(binText, radix)

                            val selectedText = tvSelectedNumsysDigit.text.toString()
                            tvTargetNumsysDigit.text = convertNumsysToDec(selectedText, radix)
                        }
                        R.id.rb_oct_dec -> {
                            val radix = 8
                            tvSelectedNumsysTitle.setText(R.string.oct_formulation_title)
                            tvSelectedNumsysDigit.text = takesOnlyDigits(octText, radix)

                            val selectedText = tvSelectedNumsysDigit.text.toString()
                            tvTargetNumsysDigit.text = convertNumsysToDec(selectedText, radix)
                        }
                        R.id.rb_hex_dec -> {
                            val radix = 16
                            tvSelectedNumsysTitle.setText(R.string.hex_formulation_title)
                            tvSelectedNumsysDigit.text = takesOnlyDigits(hexText, radix)

                            val selectedText = tvSelectedNumsysDigit.text.toString()
                            tvTargetNumsysDigit.text = convertNumsysToDec(selectedText, radix)
                        }
                        else -> {
                            tvSelectedNumsysDigit.setText(R.string.radio_is_not_selected)
                            tvTargetNumsysDigit.setText(R.string.radio_is_not_selected)
                        }
                    }
                })
            }
        }
    }

    private fun takesOnlyDigits(numberSystemDigit: String?, radix: Int): String? {
        return numberSystemDigit?.takeWhile {
            it.digitToIntOrNull(radix) != null
        }
    }

    private fun convertNumsysToDec(numberSystemDigit: String?, radix: Int): String {
        return StringBuilder().apply {
            numberSystemDigit?.forEachIndexed { index, c ->
                val newDigit = c.digitToIntOrNull(radix)
                val power = numberSystemDigit.lastIndex - index

                if (newDigit != null) {
                    append(
                        when (index) {
                            numberSystemDigit.lastIndex -> "($newDigit . $radix^$power)"
                            0 -> "($newDigit . $radix^$power) + "
                            else -> "($newDigit . $radix^$power) + "
                        }
                    )
                }
            }
        }.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.dispose()
    }

    companion object {
        const val BIN_TEXT = "extra_binary_text"
        const val OCT_TEXT = "extra_octal_text"
        const val HEX_TEXT = "extra_hexadecimal_text"
    }
}
