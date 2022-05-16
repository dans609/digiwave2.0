package com.dash.projects.android.digiwave.views.features.numsys

import android.os.Bundle
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.adapter.features.numsys.ViewPagerAdapter
import com.dash.projects.android.digiwave.databinding.ActivityNumberSystemBinding
import com.dash.projects.android.digiwave.enum.NumberSystemCategory
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
    }

    private fun injectViewPager() = binding?.incTabLayout?.run {
        ViewPagerAdapter(supportFragmentManager, lifecycle).let { adapter ->
            viewPager2.adapter = adapter
            tabLayout.background = drawableRes(R.color.softGrey)
            TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
                tab.text = when (ViewPagerAdapter.ITEM_CATEGORY[position]) {
                    NumberSystemCategory.NUMBER_SYSTEM -> stringRes(R.string.numberSystemFeatureName)
                    NumberSystemCategory.CODE_SYSTEM -> stringRes(R.string.code_system_tab_title)
                }
            }.attach()
        }
    }

    private fun intRes(@IntegerRes id: Int) =
        resources::getInteger.invoke(id)

    private fun stringRes(@StringRes id: Int) =
        resources.getString(id)

    private fun drawableRes(@DrawableRes id: Int) =
        ContextCompat.getDrawable(this, id)

    override fun onDestroy() {
        super.onDestroy()
        ::_binding.set(null)
    }
}
