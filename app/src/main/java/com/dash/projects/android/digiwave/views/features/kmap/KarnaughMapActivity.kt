package com.dash.projects.android.digiwave.views.features.kmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringRes
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

        actionBarSettings()
        injectViewPager()
    }

    private fun injectViewPager() = binding?.incKmapViewpager?.run {
        KmapPagerAdapter(supportFragmentManager, lifecycle).let { adapter ->
            viewPager2.adapter = adapter
            dotsIndicator.attachTo(viewPager2)
        }
    }

    private fun actionBarSettings() = supportActionBar?.apply {
        elevation = intRes(R.integer.low).toFloat()
        title = stringRes(R.string.karnaughMapFeatureName)
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}
