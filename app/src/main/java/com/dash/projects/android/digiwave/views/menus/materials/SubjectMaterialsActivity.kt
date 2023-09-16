package com.dash.projects.android.digiwave.views.menus.materials

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.ToolbarPreferences
import com.dash.projects.android.digiwave.databinding.ActivitySubjectMaterialsBinding
import com.dash.projects.android.digiwave.views.menus.materials.branch.SubjectMaterialsFragment

class SubjectMaterialsActivity : AppCompatActivity() {
    private val binding by lazy(LazyThreadSafetyMode.NONE) {
        ActivitySubjectMaterialsBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.also {
            ToolbarPreferences.Builder(this)
                .injectToolbar(it.incToolbar.toolbar)
                .createInstance()
                .setToolbarAsActionbar()
                .setToolbarTitle(R.string.subject_materials, true)
        }

        val fragmentTag = SubjectMaterialsFragment::class.java.simpleName
        val fragment = SubjectMaterialsFragment()

        if (supportFragmentManager.findFragmentByTag(fragmentTag) !is SubjectMaterialsFragment) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container_subject_materials, fragment, fragmentTag)
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home) {
            onBackPressedDispatcher.onBackPressed()
            return true
        }

        return super.onOptionsItemSelected(item)
    }
}
