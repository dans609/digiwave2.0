package com.dash.projects.android.digiwave.`object`

import android.content.Context
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

class ToolbarPreferences(private val context: Context) {
    private var appbar: AppBarLayout? = null
    private var toolbar: Toolbar? = null
    private var appcompat: AppCompatActivity? = null

    fun injectAppbar(appbar: AppBarLayout) = apply {
        this.appbar = appbar
    }

    fun injectToolbar(toolbar: Toolbar) = apply {
        this.toolbar = toolbar
    }

    fun injectAppcompat(appcompat: AppCompatActivity) = apply {
        this.appcompat = appcompat
    }

    fun toolbarAsActionbar() = apply {
        appcompat?.setSupportActionBar(toolbar)
    }

    fun setElevation(@IntegerRes id: Int) = apply {
        appbar?.elevation = context.resources.getInteger(id).toFloat()
    }

    fun setTitle(@StringRes title: Int, withBackButton: Boolean = false) = apply {
        appcompat?.supportActionBar?.apply {
            this.title = context.getString(title)
            if (withBackButton) {
                setDisplayHomeAsUpEnabled(withBackButton)
                setDisplayShowHomeEnabled(withBackButton)
            }
        }
    }
}
