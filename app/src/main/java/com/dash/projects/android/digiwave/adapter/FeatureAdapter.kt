package com.dash.projects.android.digiwave.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import com.dash.projects.android.digiwave.databinding.LayoutFeatureTemplateBinding
import com.dash.projects.android.digiwave.interfaces.OnFeatureClickCallback
import com.dash.projects.android.digiwave.model.AppFeature

class FeatureAdapter(private val clickCallbackListener: OnFeatureClickCallback) :
    RecyclerView.Adapter<FeatureAdapter.FeaturesViewHolder>() {
    private val listFeatures = ArrayList<AppFeature>()

    fun addFeatures(features: List<AppFeature>) = listFeatures.addAll(features)

    inner class FeaturesViewHolder(private val binding: LayoutFeatureTemplateBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(appFeature: AppFeature) {
            binding.run {
                itemView.resources.let { res ->
                    imgFeature.setImageResource(appFeature.featureImage)
                    tvFeatureTitle.text = res.getString(appFeature.featureName)
                    tvFeatureDesc.text = res.getString(appFeature.featureDescription)

                    itemView.setOnClickListener {
                        Toast.makeText(
                            it.context,
                            tvFeatureTitle.text.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                        clickCallbackListener.onFeatureClicked(appFeature.featureType)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeaturesViewHolder =
        FeaturesViewHolder(
            LayoutFeatureTemplateBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: FeaturesViewHolder, position: Int) =
        holder.bind(listFeatures[position])

    override fun getItemCount(): Int = listFeatures.size
}