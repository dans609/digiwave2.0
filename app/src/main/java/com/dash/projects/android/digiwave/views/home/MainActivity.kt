package com.dash.projects.android.digiwave.views.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dash.projects.android.digiwave.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ::_binding.set(ActivityMainBinding.inflate(layoutInflater))
        setContentView(binding?.root)

        // TODO: 24/12/2021
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}