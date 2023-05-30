package com.dash.projects.android.digiwave.adapter.menus

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.databinding.TemplateCardImageTitleBinding
import com.dash.projects.android.digiwave.model.SubjectMaterial

class SubjectMaterialAdapter(private val subjectMaterialList: List<SubjectMaterial>) :
    RecyclerView.Adapter<SubjectMaterialAdapter.SubjectMaterialViewHolder>() {
    var onMaterialClicked: ((folderLink: String, resTitleId: String) -> Unit)? = null

    inner class SubjectMaterialViewHolder(private val binding: TemplateCardImageTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subjectMaterial: SubjectMaterial, ordinal: Int) {
            binding.run {
                itemView.resources.let { res ->
                    imgMaterial.setImageResource(subjectMaterial.subjectImage)
                    tvMaterialTitle.text = res.getString(subjectMaterial.subjectTitle, ordinal)
                    tvMaterialDesc.text = res.getString(subjectMaterial.subjectDesc)

                    itemView.setOnClickListener {
                        it.callToast(tvMaterialDesc.text.toString())
                        onMaterialClicked?.invoke(
                            subjectMaterial.folderUrlLink,
                            tvMaterialTitle.text.toString(),
                        )
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SubjectMaterialViewHolder(
        TemplateCardImageTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SubjectMaterialViewHolder, position: Int) =
        holder.bind(subjectMaterialList[position], position + 1)

    override fun getItemCount(): Int = subjectMaterialList.size
}
