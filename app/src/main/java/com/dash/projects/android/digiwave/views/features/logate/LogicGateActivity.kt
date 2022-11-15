package com.dash.projects.android.digiwave.views.features.logate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dash.projects.android.digiwave.`object`.ToolbarPreferences
import com.dash.projects.android.digiwave.databinding.ActivityLogicGateBinding

class LogicGateActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivityLogicGateBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        ToolbarPreferences(this)
            .injectWindow(window)
            .hideStatusBar()
    }
}
