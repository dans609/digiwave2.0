package com.dash.projects.android.digiwave.`object`

import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.appbar.AppBarLayout

class ToolbarPreferences(private val context: Context) {
    private var appbar: AppBarLayout? = null
    private var toolbar: Toolbar? = null
    private var appcompat: AppCompatActivity? = null
    private var window: Window? = null

    fun injectWindow(window: Window) = apply {
        this.window = window
    }

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

    fun setTitle(@StringRes title: Int, withBackButton: Boolean?) = apply {
        appcompat?.supportActionBar?.apply {
            this.title = context.getString(title)
            if (withBackButton != null) {
                setDisplayHomeAsUpEnabled(withBackButton)
                setDisplayShowHomeEnabled(withBackButton)
            }
        }
    }

    fun hideStatusBar() = apply {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
            window?.insetsController?.hide(WindowInsets.Type.statusBars())
        else window?.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
    }

    companion object {
        @Suppress("Deprecation")
        private const val FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN
    }
}
