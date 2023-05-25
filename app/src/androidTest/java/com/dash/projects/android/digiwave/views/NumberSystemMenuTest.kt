package com.dash.projects.android.digiwave.views

import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.TestUtils.withError
import com.dash.projects.android.digiwave.views.features.numsys.NumberSystemActivity
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class NumberSystemMenuTest {
    private val fullyDisplayed = 100

    @Before
    fun setUp() {
        ActivityScenario.launch(NumberSystemActivity::class.java)
        onView(withId(R.id.view_pager2)).perform(swipeRight())
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doBinaryCalculationsAndDeleteSomeOfItsDigit_verifyResultBeforeAndAfterEventDelete() {
        // Arrange (Initial)
        val bitBeforeDeleting = 1011
        val bitAfterDeleting = 10

        val expectedDecimalDigitBefore = 11
        val expectedDecimalDigitAfter = 2

        val expectedOctalDigitBefore = 13
        val expectedOctalDigitAfter = 2

        val expectedHexDigitBefore = "B"
        val expectedHexDigitAfter = 2

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(bitBeforeDeleting.toString()))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        onView(withId(R.id.et_bin))
            .perform(replaceText(bitBeforeDeleting.toString()))
            .perform(click())
            .perform(pressKey(KeyEvent.KEYCODE_DEL))
            .perform(pressKey(KeyEvent.KEYCODE_DEL))
            .check(matches(withText(bitAfterDeleting.toString())))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitAfter.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitAfter.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitAfter.toString())))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doBinaryCalculationsAndInputSomeForbiddenDigit_checkResultWhenErrorOccurAndVerifyTheErrorMessage() {
        // Arrange (Initial)
        val bitBeforeDeleting = 1111851
        val bitAfterDeleting = 1111

        val expectedDecimalDigitBefore = 15
        val expectedDecimalDigitAfter = 15

        val expectedOctalDigitBefore = 17
        val expectedOctalDigitAfter = 17

        val expectedHexDigitBefore = "F"
        val expectedHexDigitAfter = "F"

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(bitBeforeDeleting.toString()))
            .perform(closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bin)))
            .check(matches(isDisplayed()))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        val keyCode = KeyEvent.KEYCODE_DEL
        onView(withId(R.id.et_bin))
            .perform(replaceText(bitBeforeDeleting.toString()), click())
            .perform(pressKey(keyCode), pressKey(keyCode), pressKey(keyCode))
            .check(matches(withText(bitAfterDeleting.toString())))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.etl_bin)).check(matches(withError(null)))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitAfter.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitAfter.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitAfter)))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doBinaryCalculationsAndClearItAfter_checkResultBeforeAndAfterClearingText() {
        // Arrange (Initial)
        val bitBeforeClearing = 1111
        val expectedDecimalDigitBefore = 15
        val expectedOctalDigitBefore = 17
        val expectedHexDigitBefore = "F"

        val expectedDigitOfAllInputsAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(bitBeforeClearing.toString()))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        val binaryEditTextInteraction = onView(withId(R.id.et_bin)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        binaryEditTextInteraction
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doDecimalCalculationsAndDeleteSomeOfItsDigit_verifyResultBeforeAndAfterEventDelete() {
        // Arrange (Initial)
        val digitBeforeDeleting = 671
        val digitAfterDeleting = 67

        val expectedBinaryDigitBefore = 1010011111
        val expectedBinaryDigitAfter = 1000011

        val expectedOctalDigitBefore = 1237
        val expectedOctalDigitAfter = 103

        val expectedHexDigitBefore = "29F"
        val expectedHexDigitAfter = 43

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_dec))
            .check(matches(isDisplayed()))
            .perform(typeText(digitBeforeDeleting.toString()))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        onView(withId(R.id.et_dec))
            .perform(replaceText(digitBeforeDeleting.toString()))
            .perform(click())
            .perform(pressKey(KeyEvent.KEYCODE_DEL))
            .check(matches(withText(digitAfterDeleting.toString())))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitAfter.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitAfter.toString())))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doDecimalCalculationsAndClearItAfter_checkResultBeforeAndAfterClearingText() {
        // Arrange (Initial)
        val decimalBeforeClearing = 4621
        val expectedBinaryDigitBefore = 1001000001101
        val expectedOctalDigitBefore = 11015
        val expectedHexDigitBefore = "120D"

        val expectedDigitOfAllInputsAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_dec))
            .check(matches(isDisplayed()))
            .perform(typeText(decimalBeforeClearing.toString()))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        val decimalEditTextInteraction = onView(withId(R.id.et_dec)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        decimalEditTextInteraction
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doOctalCalculationsAndDeleteSomeOfItsDigit_verifyResultBeforeAndAfterEventDelete() {
        // Arrange (Initial)
        val digitBeforeDeleting = 573
        val digitAfterDeleting = 57

        val expectedBinaryDigitBefore = 101111011
        val expectedBinaryDigitAfter = 101111

        val expectedDecDigitBefore = 379
        val expectedDecDigitAfter = 47

        val expectedHexDigitBefore = "17B"
        val expectedHexDigitAfter = "2F"

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_oct))
            .check(matches(isDisplayed()))
            .perform(typeText(digitBeforeDeleting.toString()))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore)))

        // Action and Assert #2
        onView(withId(R.id.et_oct))
            .perform(replaceText(digitBeforeDeleting.toString()), click())
            .perform(pressKey(KeyEvent.KEYCODE_DEL))
            .check(matches(withText(digitAfterDeleting.toString())))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecDigitAfter.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitAfter)))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doOctalCalculationsAndInputSomeForbiddenDigit_checkResultWhenErrorOccurAndVerifyTheErrorMessage() {
        // Arrange (Initial)
        val octalBeforeDeleting = 54798
        val octalAfterDeleting = 547

        val expectedDecimalDigitBefore = 359
        val expectedDecimalDigitAfter = 359

        val expectedBinaryDigitBefore = 101100111
        val expectedBinaryDigitAfter = 101100111

        val expectedHexDigitBefore = 167
        val expectedHexDigitAfter = 167

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_oct))
            .check(matches(isDisplayed()))
            .perform(typeText(octalBeforeDeleting.toString()))
            .perform(closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_oct)))
            .check(matches(isDisplayed()))
            .check(matches(withError("Please input allowed digit: (0-7)")))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore.toString())))

        // Action and Assert #2
        val keyCode = KeyEvent.KEYCODE_DEL
        onView(withId(R.id.et_oct))
            .perform(replaceText(octalBeforeDeleting.toString()), click())
            .perform(pressKey(keyCode), pressKey(keyCode))
            .check(matches(withText(octalAfterDeleting.toString())))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.etl_oct)).check(matches(withError(null)))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitAfter.toString())))
        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitAfter.toString())))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doOctalCalculationsAndClearItAfter_checkResultBeforeAndAfterClearingText() {
        // Arrange (Initial)
        val octalBeforeClearing = 4621
        val expectedBinaryDigitBefore = 100110010001
        val expectedDecimalDigitBefore = 2449
        val expectedHexDigitBefore = 991

        val expectedDigitOfAllInputsAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_hex)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_oct))
            .check(matches(isDisplayed()))
            .perform(typeText(octalBeforeClearing.toString()))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedHexDigitBefore.toString())))

        // Action and Assert #2
        val octalEditTextInteraction = onView(withId(R.id.et_oct)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        octalEditTextInteraction
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doHexCalculationsAndDeleteSomeOfItsDigit_verifyResultBeforeAndAfterEventDelete() {
        // Arrange (Initial)
        val digitBeforeDeleting = "5EED"
        val digitAfterDeleting = "5E"

        val expectedBinaryDigitBefore = 101111011101101
        val expectedBinaryDigitAfter = 1011110

        val expectedDecDigitBefore = 24301
        val expectedDecDigitAfter = 94

        val expectedOctDigitBefore = 57355
        val expectedOctDigitAfter = 136

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_hex))
            .check(matches(isDisplayed()))
            .perform(typeText(digitBeforeDeleting), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctDigitBefore.toString())))

        // Action and Assert #2
        onView(withId(R.id.et_hex))
            .perform(replaceText(digitBeforeDeleting), click())
            .perform(pressKey(KeyEvent.KEYCODE_DEL), pressKey(KeyEvent.KEYCODE_DEL))
            .perform(closeSoftKeyboard())
            .check(matches(withText(digitAfterDeleting)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecDigitAfter.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctDigitAfter.toString())))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doHexCalculationsAndInputSomeForbiddenDigit_checkResultWhenErrorOccurAndVerifyTheErrorMessage() {
        // Arrange (Initial)
        val hexBeforeDeleting = "F52AZX69"
        val hexAfterDeleting = "F52A"

        val expectedDecimalDigitBefore = 62762
        val expectedDecimalDigitAfter = 62762

        val expectedBinaryDigitBefore = 1111010100101010
        val expectedBinaryDigitAfter = 1111010100101010

        val expectedOctalDigitBefore = 172452
        val expectedOctalDigitAfter = 172452

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))

        // Action and Assert #1
        val forbiddenSymbols = arrayOf(0, 9, "A", "F")
        val errorResource = R.string.et_error_format_with_symbol

        onView(withId(R.id.et_hex))
            .check(matches(isDisplayed()))
            .perform(typeText(hexBeforeDeleting))
            .perform(closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_hex)))
            .check(matches(isDisplayed()))
            .check(matches(withError(errorResource, forbiddenSymbols)))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))

        // Action and Assert #2
        val keyCode = KeyEvent.KEYCODE_DEL
        onView(withId(R.id.et_hex))
            .perform(replaceText(hexBeforeDeleting), click())
            .perform(pressKey(keyCode), pressKey(keyCode), pressKey(keyCode), pressKey(keyCode))
            .check(matches(withText(hexAfterDeleting)))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.etl_hex)).check(matches(withError(null)))

        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitAfter.toString())))
        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitAfter.toString())))
    }

    @Test
    fun openNumberSystemMenuAtNumberSystemFeatures_doHexCalculationsAndClearItAfter_checkResultBeforeAndAfterClearingText() {
        // Arrange (Initial)
        val hexBeforeClearing = "FD5AB"
        val expectedBinaryDigitBefore = "11111101010110101011"
        val expectedDecimalDigitBefore = 1037739
        val expectedOctalDigitBefore = 3752653

        val expectedDigitOfAllInputsAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))

        onView(withId(R.id.et_bin)).check(matches(isDisplayed()))
        onView(withId(R.id.et_dec)).check(matches(isDisplayed()))
        onView(withId(R.id.et_oct)).check(matches(isDisplayed()))

        // Action and Assert #1
        onView(withId(R.id.et_hex))
            .check(matches(isDisplayed()))
            .perform(typeText(hexBeforeClearing))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore)))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDecimalDigitBefore.toString())))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedOctalDigitBefore.toString())))

        // Action and Assert #2
        onView(withId(R.id.et_hex)).perform(click(), closeSoftKeyboard())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_dec)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_oct)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
        onView(withId(R.id.et_hex)).check(matches(withText(expectedDigitOfAllInputsAfterClearing)))
    }
}
