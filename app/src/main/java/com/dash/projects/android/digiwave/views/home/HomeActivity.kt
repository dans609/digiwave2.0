package com.dash.projects.android.digiwave.views.home

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.ToolbarPreferences
import com.dash.projects.android.digiwave.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        ::_binding.set(ActivityHomeBinding.inflate(layoutInflater))
        setContentView(binding?.root)

        binding?.also {
            ToolbarPreferences.Builder(this)
                .injectToolbar(it.incToolbar.toolbar)
                .createInstance()
                .setToolbarAsActionbar()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.language_preferences -> startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
            R.id.subject_materials -> Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}
