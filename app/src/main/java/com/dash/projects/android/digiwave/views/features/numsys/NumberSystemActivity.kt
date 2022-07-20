package com.dash.projects.android.digiwave.views.features.numsys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.intRes
import com.dash.projects.android.digiwave.`object`.utils.Utils.stringRes
import com.dash.projects.android.digiwave.adapter.features.numsys.ViewPagerAdapter
import com.dash.projects.android.digiwave.databinding.ActivityNumberSystemBinding
import com.dash.projects.android.digiwave.enum.NumberSystemFeatureCategory
import com.google.android.material.tabs.TabLayoutMediator

class NumberSystemActivity : AppCompatActivity() {
    private var _binding: ActivityNumberSystemBinding? = null
    private val binding
        get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ::_binding.set(ActivityNumberSystemBinding.inflate(layoutInflater))
        setContentView(binding?.root)

        actionBarSettings()
        injectViewPager()
    }

    private fun actionBarSettings() = supportActionBar?.apply {
        elevation = intRes(R.integer.low).toFloat()
        title = getString(R.string.numberSystemFeatureName)
    }

    private fun injectViewPager() = binding?.incTabLayout?.run {
        ViewPagerAdapter(supportFragmentManager, lifecycle).let { adapter ->
            viewPager2.adapter = adapter
            tabLayout.background = drawableRes(R.color.softGrey)
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = when (ViewPagerAdapter.FEATURE_CATEGORY[position]) {
                    NumberSystemFeatureCategory.NUMBER_SYSTEM -> stringRes(R.string.numberSystemFeatureName)
                    NumberSystemFeatureCategory.CODE_SYSTEM -> stringRes(R.string.code_system_tab_title)
                }
            }.attach()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }

    companion object {
        const val ON_NEXT_TAG = "onNext"
        const val ON_ERROR_TAG = "onError"
    }
}
