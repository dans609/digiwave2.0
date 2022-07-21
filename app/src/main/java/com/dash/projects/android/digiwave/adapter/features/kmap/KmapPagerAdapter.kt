package com.dash.projects.android.digiwave.adapter.features.kmap

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dash.projects.android.digiwave.enum.KmapVariable

class KmapPagerAdapter(fm: FragmentManager, l: Lifecycle) : FragmentStateAdapter(fm, l) {
    override fun getItemCount() = KMAP_VARIABLES.size

    override fun createFragment(position: Int) = when (KMAP_VARIABLES[position]) {
        KmapVariable.TWO_VARIABLES -> Fragment()
        KmapVariable.THREE_VARIABLES -> Fragment()
    }

    companion object {
        private val KMAP_VARIABLES = enumValues<KmapVariable>()
    }
}
