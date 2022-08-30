package com.dash.projects.android.digiwave.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.databinding.LayoutFeatureTemplateBinding
import com.dash.projects.android.digiwave.enum.FeatureName
import com.dash.projects.android.digiwave.model.AppFeature

class FeatureAdapter :
    RecyclerView.Adapter<FeatureAdapter.FeaturesViewHolder>() {
    var onFeatureClicked: ((FeatureName, View) -> Unit)? = null
    private val featureList = ArrayList<AppFeature>()

    fun addFeatures(features: List<AppFeature>) =
        featureList.addAll(features)

    inner class FeaturesViewHolder(private val binding: LayoutFeatureTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appFeature: AppFeature) {
            binding.run {
                itemView.resources.let { res ->
                    imgFeature.setImageResource(appFeature.featureImage)
                    tvFeatureTitle.text = res.getString(appFeature.featureName)
                    tvFeatureDesc.text = res.getString(appFeature.featureDescription)

                    itemView.setOnClickListener { v ->
                        v.callToast(appFeature.featureName)
                        onFeatureClicked?.invoke(appFeature.featureType, v)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeaturesViewHolder(
        LayoutFeatureTemplateBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: FeaturesViewHolder, position: Int) =
        holder.bind(featureList[position])

    override fun getItemCount() = featureList.size
}
