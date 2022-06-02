package com.dash.projects.android.digiwave.`object`.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat

object Utils {
    fun stringResListOf(@StringRes vararg id: Int) = id.toList()

    fun drawableResListOf(@DrawableRes vararg id: Int) = id.toList()

    fun Context.intRes(@IntegerRes id: Int) = resources.getInteger(id)

    fun Context.stringRes(@StringRes id: Int) = resources.getString(id)

    fun Context.drawableRes(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

    fun View.callToast(@StringRes text: Int, short: Boolean = true) = Toast.makeText(
        context, text, if (short) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
    ).show()
}
