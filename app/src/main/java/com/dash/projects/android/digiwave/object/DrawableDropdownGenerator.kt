package com.dash.projects.android.digiwave.`object`

import android.content.Context
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.`object`.utils.Utils.drawableResListOf
import com.dash.projects.android.digiwave.model.DrawableDropdownItem

class DrawableDropdownGenerator(context: Context) {
    private val drawables = drawableResListOf(
        R.drawable.ic_and, R.drawable.ic_or, R.drawable.ic_not,
        R.drawable.ic_nand, R.drawable.ic_nor, R.drawable.ic_xor,
        R.drawable.ic_xnor,
    )

    private val items = context.resources.getStringArray(R.array.gate_list)

    fun generateDropdownItems() = items.mapIndexed { index, itemName ->
        DrawableDropdownItem(itemName, drawables[index])
    }
}
