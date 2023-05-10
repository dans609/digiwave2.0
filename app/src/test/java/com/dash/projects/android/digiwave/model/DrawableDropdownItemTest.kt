package com.dash.projects.android.digiwave.model

import org.junit.Assert
import org.junit.Test

class DrawableDropdownItemTest {
    private val dropdownItem by lazy(LazyThreadSafetyMode.NONE) {
        DrawableDropdownItem("and", 121213)
    }

    @Test
    fun getDropdownItemName_validatingItemName_returnTrue() {
        val expectedItemName = "and"
        Assert.assertEquals(expectedItemName, dropdownItem.itemName)
    }

    @Test
    fun getDropdownItemImageId_validatingImageId_returnTrue() {
        val expectedImageId = 121213
        Assert.assertEquals(expectedImageId, dropdownItem.itemImage)
    }
}