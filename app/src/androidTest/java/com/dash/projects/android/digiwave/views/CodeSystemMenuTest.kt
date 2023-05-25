package com.dash.projects.android.digiwave.views

import android.os.Build
import android.view.KeyEvent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.TestUtils.getStringArray
import com.dash.projects.android.digiwave.utils.TestUtils.pressKey
import com.dash.projects.android.digiwave.utils.TestUtils.withError
import com.dash.projects.android.digiwave.views.features.numsys.NumberSystemActivity
import org.hamcrest.Matchers.*
import org.jetbrains.annotations.TestOnly
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class CodeSystemMenuTest {
    private val fullyDisplayed = 100
    private val dropdownArray = getStringArray(R.array.code_system_mode)
    private val excess3Text = dropdownArray[EXCESS3_INDEX]
    private val grayText = dropdownArray[GRAY_INDEX]

    @get:TestOnly
    private lateinit var mActivityUT: NumberSystemActivity

    @Before
    fun setUp() {
        ActivityScenario.launch(NumberSystemActivity::class.java).onActivity { mActivityUT = it }
        onView(withId(R.id.view_pager2)).perform(swipeLeft(), swipeLeft())
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_encodeBinaryDigitsToBinary-CodedDecimal_verifyResultBeforeAndAfterEventDelete`() {
        val binaryDigitBeforeDeleting = 111010
        val expectedBCDDigitResultBefore = 1011000

        val binaryDigitAfterDeleting = 1110
        val expectedBCDDigitResultAfter = 10100

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bcd)).check(matches(withText(expectedBCDDigitResultBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 2
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(binaryDigitAfterDeleting.toString())))

        onView(withId(R.id.et_bcd)).check(matches(withText(expectedBCDDigitResultAfter.toString())))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_encodeForbiddenBinaryDigitsToBinary-CodedDecimal_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenBinaryDigit = 101011654
        val allowedBinaryDigit = 101011

        val expectedBCDDigitResult = 1000011

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenBinaryDigit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bin)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        onView(withId(R.id.et_bcd)).check(matches(withText(expectedBCDDigitResult.toString())))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedBinaryDigit.toString())))

        onView(withId(R.id.etl_bin)).check(matches(withError(null)))

        onView(withId(R.id.et_bcd)).check(matches(withText(expectedBCDDigitResult.toString())))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_encodeBinaryDigitsToBinary-CodedDecimalAndClearItsAfter_checkResultBeforeAndAfterClearingText`() {
        val binaryDigitBeforeClearing = 110010
        val expectedBCDDigitResultBefore = 1010000

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bcd)).check(matches(withText(expectedBCDDigitResultBefore.toString())))

        // Action and Assert #2
        val binaryEditTextInteractions = onView(withId(R.id.et_bin)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        binaryEditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_bcd)).check(matches(withText(expectedResultAfterClearing)))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_decodeBinary-CodedDecimalDigitsToBinary_verifyResultBeforeAndAfterEventDelete`() {
        val bcdDigitBeforeDeleting = 100010110
        val expectedBinaryDigitBefore = 1110100

        val bcdDigitAfterDeleting = 100010
        val expectedBinaryDigitAfter = 10110

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bcd))
            .check(matches(isDisplayed()))
            .perform(typeText(bcdDigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_bcd))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(bcdDigitAfterDeleting.toString())))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_decodeForbiddenBinary-CodedDecimalDigitsToBinary_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenBCDDigit = 101011082121
        val allowedBCDDigit = 1010110

        val expectedBinaryDigitResult = 111000

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bcd))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenBCDDigit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bcd)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        // Action and Assert #2
        val countOfActionDelete = 5
        onView(withId(R.id.et_bcd))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedBCDDigit.toString())))
        onView(withId(R.id.etl_bcd)).check(matches(withError(null)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResult.toString())))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_decodeForbiddenBinary-CodedDecimalNybbleToBinary_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenBCDNybble = 1111
        val continueTypingBCDDigit = "000"
        val allowedBCDNybble = 1111000

        val expectedBinaryDigitWhenForbiddenNybble = 111
        val expectedBinaryDigitResult = 1001110

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bcd))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenBCDNybble.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bcd)))
            .check(matches(withError("Contain BCD forbidden 4-bit: 1111")))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitWhenForbiddenNybble.toString())))

        // Action and Assert #2
        onView(withId(R.id.et_bcd))
            .perform(click(), typeText(continueTypingBCDDigit))
            .check(matches(withText(allowedBCDNybble.toString())))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.etl_bcd)).check(matches(withError(null)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResult.toString())))
    }

    @Test
    fun `selectBinaryAndBinary-CodedDecimalDropdown_decodeBinary-CodedDecimalDigitsToBinaryAndClearItsAfter_checkResultBeforeAndAfterClearingText`() {
        val bcdDigitBeforeClearing = 110010
        val expectedBinaryDigitResultBefore = 100000

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(R.string.auto_complete_tv_default_mode)))

        onView(withId(R.id.et_bcd)).check(matches(isDisplayed()))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bcd))
            .check(matches(isDisplayed()))
            .perform(typeText(bcdDigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResultBefore.toString())))

        // Action and Assert #2
        val bcdEditTextInteractions = onView(withId(R.id.et_bcd)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        bcdEditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_bcd)).check(matches(withText(expectedResultAfterClearing)))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_encodeBinaryDigitsToExcess-3_verifyResultBeforeAndAfterEventDelete`() {
        val binaryDigitBeforeDeleting = 111111
        val expectedXS3DigitResultBefore = 10010110

        val binaryDigitAfterDeleting = 11111
        val expectedXS3DigitResultAfter = "01100100"

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_xs3)).check(matches(withText(expectedXS3DigitResultBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 1
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(binaryDigitAfterDeleting.toString())))

        onView(withId(R.id.et_xs3)).check(matches(withText(expectedXS3DigitResultAfter)))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_encodeForbiddenBinaryDigitsToExcess-3_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenBinaryDigit = 101011654
        val allowedBinaryDigit = 101011

        val expectedXS3DigitResult = "01110110"

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenBinaryDigit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bin)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        onView(withId(R.id.et_xs3)).check(matches(withText(expectedXS3DigitResult)))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedBinaryDigit.toString())))

        onView(withId(R.id.etl_bin)).check(matches(withError(null)))

        onView(withId(R.id.et_xs3)).check(matches(withText(expectedXS3DigitResult)))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_encodeBinaryDigitsToExcess-3AndClearItsAfter_checkResultBeforeAndAfterClearingText`() {
        val binaryDigitBeforeClearing = 110010
        val expectedXS3DigitResultBefore = 10000011

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_xs3)).check(matches(withText(expectedXS3DigitResultBefore.toString())))

        // Action and Assert #2
        val binaryEditTextInteractions = onView(withId(R.id.et_bin)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        binaryEditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_xs3)).check(matches(withText(expectedResultAfterClearing)))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_decodeExcess-3DigitsToBinary_verifyResultBeforeAndAfterEventDelete`() {
        val xs3DigitBeforeDeleting = 10001011010
        val expectedBinaryDigitBefore = 1111111

        val xs3DigitAfterDeleting = 10001011
        val expectedBinaryDigitAfter = 111010

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_xs3))
            .check(matches(isDisplayed()))
            .perform(typeText(xs3DigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_xs3))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(xs3DigitAfterDeleting.toString())))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_decodeForbiddenExcess-3DigitsToBinary_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenXS3Digit = 101011082121
        val allowedXS3Digit = 1010110

        val expectedBinaryDigitResult = 10111

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_xs3))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenXS3Digit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_xs3)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        // Action and Assert #2
        val countOfActionDelete = 5
        onView(withId(R.id.et_xs3))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedXS3Digit.toString())))
        onView(withId(R.id.etl_xs3)).check(matches(withError(null)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResult.toString())))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_decodeForbiddenExcess-3NybbleToBinary_checkResultWhenErrorOccurAndVerifyTheErrorMessage`() {
        val forbiddenXS3Nybble = "01011"
        val continueTypingXS3Digit = "000"
        val allowedXS3Nybble = "01011000"

        val expectedBinaryDigitWhenForbiddenNybble = 10
        val expectedBinaryDigitResult = 11001

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_xs3))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenXS3Nybble), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_xs3)))
            .check(matches(withError("Contain XS3 forbidden 4-bit: 0000")))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitWhenForbiddenNybble.toString())))

        // Action and Assert #2
        onView(withId(R.id.et_xs3))
            .perform(click(), typeText(continueTypingXS3Digit))
            .check(matches(withText(allowedXS3Nybble)))
            .perform(closeSoftKeyboard())

        onView(withId(R.id.etl_xs3)).check(matches(withError(null)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResult.toString())))
    }

    @Test
    fun `selectBinaryAndExcess-3Dropdown_decodeExcess-3DigitsToBinaryAndClearItsAfter_checkResultBeforeAndAfterClearingText`() {
        val xs3DigitBeforeClearing = 11001010
        val expectedBinaryDigitResultBefore = 1100001

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(EXCESS3_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(excess3Text))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(excess3Text)))

        onView(withId(R.id.et_xs3)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_gray)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_xs3))
            .check(matches(isDisplayed()))
            .perform(typeText(xs3DigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResultBefore.toString())))

        // Action and Assert #2
        val xs3EditTextInteractions = onView(withId(R.id.et_xs3)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        xs3EditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_xs3)).check(matches(withText(expectedResultAfterClearing)))
    }

    // gray
    @Test
    fun selectBinaryAndGrayDropdown_encodeBinaryDigitsToGray_verifyResultBeforeAndAfterEventDelete() {
        val binaryDigitBeforeDeleting = 111111
        val expectedGrayDigitResultBefore = 100000

        val binaryDigitAfterDeleting = 11111
        val expectedGrayDigitResultAfter = 10000

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_gray)).check(matches(withText(expectedGrayDigitResultBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 1
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(binaryDigitAfterDeleting.toString())))

        onView(withId(R.id.et_gray)).check(matches(withText(expectedGrayDigitResultAfter.toString())))
    }

    @Test
    fun selectBinaryAndGrayDropdown_encodeForbiddenBinaryDigitsToGray_checkResultWhenErrorOccurAndVerifyTheErrorMessage() {
        val forbiddenBinaryDigit = 101011654
        val allowedBinaryDigit = 101011

        val expectedGrayDigitResult = 111110

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenBinaryDigit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_bin)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        onView(withId(R.id.et_gray)).check(matches(withText(expectedGrayDigitResult.toString())))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_bin))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedBinaryDigit.toString())))

        onView(withId(R.id.etl_bin)).check(matches(withError(null)))

        onView(withId(R.id.et_gray)).check(matches(withText(expectedGrayDigitResult.toString())))
    }

    @Test
    fun selectBinaryAndGrayDropdown_encodeBinaryDigitsToGrayAndClearItsAfter_checkResultBeforeAndAfterClearingText() {
        val binaryDigitBeforeClearing = 110010
        val expectedGrayDigitResultBefore = 101011

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_bin))
            .check(matches(isDisplayed()))
            .perform(typeText(binaryDigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_gray)).check(matches(withText(expectedGrayDigitResultBefore.toString())))

        // Action and Assert #2
        val binaryEditTextInteractions = onView(withId(R.id.et_bin)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        binaryEditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_gray)).check(matches(withText(expectedResultAfterClearing)))
    }

    @Test
    fun selectBinaryAndGrayDropdown_decodeGrayDigitsToBinary_verifyResultBeforeAndAfterEventDelete() {
        val grayDigitBeforeDeleting = 10001011010
        val expectedBinaryDigitBefore = 11110010011

        val grayDigitAfterDeleting = 10001011
        val expectedBinaryDigitAfter = 11110010

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
                .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_gray))
            .check(matches(isDisplayed()))
            .perform(typeText(grayDigitBeforeDeleting.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitBefore.toString())))

        // Action and Assert #2
        val countOfActionDelete = 3
        onView(withId(R.id.et_gray))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(grayDigitAfterDeleting.toString())))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitAfter.toString())))
    }

    @Test
    fun selectBinaryAndGrayDropdown_decodeForbiddenGrayDigitsToBinary_checkResultWhenErrorOccurAndVerifyTheErrorMessage() {
        val forbiddenGrayDigit = 101011082121
        val allowedGrayDigit = 1010110

        val expectedBinaryDigitResult = 1100100

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_gray))
            .check(matches(isDisplayed()))
            .perform(typeText(forbiddenGrayDigit.toString()), closeSoftKeyboard())

        onView(allOf(withId(R.id.etl_gray)))
            .check(matches(withError("Please input allowed digit: (0-1)")))

        // Action and Assert #2
        val countOfActionDelete = 5
        onView(withId(R.id.et_gray))
            .perform(click(), *pressKey(countOfActionDelete, KEYCODE_DEL))
            .check(matches(withText(allowedGrayDigit.toString())))
        onView(withId(R.id.etl_gray)).check(matches(withError(null)))

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResult.toString())))
    }

    @Test
    fun selectBinaryAndGrayDropdown_decodeGrayDigitsToBinaryAndClearItsAfter_checkResultBeforeAndAfterClearingText() {
        val grayDigitBeforeClearing = 11001010
        val expectedBinaryDigitResultBefore = 10001100

        val expectedResultAfterClearing = ""

        onView(withId(R.id.view_pager2)).check(matches(isDisplayingAtLeast(fullyDisplayed)))
        onView(withId(R.id.dropdown_inp_layout))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(anything())
            .inRoot(isPlatformPopup())
            .atPosition(GRAY_INDEX)
            .perform(click())

        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(grayText))
                .inRoot(withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.auto_complete_tv_dropdown)).check(matches(withText(grayText)))

        onView(withId(R.id.et_gray)).check(matches(isDisplayed()))
        onView(withId(R.id.et_bcd)).check(matches(not(isDisplayed())))
        onView(withId(R.id.et_xs3)).check(matches(not(isDisplayed())))

        // Action and Assert #1
        onView(withId(R.id.et_gray))
            .check(matches(isDisplayed()))
            .perform(typeText(grayDigitBeforeClearing.toString()), closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedBinaryDigitResultBefore.toString())))

        // Action and Assert #2
        val grayEditTextInteractions = onView(withId(R.id.et_gray)).perform(click())
        onView(allOf(withContentDescription("Clear text"), isDisplayed()))
            .check(matches(isDisplayed()))
            .perform(click())
        grayEditTextInteractions.perform(closeSoftKeyboard())

        onView(withId(R.id.et_bin)).check(matches(withText(expectedResultAfterClearing)))
        onView(withId(R.id.et_gray)).check(matches(withText(expectedResultAfterClearing)))
    }

    companion object {
        private const val KEYCODE_DEL = KeyEvent.KEYCODE_DEL
        private const val EXCESS3_INDEX = 1
        private const val GRAY_INDEX = 2
    }
}
