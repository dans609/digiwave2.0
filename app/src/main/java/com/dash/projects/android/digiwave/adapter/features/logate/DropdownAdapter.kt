package com.dash.projects.android.digiwave.adapter.features.logate

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.databinding.LayoutDropdownDrawableItemBinding
import com.dash.projects.android.digiwave.model.DrawableDropdownItem

class DropdownAdapter(context: Context, resource: Int, objects: Array<out DrawableDropdownItem>) :
    ArrayAdapter<String>(context, resource, objects.map { it.itemName }) {
    private val dropdownList = ArrayList<DrawableDropdownItem>()

    init {
        dropdownList.clear()
        dropdownList.addAll(objects)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: LayoutDropdownDrawableItemBinding
        var row = convertView

        if (row == null) {
            binding = LayoutDropdownDrawableItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

            row = binding.root
        } else binding = LayoutDropdownDrawableItemBinding.bind(row)

        binding.apply {
            tvGateName.text = getItem(position)
            imageDropdownIcon.run {
                setImageResource(dropdownList[position].itemImage)
                val contentDescriptionId = R.string.cd_icon_logic_gate
                contentDescription = context.getString(contentDescriptionId, getItem(position))
            }
        }

        return row
    }
}
