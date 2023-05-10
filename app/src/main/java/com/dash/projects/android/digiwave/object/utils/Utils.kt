package com.dash.projects.android.digiwave.`object`.utils

import android.content.Context
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.enum.ViewVisibility
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.Observables
import java.util.*
import kotlin.collections.ArrayList

object Utils {
    private fun Context.listIntRes(@IntegerRes vararg id: Int) = id.map {
        resources.getInteger(it)
    }

    fun <T : Any> str(obj: T) = obj.toString()

    fun Int.castToBoolean() = this >= 1

    fun stringResListOf(@StringRes vararg id: Int) = id.toList()

    fun drawableResListOf(@DrawableRes vararg id: Int) = id.toList()

    fun Context.intRes(@IntegerRes id: Int) = resources.getInteger(id)

    fun Context.strIntRes(@IntegerRes id: Int) = intRes(id).toString()

    fun Context.stringRes(@StringRes id: Int) = resources.getString(id)

    fun Context.arrayRes(@ArrayRes id: Int): Array<String> = resources.getStringArray(id)

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

    fun <T : View> T.clickEvent() = RxView.clicks(this)
        .observeOn(AndroidSchedulers.mainThread())
        .publish()
        .refCount()

    fun <T : TextView> T.observeTextView() = RxTextView.textChanges(this)
        .observeOn(AndroidSchedulers.mainThread())
        .map(CharSequence::toString)
        .publish()
        .refCount()

    fun <T : TextView> T.observeTv() = RxTextView.textChanges(this)
        .observeOn(AndroidSchedulers.mainThread())
        .publish()
        .refCount()

    fun clearText(vararg et: EditText) = et.forEach {
        it.text = null
    }

    val Context.bcdForbiddenDigit: List<Int>
        get() = listIntRes(
            R.integer.decRadix, R.integer.fifthPrimeNum, R.integer.slab,
            R.integer.archimedianSolid, R.integer.tetraRadix, R.integer.parcel,
        )

    val Context.xs3ForbiddenDigit: List<Int>
        get() = listIntRes(
            R.integer.low, R.integer.high, R.integer.bitRadix,
            R.integer.archimedianSolid, R.integer.tetraRadix, R.integer.parcel,
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
        it.xor(it.shr(1)).toString(2)
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

    fun <T : CharSequence> eachIsNotEmpty(vararg t: T) = !t.any(CharSequence::isEmpty)

    fun <T : CharSequence> T.notContainedIn(vararg arr: T) = !arr.contains(this)

    private fun <T : CharSequence> T.notContainedIn(arr: Collection<T>) = !arr.contains(this)

    fun <T : CharSequence> T.eachIsNotEmpty(
        vararg t: T,
        onSeedContainedIn: ArrayList<T>.() -> ArrayList<T>
    ) = eachIsNotEmpty(*t) && notContainedIn(ArrayList<T>().onSeedContainedIn())

    fun <T> ArrayList<T>.adds(vararg data: T) = apply { addAll(data) }

    fun CompositeDisposable.disposeAll(vararg observables: Observable<String>) =
        addAll(*observables.map { it.subscribe() }.toTypedArray())

    @Suppress("UNCHECKED_CAST", "unused")
    inline fun <T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, R> Observables.combineLatest(
        source1: Observable<T1>, source2: Observable<T2>,
        source3: Observable<T3>, source4: Observable<T4>,
        source5: Observable<T5>, source6: Observable<T6>,
        source7: Observable<T7>, source8: Observable<T8>,
        source9: Observable<T9>, source10: Observable<T10>,
        source11: Observable<T11>, source12: Observable<T12>,
        source13: Observable<T13>, source14: Observable<T14>,
        source15: Observable<T15>, source16: Observable<T16>,
        crossinline combineFunction: (T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16) -> R
    ): Observable<R> = Observable.combineLatest(
        arrayOf(
            source1, source2, source3, source4, source5, source6, source7, source8,
            source9, source10, source11, source12, source13, source14, source15, source16
        )
    ) {
        combineFunction(
            it[0] as T1, it[1] as T2, it[2] as T3, it[3] as T4, it[4] as T5,
            it[5] as T6, it[6] as T7, it[7] as T8, it[8] as T9, it[9] as T10,
            it[10] as T11, it[11] as T12, it[12] as T13, it[13] as T14, it[14] as T15,
            it[15] as T16,
        )
    }

    fun and(a: Int, b: Int) = if (a >= 1 && b >= 1) 1 else 0

    fun or(a: Int, b: Int) = if (a >= 1 || b >= 1) 1 else 0

    fun xor(a: Int, b: Int) = (if (a >= 1) 1 else 0).xor(if (b >= 1) 1 else 0)

    fun inverse(a: Int) = if (a >= 1) 0 else 1

    fun nand(a: Int, b: Int) = inverse(and(a, b))

    fun nor(a: Int, b: Int) = inverse(or(a, b))

    fun xnor(a: Int, b: Int) = inverse(xor(a, b))


    /**
     * [toi] are slang of "Text to Int".
     *
     * Will throw exception if condition doesn't meet (only casting numerical string)
     */
    fun TextView.toi() = text.toString().toInt()
}
