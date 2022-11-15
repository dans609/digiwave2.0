package com.dash.projects.android.digiwave.views.features.numsys.branch

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.asNybble
import com.dash.projects.android.digiwave.`object`.utils.Utils.bcdForbiddenDigit
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.`object`.utils.Utils.clearText
import com.dash.projects.android.digiwave.`object`.utils.Utils.elementToString
import com.dash.projects.android.digiwave.`object`.utils.Utils.fon
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.isNotNull
import com.dash.projects.android.digiwave.`object`.utils.Utils.merge
import com.dash.projects.android.digiwave.`object`.utils.Utils.observeInput
import com.dash.projects.android.digiwave.`object`.utils.Utils.setTextOnlyIfChanged
import com.dash.projects.android.digiwave.`object`.utils.Utils.setTextOnlyIfChangedInteger
import com.dash.projects.android.digiwave.`object`.utils.Utils.setVisibility
import com.dash.projects.android.digiwave.`object`.utils.Utils.splitDigit
import com.dash.projects.android.digiwave.`object`.utils.Utils.toDecimal
import com.dash.projects.android.digiwave.`object`.utils.Utils.toGray
import com.dash.projects.android.digiwave.`object`.utils.Utils.xs3ForbiddenDigit
import com.dash.projects.android.digiwave.databinding.FragmentCodeSystemCategoryBinding
import com.dash.projects.android.digiwave.databinding.LayoutTextInputCodesysBinding
import com.dash.projects.android.digiwave.enum.ViewVisibility
import com.dash.projects.android.digiwave.views.features.numsys.NumberSystemActivity.Companion.ON_ERROR_TAG
import com.dash.projects.android.digiwave.views.features.numsys.NumberSystemActivity.Companion.ON_NEXT_TAG
import com.google.android.material.textfield.TextInputLayout
import io.reactivex.disposables.CompositeDisposable

class CodeSystemCategoryFragment : Fragment() {
    private val disposables = CompositeDisposable()

    private val _bcdForbiddenDigit = ArrayList<String>()
    private val _xs3ForbiddenDigit = ArrayList<String>()
    private val bcdForbiddenDigit: List<String> get() = _bcdForbiddenDigit
    private val xs3ForbiddenDigit: List<String> get() = _xs3ForbiddenDigit

    private var _modes: Array<String>? = null
    private val modes
        get() = _modes

