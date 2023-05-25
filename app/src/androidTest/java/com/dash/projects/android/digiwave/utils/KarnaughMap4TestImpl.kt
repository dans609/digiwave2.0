package com.dash.projects.android.digiwave.utils

import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.dash.projects.android.digiwave.R
import org.jetbrains.annotations.TestOnly

object KarnaughMap4TestImpl {
    @TestOnly
    fun <T : Collection<Cell>> kmapPerformClick(
        selectedCell: T,
        whichMatchesWithText: String
    ): ((Map.Entry<Cell, ViewInteraction>) -> Unit) = { (type, cell) ->
        if (selectedCell.isEmpty()) Unit
        else if (type in selectedCell) cell
            .check(ViewAssertions.matches(ViewMatchers.withText(whichMatchesWithText)))
            .perform(ViewActions.click())
    }

    @TestOnly
    fun <T : Collection<Cell>> onViewAssert(
        selectedCell: T,
        selectedMatchesWithText: String,
        notSelectedMatchesWithText: String,
    ): ((Map.Entry<Cell, ViewInteraction>) -> Unit) = { (type, cell) ->
        cell.check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    if (type in selectedCell) selectedMatchesWithText
                    else notSelectedMatchesWithText
                )
            )
        )
    }

    enum class Cell {
        C00, C01, C11, C10, ERROR
    }

    sealed class WhichParent(val childIds: List<Int>, val parentId: Int) {
        companion object {
            private const val ROW1_ID = R.id.inc_row1
            private const val ROW2_ID = R.id.inc_row2
            private const val ROW3_ID = R.id.inc_row3
            private const val ROW4_ID = R.id.inc_row4

            @TestOnly
            private fun getChildIds() = listOf(
                R.id.tv_kmap_option00,
                R.id.tv_kmap_option01,
                R.id.tv_kmap_option11,
                R.id.tv_kmap_option10,
            )
        }

        object Include1 : WhichParent(getChildIds(), ROW1_ID)
        object Include2 : WhichParent(getChildIds(), ROW2_ID)
        object Include3 : WhichParent(getChildIds(), ROW3_ID)
        object Include4 : WhichParent(getChildIds(), ROW4_ID)
    }
}
