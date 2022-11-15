package com.dash.projects.android.digiwave.adapter.features.kmap

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dash.projects.android.digiwave.enum.KmapVariable
import com.dash.projects.android.digiwave.views.features.kmap.branch.KmapFourFragment
import com.dash.projects.android.digiwave.views.features.kmap.branch.KmapThreeFragment
import com.dash.projects.android.digiwave.views.features.kmap.branch.KmapTwoFragment

class KmapPagerAdapter(fm: FragmentManager, l: Lifecycle) : FragmentStateAdapter(fm, l) {
    override fun getItemCount() = KMAP_VARIABLES.size

    override fun createFragment(position: Int) = when (KMAP_VARIABLES[position]) {
        KmapVariable.TWO_VARIABLES -> KmapTwoFragment()
        KmapVariable.THREE_VARIABLES -> KmapThreeFragment()
        KmapVariable.FOURTH_VARIABLES -> KmapFourFragment()
    }

    companion object {
        private val KMAP_VARIABLES = enumValues<KmapVariable>()
    }
}