    private var _binding: FragmentCodeSystemCategoryBinding? = null
    private val binding
        get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = FragmentCodeSystemCategoryBinding.inflate(inflater, container, IS_ATTACH).let {
        ::_binding.set(it)
        ::_modes.set(resources.getStringArray(R.array.code_system_mode))
        binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.apply {
            val base = resources.getInteger(R.integer.bitRadix)
            requireContext().also {
                _bcdForbiddenDigit.addAll(it.bcdForbiddenDigit.elementToString(base, true))
                _xs3ForbiddenDigit.addAll(it.xs3ForbiddenDigit.elementToString(base, true))
            }

            incCodesysInput.run {
                disposables.addAll(
                    etBin.observeInput().subscribe(onNext(BIN_FLAG, base), ::onError),
                    etBcd.observeInput().subscribe(onNext(BCD_FLAG, base), ::onError),
                    etXs3.observeInput().subscribe(onNext(XS3_FLAG, base), ::onError),
                    etGray.observeInput().subscribe(onNext(GRAY_FLAG, base), ::onError),
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        binding?.showDropdownItem(
            requireContext(),
            R.layout.layout_dropdown_item,
            modes?.toList() ?: emptyList()
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        ::_modes.set(null)
        ::_binding.set(null)
    }

    private fun <T> FragmentCodeSystemCategoryBinding.showDropdownItem(
        context: Context,
        @LayoutRes layoutId: Int,
        items: List<T>
    ) = ArrayAdapter(context, layoutId, items).also {
        fun visibilityModifier(visibleView: View, vararg invisibleView: View) {
            setVisibility(ViewVisibility.INVISIBLE, *invisibleView)
            setVisibility(ViewVisibility.VISIBLE, visibleView)
        }

        incCodesysInput.apply {
            autoCompleteTvDropdown.setAdapter(it)
            autoCompleteTvDropdown.setOnItemClickListener { av, v, i, _ ->
                v.callToast(av.getItemAtPosition(i).toString())
                clearText(etBin, etBcd, etXs3, etGray)
                with(context) {
                    when (i) {
                        intRes(R.integer.low) -> visibilityModifier(etlBcd, etlXs3, etlGray)
                        intRes(R.integer.high) -> visibilityModifier(etlXs3, etlBcd, etlGray)
                        intRes(R.integer.bitRadix) -> visibilityModifier(etlGray, etlBcd, etlXs3)
                    }
                }
            }
        }
    }

    private fun FragmentCodeSystemCategoryBinding.onNext(flag: String, base: Int) = { t: String ->
        incCodesysInput.run {
            try {
                when (flag) {
                    BIN_FLAG -> binConversion(t, base)
                    BCD_FLAG -> bcdConversion(t, base)
                    XS3_FLAG -> xs3Conversion(t, base)
                    GRAY_FLAG -> grayConversion(t, base)
                }
            } catch (e: NumberFormatException) {
                Log.e("$ON_NEXT_TAG throw ${NumberFormatException()}", e.message.toString())
                inputErrorHandler(flag, getString(R.string.err_exceeding_limit), base)
            } catch (e: IllegalArgumentException) {
                Log.e("$ON_NEXT_TAG throw ${IllegalArgumentException()}", e.message.toString())
                val baseError = getString(R.string.error_radix_namespace, base)
                val msg = e.message?.let { if (it.contains(baseError)) null else it }
                inputErrorHandler(flag, msg, base)
            }
        }
    }

    private fun onError(e: Throwable) {
        Log.e(ON_ERROR_TAG, e.message.toString())
    }

    private fun FragmentCodeSystemCategoryBinding.inputErrorHandler(
        flag: String,
        msg: String? = null,
        base: Int,
    ): Unit = incCodesysInput.run {
        val bitError = getString(R.string.et_error_format, 0, base - 1)
        Pair(true, (msg ?: bitError)).let {
            when (flag) {
                BIN_FLAG -> etlBin.enableError(it.first, it.second)
                BCD_FLAG -> etlBcd.enableError(it.first, it.second)
                XS3_FLAG -> etlXs3.enableError(it.first, it.second)
                GRAY_FLAG -> etlGray.enableError(it.first, it.second)
            }
        }
    }

    private fun TextInputLayout.enableError(
        enable: Boolean,
        errorText: String? = null,
    ) = if (enable) {
        isErrorEnabled = true
        error = errorText
    } else {
        error = null
        isErrorEnabled = false
    }

    private fun LayoutTextInputCodesysBinding.binConversion(digit: String, radix: Int) {
        etlBin.enableError(false)
        if (digit.isNotEmpty()) {
            fun Int.plusThree() = this + 3L
            fun Char.isZero() = digitToInt(radix) == 0
            fun decSplit() = digit.toDecimal(radix).splitDigit()
            fun Long.toBinary() = toString(radix).padStart(4, '0')
            fun <T : EditText> Collection<Int>.paste(to: T) = to.setTextOnlyIfChanged(
                elementToString(radix, true).merge().dropWhile(Char::isZero)
            )

            when {
                etlBcd.isVisible -> decSplit().paste(etBcd)
                etlXs3.isVisible -> {
                    etXs3.setTextOnlyIfChangedInteger(
                        decSplit().map(Int::plusThree)
                            .joinToString("", transform = Long::toBinary)
                    )
                }
                etlGray.isVisible -> etGray.setTextOnlyIfChangedInteger(digit.toGray(radix))
            }
        } else clearText(etBcd, etXs3, etGray)
    }

    private fun LayoutTextInputCodesysBinding.xs3Conversion(digit: String, radix: Int) {
        fun Long.minusThree() = minus(3)
        etlXs3.enableError(false)

        if (digit.isNotEmpty() && etlXs3.isVisible) {
            val zerosToAdd = (4 - digit.length % 4).takeIf { it != 4 } ?: 0
            val groupOfNybbles = "0".repeat(zerosToAdd) + digit
            val result = groupOfNybbles.chunked(4) {
                if (it.toString() in xs3ForbiddenDigit) {
                    val xs3 = getString(R.string.hint_xs3_keyword)
                    val msg = getString(R.string.nybble_error, xs3, it)
                    throw IllegalArgumentException(msg)
                }

                it.toString().toDecimal(radix)
            }.map(Long::minusThree).joinToString("")

            etBin.setTextOnlyIfChangedInteger(result.toLong().toString(radix))
        } else clearText(etBin, etBcd, etGray)
    }

    private fun String.chunkAsNybble(radix: Int, chunkSize: Int, padStart: Boolean): List<String> {
        fun Char.validateDigit() = digitToInt(radix)
        fun <T : CharSequence> T.leastAndReversed() =
            asNybble(padStart).reversed().toString()

        return filter(Char::isDigit)
            .onEach(Char::validateDigit)
            .reversed()
            .chunked(chunkSize, CharSequence::leastAndReversed)
            .reversed()
    }

    private fun LayoutTextInputCodesysBinding.bcdConversion(digit: String, radix: Int) {
        etlBcd.enableError(false)
        if (digit.any(Char::isDigit) && etlBcd.isVisible) {
            val nybbles = digit.chunkAsNybble(radix, 4, false)
            nybbles.fon(bcdForbiddenDigit).also {
                if (it.isNotNull()) {
                    val bcd = getString(R.string.hint_bcd_keyword)
                    val msg = getString(R.string.nybble_error, bcd, it)
                    throw IllegalArgumentException(msg)
                }

                etBin.setTextOnlyIfChanged(nybbles.map { chk -> chk.toDecimal(radix) }
                    .merge()
                    .toLong()
                    .toString(radix))
            }
        } else clearText(etBin, etXs3, etGray)
    }

    private fun LayoutTextInputCodesysBinding.grayConversion(digit: String, radix: Int) {
        fun Long.toDecimalGray() = let {
            var (num, res) = it to it
            while (num > 0) {
                num = num shr 1
                res = res xor num
            }

            res
        }

        etlGray.enableError(false)
        if (digit.any(Char::isDigit) && etlGray.isVisible) etBin.setTextOnlyIfChangedInteger(
            digit.toDecimal(radix).toDecimalGray().toString(radix)
        )
        else clearText(etBin, etBcd, etXs3)
    }

    companion object {
        private const val IS_ATTACH = false
        private const val BIN_FLAG = "bin_flag"
        private const val BCD_FLAG = "bcd_flag"
        private const val XS3_FLAG = "xs3_flag"
        private const val GRAY_FLAG = "gray_flag"
    }
}
