package com.dash.projects.android.digiwave.views.features.numsys.branch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.databinding.FragmentNumberSystemCategoryBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class NumberSystemCategoryFragment : Fragment() {
    private val disposables = CompositeDisposable()
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
        activity?.run {
            binding?.let { bind ->
                bind.incNumsysInput.apply {
                    val bitRadix = intRes(R.integer.bitRadix)
                    val decRadix = intRes(R.integer.decRadix)
                    val octRadix = intRes(R.integer.byteRadix)
                    val hexRadix = intRes(R.integer.hexRadix)
                    disposables.addAll(
                        etBin.observe().subscribe(onNext(bitRadix, bind), ::onError),
                        etDec.observe().subscribe(onNext(decRadix, bind), ::onError),
                        etOct.observe().subscribe(onNext(octRadix, bind), ::onError),
                        etHex.observe().subscribe(onNext(hexRadix, bind), ::onError),
                    )
                }
            }
        }
    }

    private fun <T : EditText> T.observe() = RxTextView.textChanges(this)
        .skipInitialValue()
        .map(CharSequence::toString)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .publish()
        .refCount()

    private fun onNext(radix: Int, bind: FragmentNumberSystemCategoryBinding): (String) -> Unit =
        { t: String ->
            bind.incNumsysInput.apply {
                try {
                    when (radix) {
                        2 -> {
                            if (t.isEmpty()) clearText(etlBin, etOct, etDec, etHex)
                            else {
                                val decResult = t.fold(0L) { acc, char ->
                                    (acc * radix) + char.digitToInt(radix)
                                }
                                val octResult = decResult.toString(8)
                                val hexResult = decResult.toString(16)

                                etlBin.enableError(false)
                                etDec.setText(decResult.toString(), true)
                                etHex.setText(hexResult.uppercase(), true)
                                etOct.setText(octResult, true)
                            }
                        }
                        10 -> {
                            if (t.isEmpty()) clearText(etlDec, etBin, etOct, etHex)
                            else {
                                val binResult = t.toString(2)
                                val octResult = t.toString(8)
                                val hexResult = t.toString(16).uppercase()

                                etlDec.enableError(false)
                                etBin.setText(binResult, true)
                                etOct.setText(octResult, true)
                                etHex.setText(hexResult, true)
                            }
                        }
                        8 -> {
                            if (t.isEmpty()) clearText(etlOct, etBin, etDec, etHex)
                            else {
                                val decResult = t.fold(0L) { acc, char ->
                                    (acc * radix) + char.digitToInt(radix)
                                }
                                val binResult = decResult.toString(2)
                                val hexResult = decResult.toString(16)

                                etlOct.enableError(false)
                                etDec.setText(decResult.toString(), true)
                                etHex.setText(hexResult.uppercase(), true)
                                etBin.setText(binResult, true)
                            }
                        }
                        16 -> {
                            if (t.isEmpty()) clearText(etlHex, etBin, etDec, etOct)
                            else {
                                val decResult = t.fold(0L) { acc, char ->
                                    (acc * radix) + char.digitToInt(radix)
                                }
                                val binResult = decResult.toString(2)
                                val octResult = decResult.toString(8)

                                etlHex.enableError(false)
                                etDec.setText(decResult.toString(), true)
                                etBin.setText(binResult, true)
                                etOct.setText(octResult, true)
                            }
                        }
                    }
                } catch (e: NumberFormatException) {
                    Log.e("$ON_NEXT_TAG throw ${NumberFormatException()}", e.message.toString())
                    val errorMessage = getString(R.string.err_exceeding_limit)
                    switchErrorHandler(radix, bind, errorMessage)
                } catch (e: IllegalArgumentException) {
                    Log.e("$ON_NEXT_TAG throw ${IllegalArgumentException()}", e.message.toString())
                    switchErrorHandler(radix, bind)
                }
            }
        }

    private fun onError(e: Throwable) {
        Log.e(ON_ERROR_TAG, e.message.toString())
    }

    private fun String.toString(radix: Int) =
        toLong().toString(radix)

    private fun switchErrorHandler(
        radix: Int,
        binding: FragmentNumberSystemCategoryBinding,
        msg: String? = null,
    ) = binding.incNumsysInput.apply {
        val errorFormat: String
        when (radix) {
            2 -> {
                errorFormat = getString(R.string.et_error_format, 0, 1)
                etlBin.enableError(true, msg ?: errorFormat)
            }
            10 -> {
                errorFormat = getString(R.string.et_error_format, 0, 9)
                etlDec.enableError(true, msg ?: errorFormat)
            }
            8 -> {
                errorFormat = getString(R.string.et_error_format, 0, 7)
                etlOct.enableError(true, msg ?: errorFormat)
            }
            16 -> {
                errorFormat = getString(R.string.et_error_format_with_symbol, 0, 9, "A", "F")
                etlHex.enableError(true, msg ?: errorFormat)
            }
        }
    }

    private fun TextInputLayout.enableError(isEnabled: Boolean, errorText: String? = null) {
        if (isEnabled) {
            isErrorEnabled = true
            error = errorText
        } else {
            error = null
            isErrorEnabled = false
        }
    }

    private fun TextInputEditText.setText(text: CharSequence, onlyIfChanged: Boolean) {
        if (onlyIfChanged) {
            if (Objects.equals(this.text.toString(), text.toString())) {
                return
            }
        }
        this.setText(text)
    }

    private fun clearText(inpLayout: TextInputLayout, vararg editText: EditText) {
        inpLayout.enableError(false)
        editText.forEach { it.text = null }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        ::_binding.set(null)
    }

    companion object {
        private const val IS_ATTACH = false
        private const val ON_NEXT_TAG = "onNext"
        private const val ON_ERROR_TAG = "onError"
    }
}
