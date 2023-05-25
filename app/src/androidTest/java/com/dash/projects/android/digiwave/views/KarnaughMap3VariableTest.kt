package com.dash.projects.android.digiwave.views

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.TestUtils.onViewWithParent
import com.dash.projects.android.digiwave.views.features.kmap.KarnaughMapActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class KarnaughMap3VariableTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(KarnaughMapActivity::class.java)
        onView(withId(R.id.view_pager2))
            .check(matches(isDisplayed()))
            .perform(swipeLeft())
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
    fun chooseKarnaughMap3Variables_activateOrChangeAllCellsValueToOne_verifyTheAnswerIsOne() {
        val expectedResult = 1

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())

        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult.toString())))
    }

    @Test
    fun `chooseKarnaughMap3Variables_doubleClickAllCellsToChangeItsValueToOneThenEmptyItAfter_verifyTheAnswer-BeforeAnActionShouldEmpty-AfterFirstActionShouldBeOne-AndLastActionShouldBeZero`() {
        val expectedResultBeforeAnAction = ""
        val expectedResultAfterFirstAction = 1
        val expectedResultAfterLastAction = 0

        // Pre-Assert
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultBeforeAnAction)))

        // Action #1
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())

        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert #1
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterFirstAction.toString())))

        // Action #2
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1"))).perform(click())

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1"))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1"))).perform(click())

        // Assert #2
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterLastAction.toString())))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCell-000-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-BInvert-Times-CInvert`() {
        val expectedResult = "!A.!B.!C"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCell-011-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-B-Times-C`() {
        val expectedResult = "!A.B.C"

        // Action
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCell-101-ValueToOne_verifyTheAnswerShouldBe-A-Times-BInvert-Times-C`() {
        val expectedResult = "A.!B.C"

        // Action
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCell-111-ValueToOne_verifyTheAnswerShouldBe-A-Times-B-Times-C`() {
        val expectedResult = "A.B.C"

        // Action
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-000-010-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-CInvert`() {
        val expectedResult = "!A.!C"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-101-111-ValueToOne_verifyTheAnswerShouldBe-A-Times-C`() {
        val expectedResult = "A.C"

        // Action
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-001-111-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvertTimesC-Plus-ATimesBTimesC`() {
        val expectedResult = "(!A.!B.C) + (A.B.C)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-010-100-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBTimesCInvert-Plus-ATimesBInvertTimesCInvert`() {
        val expectedResult = "(!A.B.!C) + (A.!B.!C)"

        // Action
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-010-110-ValueToOne_verifyTheAnswerShouldBe-BTimesCInvert`() {
        val expectedResult = "B.!C"

        // Action
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_ChangeCells-001-101-ValueToOne_verifyTheAnswerShouldBe-BInvertTimesC`() {
        val expectedResult = "!B.C"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-011-010-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesC-Plus-AInvertTimesB`() {
        val expectedResult = "(!A.C) + (!A.B)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-100-101-111-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvert-Plus-ATimesC`() {
        val expectedResult = "(A.!B) + (A.C)"

        // Action
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-100-101-ValueToOne_verifyTheAnswerShouldBe-BInvertTimesCInvert-Plus-ATimesBInvert`() {
        val expectedResult = "(!B.!C) + (A.!B)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-011-111-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesC-Plus-BTimesC`() {
        val expectedResult = "(!A.C) + (B.C)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-010-111-110-ValueToOne_verifyTheAnswerShouldBe-BTimesCInvert-Plus-ATimesB`() {
        val expectedResult = "(B.!C) + (A.B)"

        // Action
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-100-111-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvertTimesC-Plus-ATimesBInvertTimesCInvert-Plus-ATimesBTimesC`() {
        val expectedResult = "(!A.!B.C) + (A.!B.!C) + (A.B.C)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-011-101-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvertTimesCInvert-Plus-AInvertTimesBTimesC-Plus-ATimesBInvertTimesC`() {
        val expectedResult = "(!A.!B.!C) + (!A.B.C) + (A.!B.C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-011-101-110-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBTimesC-Plus-ATimesBInvertTimesC-Plus-ATimesBTimesCInvert`() {
        val expectedResult = "(!A.B.C) + (A.!B.C) + (A.B.!C)"

        // Action
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-010-110-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesCInvert-Plus-BTimesCInvert`() {
        val expectedResult = "(!A.!C) + (B.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-100-110-ValueToOne_verifyTheAnswerShouldBe-BInvertTimesCInvert-Plus-ATimesCInvert`() {
        val expectedResult = "(!B.!C) + (A.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-011-010-ValueToOne_verifyTheAnswerShouldBe-AInvert`() {
        val expectedResult = "!A"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-100-101-111-110-ValueToOne_verifyTheAnswerShouldBe-A`() {
        val expectedResult = "A"

        // Action
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-100-1O1-ValueToOne_verifyTheAnswerShouldBe-BInvert`() {
        val expectedResult = "!B"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-011-101-111-ValueToOne_verifyTheAnswerShouldBe-C`() {
        val expectedResult = "C"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-011-010-111-110-ValueToOne_verifyTheAnswerShouldBe-B`() {
        val expectedResult = "B"

        // Action
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-011-101-110-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvertTimesCInvert-Plus-AInvertTimesBTimesC-Plus-ATimesBInvertTimesC-ATimesBTimesCInvert`() {
        val expectedResult = "(!A.!B.!C) + (!A.B.C) + (A.!B.C) + (A.B.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-010-100-111-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvertTimesC-Plus-AInvertTimesBTimesCInvert-Plus-ATimesBInvertTimesCInvert-Plus-ATimesBTimesC`() {
        val expectedResult = "(!A.!B.C) + (!A.B.!C) + (A.!B.!C) + (A.B.C)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-010-111-110-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesCInvert-Plus-ATimesB`() {
        val expectedResult = "(!A.!C) + (A.B)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-010-100-101-ValueToOne_verifyTheAnswerShouldBe-ATimesBInvert-Plus-AInvertTimesCInvert`() {
        val expectedResult = "(A.!B) + (!A.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-100-101-110-ValueToOne_verifyTheAnswerShouldBe-BInvertTimesCInvert-Plus-ATimesBInvert-Plus-ATimesCInvert`() {
        val expectedResult = "(!B.!C) + (A.!B) + (A.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-011-010-110-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesCInvert-Plus-AInvertTimesB-Plus-BTimesCInvert`() {
        val expectedResult = "(!A.!C) + (!A.B) + (B.!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-011-100-111-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesBInvert-Plus-BInvertTimesCInvert-Plus-BTimesC`() {
        val expectedResult = "(!A.!B) + (!B.!C) + (B.C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-011-100-101-111-ValueToOne_verifyTheAnswerShouldBe-BInvertTimesCInvert-Plus-BTimesC-Plus-ATimesC`() {
        val expectedResult = "(!B.!C) + (B.C) + (A.C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-010-100-101-110-ValueToOne_verifyTheAnswerShouldBe-BInvert-Plus-CInvert`() {
        val expectedResult = "(!B) + (!C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-011-100-101-111-ValueToOne_verifyTheAnswerShouldBe-BInvert-Plus-C`() {
        val expectedResult = "(!B) + (C)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-000-001-011-010-100-101-111-ValueToOne_verifyTheAnswerShouldBe-C-Plus-AInvert-Plus-BInvert`() {
        val expectedResult = "(C) + (!A) + (!B)"

        // Action
        onViewWithParent(OPT00, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap3Variables_changeCells-001-011-010-100-101-111-110-ValueToOne_verifyTheAnswerShouldBe-C-Plus-B-Plus-A`() {
        val expectedResult = "(C) + (B) + (A)"

        // Action
        onViewWithParent(OPT01, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW1ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT00, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT01, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT11, ROW2ID).check(matches(withText(""))).perform(click())
        onViewWithParent(OPT10, ROW2ID).check(matches(withText(""))).perform(click())

        // Assert
        onViewWithParent(OPT00, ROW1ID).check(matches(withText("")))
        onViewWithParent(OPT01, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW1ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW1ID).check(matches(withText("1")))

        onViewWithParent(OPT00, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT01, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT11, ROW2ID).check(matches(withText("1")))
        onViewWithParent(OPT10, ROW2ID).check(matches(withText("1")))

        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    companion object {
        const val KMAP_VARIABLE = "3 Variable"
        const val SOP_TYPE = "Type: SoP"
        const val KMAP_VARIABLE_IDENTIFIER1 = "BC"
        const val KMAP_VARIABLE_IDENTIFIER2 = "A"

        private const val ROW1ID = R.id.inc_row_tiles1
        private const val ROW2ID = R.id.inc_row_tiles2

        private const val OPT00 = R.id.tv_kmap_option00
        private const val OPT01 = R.id.tv_kmap_option01
        private const val OPT11 = R.id.tv_kmap_option11
        private const val OPT10 = R.id.tv_kmap_option10
    }
}
