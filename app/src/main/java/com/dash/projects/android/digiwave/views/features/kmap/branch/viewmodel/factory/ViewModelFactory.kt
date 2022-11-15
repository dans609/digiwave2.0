package com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapFourViewModel
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapThreeViewModel
import com.dash.projects.android.digiwave.views.features.kmap.branch.viewmodel.KmapTwoViewModel

class ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    private val String.message get() = "Unknown ViewModel Class $this"

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T = when {
        modelClass.isAssignableFrom(KmapTwoViewModel::class.java) -> KmapTwoViewModel() as T
        modelClass.isAssignableFrom(KmapThreeViewModel::class.java) -> KmapThreeViewModel() as T
        modelClass.isAssignableFrom(KmapFourViewModel::class.java) -> KmapFourViewModel() as T
        else -> throw Throwable(modelClass.name.message)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null

        fun getInstance(): ViewModelFactory = instance ?: synchronized(this) {
            instance ?: ViewModelFactory().apply {
                instance = this
            }
        }
    }
}
