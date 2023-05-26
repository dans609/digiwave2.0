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

class ToolbarPreferences<in T : AppCompatActivity> private constructor(private val appCompat: T) {
    private val context: Context

    private var appBar: AppBarLayout? = null
    private var toolbar: Toolbar? = null

    init {
        context = appCompat
    }

    private fun setAppBar(mAppBar: AppBarLayout) {
        appBar = mAppBar
    }

    private fun setToolbar(mToolbar: Toolbar) {
        toolbar = mToolbar
    }

    class Builder<in T : AppCompatActivity>(appCompat: T) {
        private val toolbarPreferences: ToolbarPreferences<T>

        init {
            toolbarPreferences = ToolbarPreferences(appCompat)
        }

        fun injectAppbar(mAppbar: AppBarLayout): Builder<T> = apply {
            toolbarPreferences.setAppBar(mAppbar)
        }

        fun injectToolbar(mToolbar: Toolbar): Builder<T> = apply {
            toolbarPreferences.setToolbar(mToolbar)
        }

        fun createInstance() = toolbarPreferences
    }

    fun setToolbarAsActionbar() = apply {
        appCompat.setSupportActionBar(toolbar)
    }

    fun setAppBarElevation(@IntegerRes id: Int) = apply {
        appBar?.elevation = context.resources.getInteger(id).toFloat()
    }

    fun setActionbarTitle(@StringRes title: Int, withBackButton: Boolean?) = apply {
        appCompat.supportActionBar?.apply {
            this.title = context.getString(title)
            if (withBackButton != null) {
                setDisplayHomeAsUpEnabled(withBackButton)
                setDisplayShowHomeEnabled(withBackButton)
            }
        }
    }

    companion object {
        @Suppress("Deprecation")
        private const val FLAG_FULLSCREEN = WindowManager.LayoutParams.FLAG_FULLSCREEN

        fun hideStatusBar(window: Window) = apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
                window.insetsController?.hide(WindowInsets.Type.statusBars())
            else window.setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN)
        }
    }
}
