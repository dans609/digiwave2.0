package com.dash.projects.android.digiwave.adapter.features.numsys

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dash.projects.android.digiwave.enum.NumberSystemFeatureCategory
import com.dash.projects.android.digiwave.views.features.numsys.branch.CodeSystemCategoryFragment
import com.dash.projects.android.digiwave.views.features.numsys.branch.NumberSystemCategoryFragment

class ViewPagerAdapter(fm: FragmentManager, l: Lifecycle) : FragmentStateAdapter(fm, l) {
    override fun getItemCount() = FEATURE_CATEGORY.size

    override fun createFragment(position: Int) = when (FEATURE_CATEGORY[position]) {
        NumberSystemFeatureCategory.NUMBER_SYSTEM -> NumberSystemCategoryFragment()
        NumberSystemFeatureCategory.CODE_SYSTEM -> CodeSystemCategoryFragment()
    }

    companion object {
        val FEATURE_CATEGORY = NumberSystemFeatureCategory.values()
    }
}
