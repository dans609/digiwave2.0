package com.dash.projects.android.digiwave.views.menus.materials.branch

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.SubjectMaterialData
import com.dash.projects.android.digiwave.adapter.menus.SubjectMaterialAdapter
import com.dash.projects.android.digiwave.databinding.FragmentSubjectMaterialsBinding

class SubjectMaterialsFragment : Fragment() {
    private var _binding: FragmentSubjectMaterialsBinding? = null
    private val binding get() = _binding

    private val subjectMaterialAdapter by lazy(LazyThreadSafetyMode.NONE) {
        SubjectMaterialAdapter(SubjectMaterialData.generateSubjectMaterials())
    }

    private val materialNavigationFragment by lazy(LazyThreadSafetyMode.NONE) {
        MaterialNavigationFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSubjectMaterialsBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.apply {
            rvSubjectMaterials.setHasFixedSize(true)
            showRecyclerList(rvSubjectMaterials)
        }
    }

    private fun showRecyclerList(rv: RecyclerView) = subjectMaterialAdapter.also { adapter ->
        adapter.onMaterialClicked = ::onSubjectMaterialClicked
        rv.layoutManager = LinearLayoutManager(activity)
        rv.adapter = adapter
    }

    private fun onSubjectMaterialClicked(folderLink: String, title: String) {
        val fragmentContainerId = R.id.fragment_container_subject_materials
        val materialNavigationTag = MaterialNavigationFragment::class.java.simpleName
        val bundle = Bundle().apply {
            putString(MaterialNavigationFragment.EXTRA_URL, folderLink)
            putString(MaterialNavigationFragment.EXTRA_TITLE, title)
        }

        parentFragmentManager.beginTransaction().apply {
            materialNavigationFragment.arguments = bundle
            replace(fragmentContainerId, materialNavigationFragment, materialNavigationTag)
            addToBackStack(null)
            commit()
        }
    }
}