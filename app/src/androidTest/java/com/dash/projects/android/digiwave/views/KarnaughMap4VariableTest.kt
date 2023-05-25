package com.dash.projects.android.digiwave.views

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.views.features.kmap.KarnaughMapActivity
import com.dash.projects.android.digiwave.utils.TestUtils.onViewWithParent
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.KarnaughMap4TestImpl.WhichParent
import com.dash.projects.android.digiwave.utils.KarnaughMap4TestImpl.Cell
import com.dash.projects.android.digiwave.utils.KarnaughMap4TestImpl.kmapPerformClick
import com.dash.projects.android.digiwave.utils.KarnaughMap4TestImpl.onViewAssert
import org.jetbrains.annotations.TestOnly
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class KarnaughMap4VariableTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(KarnaughMapActivity::class.java)
        onView(withId(R.id.view_pager2))
            .check(matches(isDisplayed()))
            .perform(swipeLeft(), swipeLeft())
            .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.inc_kmap_graphic)).check(matches(isDisplayed()))
        onView(withId(R.id.inc_kmap_variable)).check(matches(isDisplayed()))
        onView(withId(R.id.inc_kmap_type)).check(matches(isDisplayed()))
        onView(withId(R.id.inc_kmap_tiles)).check(matches(isDisplayed()))
        onView(withId(R.id.inc_kmap_answer)).check(matches(isDisplayed()))

        onView(withText(KMAP_VARIABLE)).check(matches(isDisplayed()))
        onView(withText(SOP_TYPE)).check(matches(isDisplayed()))
        onView(withId(R.id.kmap_variable_identifier1))
            .check(matches(withText(KMAP_VARIABLE_IDENTIFIER1)))
        onView(withId(R.id.kmap_variable_identifier2))
            .check(matches(withText(KMAP_VARIABLE_IDENTIFIER2)))
    }

    @Test
    fun chooseKarnaughMap4Variables_activateOrChangeAllCellsValueToOne_verifyTheAnswerIsOne() {
        val expectedResult = 1
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        // Action
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }

        // Assert
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult.toString())))
    }

    @Test
    fun `chooseKarnaughMap4Variables_doubleClickAllCellsToChangeItsValueToOneThenEmptyItAfter_verifyTheAnswer-BeforeAnActionShouldEmpty-AfterFirstActionShouldBeOne-AndLastActionShouldBeZero`() {
        val expectedResultBeforeAnAction = ""
        val expectedResultAfterFirstAction = 1
        val expectedResultAfterLastAction = 0

        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        // Pre-Assert
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultBeforeAnAction)))

        // Action #1
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText(""))).perform(click()) }

        // Assert #1
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText("1"))) }
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterFirstAction.toString())))

        // Action #2
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText("1"))).perform(click()) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText("1"))).perform(click()) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText("1"))).perform(click()) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText("1"))).perform(click()) }

        // Assert #2
        cellsRow1.forEach { (_, cell) -> cell.check(matches(withText(""))) }
        cellsRow2.forEach { (_, cell) -> cell.check(matches(withText(""))) }
        cellsRow3.forEach { (_, cell) -> cell.check(matches(withText(""))) }
        cellsRow4.forEach { (_, cell) -> cell.check(matches(withText(""))) }
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterLastAction.toString())))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-0000-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-BInvert-Times-CInvert-Times-DInvert`() {
        val expectedResult = "!A.!B.!C.!D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-0011-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-BInvert-Times-C-Times-D`() {
        val expectedResult = "!A.!B.C.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C11)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-0101-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-B-Times-CInvert-Times-D`() {
        val expectedResult = "!A.B.!C.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C01)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-0111-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-B-Times-C-Times-D`() {
        val expectedResult = "!A.B.C.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C11)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-1101-ValueToOne_verifyTheAnswerShouldBe-A-Times-B-Times-CInvert-Times-D`() {
        val expectedResult = "A.B.!C.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-1110-ValueToOne_verifyTheAnswerShouldBe-A-Times-B-Times-C-Times-DInvert`() {
        val expectedResult = "A.B.C.!D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-1000-ValueToOne_verifyTheAnswerShouldBe-A-Times-BInvert-Times-CInvert-Times-DInvert`() {
        val expectedResult = "A.!B.!C.!D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCell-1011-ValueToOne_verifyTheAnswerShouldBe-A-Times-BInvert-Times-C-Times-D`() {
        val expectedResult = "A.!B.C.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C11)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0011-0010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0101-0111-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C01, Cell.C11)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0100-0101-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1101-1111-ValueToOne_verifyTheAnswerShouldBe-ATimesBTimesD`() {
        val expectedResult = "A.B.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01, Cell.C11)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1100-1101-ValueToOne_verifyTheAnswerShouldBe-ATimesBTimesCInvert`() {
        val expectedResult = "A.B.!C"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1111-1110-ValueToOne_verifyTheAnswerShouldBe-ATimesBTimesC`() {
        val expectedResult = "A.B.C"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1001-1011-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesD`() {
        val expectedResult = "A.!B.D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C01, Cell.C11)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1000-1001-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesCInvert`() {
        val expectedResult = "A.!B.!C"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1011-1010-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesC`() {
        val expectedResult = "A.!B.C"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0100-0101-0111-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0101-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1100-1101-1111-ValueToOne_verifyTheAnswerShouldBe-ATimesBTimesCInvert-Plus-ATimesBTimesD`() {
        val expectedResult = "(A.B.!C) + (A.B.D)"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1101-1111-1110-ValueToOne_verifyTheAnswerShouldBe-ATimesBTimesD-Plus-ATimesBTimesC`() {
        val expectedResult = "(A.B.D) + (A.B.C)"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1000-1001-1011-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesCInvert-Plus-ATimesBInvertTimesD`() {
        val expectedResult = "(A.!B.!C) + (A.!B.D)"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesD-Plus-ATimesBInvertTimesC`() {
        val expectedResult = "(A.!B.D) + (A.!B.C)"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-0010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0100-0101-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1100-1101-1111-1110-ValueToOne_verifyTheAnswerShouldBe-ATimesB`() {
        val expectedResult = "A.B"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1000-1001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvert`() {
        val expectedResult = "A.!B"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0100-0101-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-1100-1101-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-1111-1110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-1000-1001-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-0100-0101-0111-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-0101-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-1100-1101-1111-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-1101-1111-1110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-1000-1001-1011-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0001-0011-0010-1001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-0010-0100-0101-0111-0110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-0010-1100-1101-1111-1110-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf()

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-0010-1000-1001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf()
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-0000-0001-0011-0010-0100-0110-1100-1110-1000-1001-1011-1010-ValueToOne_verifyTheAnswerShouldBe-NotDefined`() {
        val expectedResult = "N/D"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)
        val selectedCellInRow2: List<Cell> = listOf(Cell.C00, Cell.C10)
        val selectedCellInRow3: List<Cell> = listOf(Cell.C00, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C01, Cell.C11, Cell.C10)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap4Variables_changeCells-1101-1110-1000-1011-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvertTimesCInvertTimesDInvert-Plus-ATimesBInvertTimesCTimesD-Plus-ATimesBTimesCInvertTimesD-Plus-ATimesBTimesCTimesDInvert`() {
        val expectedResult = "(A.!B.!C.!D) + (A.!B.C.D) + (A.B.!C.D) + (A.B.C.!D)"
        val cellsRow1 = getCellsByParentType(WhichParent.Include1)
        val cellsRow2 = getCellsByParentType(WhichParent.Include2)
        val cellsRow3 = getCellsByParentType(WhichParent.Include3)
        val cellsRow4 = getCellsByParentType(WhichParent.Include4)

        val selectedCellInRow1: List<Cell> = listOf()
        val selectedCellInRow2: List<Cell> = listOf()
        val selectedCellInRow3: List<Cell> = listOf(Cell.C01, Cell.C10)
        val selectedCellInRow4: List<Cell> = listOf(Cell.C00, Cell.C11)

        // Action
        cellsRow1.forEach(kmapPerformClick(selectedCellInRow1, ""))
        cellsRow2.forEach(kmapPerformClick(selectedCellInRow2, ""))
        cellsRow3.forEach(kmapPerformClick(selectedCellInRow3, ""))
        cellsRow4.forEach(kmapPerformClick(selectedCellInRow4, ""))

        // Assert
        val selected = "1"
        val notSelected = ""
        cellsRow1.forEach(onViewAssert(selectedCellInRow1, selected, notSelected))
        cellsRow2.forEach(onViewAssert(selectedCellInRow2, selected, notSelected))
        cellsRow3.forEach(onViewAssert(selectedCellInRow3, selected, notSelected))
        cellsRow4.forEach(onViewAssert(selectedCellInRow4, selected, notSelected))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    companion object {
        private const val KMAP_VARIABLE = "4 Variable"
        private const val SOP_TYPE = "Type: SoP"
        private const val KMAP_VARIABLE_IDENTIFIER1 = "CD"
        private const val KMAP_VARIABLE_IDENTIFIER2 = "AB"

        @TestOnly
        private fun getCellsByParentType(whichParent: WhichParent): Map<Cell, ViewInteraction> {
            fun setPairCellToVInteraction(position: Int, vi: ViewInteraction) = when (position) {
                0 -> Cell.C00 to vi
                1 -> Cell.C01 to vi
                2 -> Cell.C11 to vi
                3 -> Cell.C10 to vi
                else -> Cell.ERROR to vi
            }

            val result = when (whichParent) {
                WhichParent.Include1 -> whichParent.childIds.mapIndexed { i, childId ->
                    setPairCellToVInteraction(i, onViewWithParent(childId, whichParent.parentId))
                }
                WhichParent.Include2 -> whichParent.childIds.mapIndexed { i, childId ->
                    setPairCellToVInteraction(i, onViewWithParent(childId, whichParent.parentId))
                }
                WhichParent.Include3 -> whichParent.childIds.mapIndexed { i, childId ->
                    setPairCellToVInteraction(i, onViewWithParent(childId, whichParent.parentId))
                }
                WhichParent.Include4 -> whichParent.childIds.mapIndexed { i, childId ->
                    setPairCellToVInteraction(i, onViewWithParent(childId, whichParent.parentId))
                }
            }

            return result.associate { it }
        }
    }
}
