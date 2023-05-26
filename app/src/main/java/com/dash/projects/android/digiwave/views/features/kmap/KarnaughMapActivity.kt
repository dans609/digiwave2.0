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
            ToolbarPreferences.Builder(this)
                .injectAppbar(it.kmapAppbar)
                .injectToolbar(it.incToolbar.toolbar)
                .createInstance()
                .setToolbarAsActionbar()
                .setAppBarElevation(R.integer.low)
                .setActionbarTitle(R.string.karnaughMapFeatureName, true)
            injectViewPager(it)
        }
    }

    private fun injectViewPager(bind: ActivityKarnaughMapBinding) = bind.incKmapViewpager.run {
        KmapPagerAdapter(supportFragmentManager, lifecycle).let { adapter ->
            viewPager2.apply {
                this.adapter = adapter
                dotsIndicator.attachTo(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}
