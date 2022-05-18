package com.dash.projects.android.digiwave.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.`object`.utils.Utils.callToast
import com.dash.projects.android.digiwave.databinding.LayoutFeatureTemplateBinding
import com.dash.projects.android.digiwave.interfaces.OnFeatureClickCallback
import com.dash.projects.android.digiwave.model.AppFeature

class FeatureAdapter(private val clickCallbackListener: OnFeatureClickCallback) :
    RecyclerView.Adapter<FeatureAdapter.FeaturesViewHolder>() {
    private val _featureList = ArrayList<AppFeature>()
    private val featureList: List<AppFeature>
        get() = _featureList

    fun addFeatures(features: List<AppFeature>) =
        _featureList.addAll(features)

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
                        clickCallbackListener.onFeatureClicked(appFeature.featureType, v)
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
