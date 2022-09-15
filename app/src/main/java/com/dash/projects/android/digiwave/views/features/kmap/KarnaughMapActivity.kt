package com.dash.projects.android.digiwave.views.features.kmap

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.ToolbarPreferences
import com.dash.projects.android.digiwave.adapter.features.kmap.KmapPagerAdapter
import com.dash.projects.android.digiwave.databinding.ActivityKarnaughMapBinding

class KarnaughMapActivity : AppCompatActivity() {
    private var _binding: ActivityKarnaughMapBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ::_binding.set(ActivityKarnaughMapBinding.inflate(layoutInflater))
        setContentView(binding?.root)

        binding?.also {
            ToolbarPreferences(this)
                .injectAppcompat(this)
                .injectAppbar(it.kmapAppbar)
                .injectToolbar(it.incToolbar.toolbar)
                .toolbarAsActionbar()
                .setElevation(R.integer.low)
                .setTitle(R.string.karnaughMapFeatureName, true)
            injectViewPager()
        }
    }

    private fun injectViewPager() = binding?.incKmapViewpager?.run {
        KmapPagerAdapter(supportFragmentManager, lifecycle).let { adapter ->
            viewPager2.adapter = adapter
            dotsIndicator.attachTo(viewPager2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}
