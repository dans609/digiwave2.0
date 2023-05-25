package com.dash.projects.android.digiwave.views

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.views.features.kmap.KarnaughMapActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class KarnaughMap2VariableTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(KarnaughMapActivity::class.java)
        onView(withId(R.id.view_pager2))
            .check(matches(isDisplayed()))
            .perform(swipeRight())
            .check(matches(isCompletelyDisplayed()))

        onView(withId(R.id.inc_kmap_graphic)).check(matches(isDisplayed()))
        onView(withId(R.id.inc_kmap_variable)).check(matches(isDisplayed()))

        onView(withText(KMAP_VARIABLE)).check(matches(isDisplayed()))
        onView(withText(SOP_TYPE)).check(matches(isDisplayed()))
        onView(withText(KMAP_VARIABLE_IDENTIFIER)).check(matches(isDisplayed()))

        onView(withId(R.id.kmap_00)).check(matches(withText("00")))
        onView(withId(R.id.kmap_01)).check(matches(withText("01")))
        onView(withId(R.id.kmap_11)).check(matches(withText("11")))
        onView(withId(R.id.kmap_10)).check(matches(withText("10")))
    }

    @Test
    fun chooseKarnaughMap2Variables_activateOrChangeAllCellsValueToOne_verifyTheAnswerIsOne() {
        val expectedResult = 1

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult.toString())))
    }

    @Test
    fun `chooseKarnaughMap2Variables_doubleClickAllCellsToChangeItsValueToOneThenEmptyItAfter_verifyTheAnswer-BeforeAnActionShouldEmpty-AfterFirstActionShouldBeOne-AndLastActionShouldBeZero`() {
        val expectedResultBeforeAnAction = ""
        val expectedResultAfterFirstAction = 1
        val expectedResultAfterLastAction = 0

        // Pre-Assert
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultBeforeAnAction)))

        // Action #1
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert #1
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterFirstAction.toString())))

        // Action #2
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1"))).perform(click())
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1"))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1"))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1"))).perform(click())

        // Assert #2
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResultAfterLastAction.toString())))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCell-10-ValueToOne_verifyTheAnswerShouldBe-A-Times-BInvert`() {
        val expectedResult = "A.!B"

        // Action
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCell-00-ValueToOne_verifyTheAnswerShouldBe-AInvert-Times-BInvert`() {
        val expectedResult = "!A.!B"

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-01-11-ValueToOne_verifyTheAnswerShouldBe-B`() {
        val expectedResult = "B"

        // Action
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-01-10-ValueToOne_verifyTheAnswerShouldBe-AInvertTimesB-Plus-ATimesBInvert`() {
        val expectedResult = "(!A.B) + (A.!B)"

        // Action
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-00-10-ValueToOne_verifyTheAnswerShouldBe-BInvert`() {
        val expectedResult = "!B"

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-00-01-ValueToOne_verifyTheAnswerShouldBe-AInvert`() {
        val expectedResult = "!A"

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-11-10-ValueToOne_verifyTheAnswerShouldBe-A`() {
        val expectedResult = "A"

        // Action
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-00-11-10-ValueToOne_verifyTheAnswerShouldBe-BInvert-Plus-A`() {
        val expectedResult = "(!B) + (A)"

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-00-01-10-ValueToOne_verifyTheAnswerShouldBe-BInvert-Plus-AInvert`() {
        val expectedResult = "(!A) + (!B)"

        // Action
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    @Test
    fun `chooseKarnaughMap2Variables_changeCells-01-11-10-ValueToOne_verifyTheAnswerShouldBe-B-Plus-A`() {
        val expectedResult = "(B) + (A)"

        // Action
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText(""))).perform(click())
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText(""))).perform(click())

        // Assert
        onView(withId(R.id.tv_kmap_option00)).check(matches(withText("")))
        onView(withId(R.id.tv_kmap_option01)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option11)).check(matches(withText("1")))
        onView(withId(R.id.tv_kmap_option10)).check(matches(withText("1")))
        onView(withId(R.id.kmap_answer)).check(matches(withText(expectedResult)))
    }

    companion object {
        const val KMAP_VARIABLE = "2 Variable"
        const val SOP_TYPE = "Type: SoP"
        const val KMAP_VARIABLE_IDENTIFIER = "AB"
    }
}
