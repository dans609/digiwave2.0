package com.dash.projects.android.digiwave.adapter.features.numsys

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dash.projects.android.digiwave.enum.NumberSystemCategory
import com.dash.projects.android.digiwave.views.features.numsys.branch.CodeSystemCategoryFragment
import com.dash.projects.android.digiwave.views.features.numsys.branch.NumberSystemCategoryFragment

class ViewPagerAdapter(fm: FragmentManager, l: Lifecycle) : FragmentStateAdapter(fm, l) {
    override fun getItemCount() = ITEM_CATEGORY.size

    override fun createFragment(position: Int) = when (ITEM_CATEGORY[position]) {
        NumberSystemCategory.NUMBER_SYSTEM -> NumberSystemCategoryFragment()
        NumberSystemCategory.CODE_SYSTEM -> CodeSystemCategoryFragment()
    }

    companion object {
        val ITEM_CATEGORY: List<NumberSystemCategory> = setOf(
            NumberSystemCategory.NUMBER_SYSTEM,
            NumberSystemCategory.CODE_SYSTEM,
        ).toList()
    }
}
