package com.dash.projects.android.digiwave.`object`.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.enum.ViewVisibility
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*

object Utils {
    private fun Context.listIntRes(@IntegerRes vararg id: Int) = id.map {
        resources.getInteger(it)
    }

    fun stringResListOf(@StringRes vararg id: Int) = id.toList()

    fun drawableResListOf(@DrawableRes vararg id: Int) = id.toList()

    fun Context.intRes(@IntegerRes id: Int) = resources.getInteger(id)

    fun Context.stringRes(@StringRes id: Int) = resources.getString(id)

    fun Context.drawableRes(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

    fun View.callToast(@StringRes resId: Int, short: Boolean = true) = Toast.makeText(
        context, resId, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    fun View.callToast(text: String, short: Boolean = true) = Toast.makeText(
        context, text, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()

    fun <T> T.isNotNull() = this != null

    fun setVisibility(visibility: ViewVisibility, vararg views: View) = views.map {
        it.visibility = when (visibility) {
            ViewVisibility.VISIBLE -> View.VISIBLE
            ViewVisibility.INVISIBLE -> View.INVISIBLE
            ViewVisibility.GONE -> View.GONE
        }
    }

    fun <T : EditText> T.observeInput() = RxTextView.textChanges(this)
        .skipInitialValue()
        .map(CharSequence::toString)
        .distinctUntilChanged()
        .observeOn(AndroidSchedulers.mainThread())
        .publish()
        .refCount()

    fun clearText(vararg et: EditText) = et.forEach {
        it.text = null
    }

    val Context.bcdForbiddenDigit: List<Int>
        get() = listIntRes(
            R.integer.decRadix, R.integer.num11, R.integer.slab,
            R.integer.num13, R.integer.num14, R.integer.parcel,
        )

    val Context.xs3ForbiddenDigit: List<Int>
        get() = listIntRes(
            R.integer.low, R.integer.high, R.integer.bitRadix,
            R.integer.num13, R.integer.num14, R.integer.parcel,
        )

    fun Collection<Int>.elementToString(radix: Int? = null) = map {
        radix?.let { rad -> it.toString(rad) } ?: run { it.toString() }
    }

    fun Collection<Int>.elementToString(
        radix: Int? = null,
        asNybble: Boolean,
        padStart: Boolean = true
    ) = elementToString(radix).map {
        Pair(4, '0').let { p ->
            if (!asNybble) it
            else {
                if (padStart) it.padStart(p.first, p.second)
                else it.padEnd(p.first, p.second)
            }
        }
    }

    fun <T : CharSequence> T.asNybble(padStart: Boolean) = Pair(4, '0').let { p ->
        if (padStart) padStart(p.first, p.second)
        else padEnd(p.first, p.second)
    }

    fun <T> List<T>.fon(with: List<T>) = asSequence().firstOrNull {
        it in with
    }

    fun <T : CharSequence> T.toDecimal(base: Int) = fold(0L) { acc, char ->
        (acc * base) + char.digitToInt(base)
    }

    fun <T : CharSequence> T.toGray(base: Int) = toDecimal(base).let {
        it.xor(it.shr(1)).toString(base)
    }

    fun Long.splitDigit() = toString().map(Char::digitToIntOrNull)
        .filterNotNull()

    fun <T : EditText, R> T.setTextOnlyIfChanged(newText: R) {
        if (Objects.equals(text.toString(), newText.toString())) return
        this.setText(newText.toString())
    }

    private fun <T : CharSequence> T.dropLeadingZero() = dropWhile { it.digitToInt() == 0 }

    fun <T : EditText, R> T.setTextOnlyIfChangedInteger(newText: R) {
        if (Objects.equals(
                text.toString().dropLeadingZero(),
                newText.toString().dropLeadingZero()
            )
        ) return

        this.setText(newText.toString())
    }

    fun <T> Iterable<T>.merge(separator: String = "") = joinToString(separator)
}
