package com.dash.projects.android.digiwave.views

import android.os.Build
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.RootMatchers.isPlatformPopup
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.DrawableMatcher.Companion.withDrawable
import com.dash.projects.android.digiwave.utils.TestUtils.withTextInputHint
import com.dash.projects.android.digiwave.views.features.logate.LogicGateActivity
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.not
import org.jetbrains.annotations.TestOnly
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class LogicGateSimulatorTest {
    @get:TestOnly
    private lateinit var mActivityUT: LogicGateActivity

    @Before
    fun setUp() {
        ActivityScenario.launch(LogicGateActivity::class.java).onActivity { mActivityUT = it }

        onView(withId(R.id.inc_layout_logate)).check(matches(isDisplayed()))
        onView(withId(R.id.til_dropdown)).check(matches(isDisplayed()))
        onView(withId(R.id.auto_tv_gates)).check(matches(isDisplayed()))
        onView(withId(R.id.image_simulation)).check(matches(isDisplayed()))

        onView(withId(R.id.tv_gate_inp1)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_gate_inp2)).check(matches(isDisplayed()))
        onView(withId(R.id.image_gate)).check(matches(isDisplayed()))
        onView(withId(R.id.line_gate_output)).check(matches(isDisplayed()))
        onView(withId(R.id.image_output)).check(matches(isDisplayed()))
    }

    @Test
    fun selectAndGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOff() {
        val gateText = "AND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_and)))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_and_inp)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectAndGate_onlyActivateInput1_verifyTheOutputShouldBeLampOff() {
        val gateText = "AND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_and)))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_and_inp)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectAndGate_onlyActivateInput2_verifyTheOutputShouldBeLampOff() {
        val gateText = "AND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_and)))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_and_inp)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectAndGate_activateAllInput_verifyTheOutputShouldBeLampOn() {
        val gateText = "AND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_and)))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_and_inp)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectORGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(OR_INDEX).perform(click())

        // Arrange
        val gateText = "OR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_or))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_or_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectORGate_onlyActivateInput1_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(OR_INDEX).perform(click())

        // Arrange
        val gateText = "OR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_or))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_or_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectORGate_onlyActivateInput2_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(OR_INDEX).perform(click())

        // Arrange
        val gateText = "OR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_or))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_or_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectORGate_activateAllInput_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(OR_INDEX).perform(click())

        // Arrange
        val gateText = "OR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_or))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_or_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNOTGate_deactivateInput1OrChangeValueToZero_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NOT_INDEX).perform(click())

        // Arrange
        val gateText = "NOT Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_not))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(not(isDisplayed())))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_not_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(not(isDisplayed())))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNOTGate_activateInput1_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NOT_INDEX).perform(click())

        // Arrange
        val gateText = "NOT Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_not))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(not(isDisplayed())))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_not_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(not(isDisplayed())))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectNANDGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NAND_INDEX).perform(click())

        // Arrange
        val gateText = "NAND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nand))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nand_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNANDGate_onlyActivateInput1_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NAND_INDEX).perform(click())

        // Arrange
        val gateText = "NAND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nand))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nand_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNANDGate_onlyActivateInput2_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NAND_INDEX).perform(click())

        // Arrange
        val gateText = "NAND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nand))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nand_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNANDGate_activateAllInput_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup()).atPosition(NAND_INDEX).perform(click())

        // Arrange
        val gateText = "NAND Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nand))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nand_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectNORGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(NOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "NOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nor_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectNORGate_onlyActivateInput1_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(NOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "NOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectNORGate_onlyActivateInput2_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(NOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "NOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectNORGate_activateAllInput_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(NOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "NOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_nor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_nor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectXORGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xor_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectXORGate_onlyActivateInput1_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectXORGate_onlyActivateInput2_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectXORGate_activateAllInput_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectXNORGate_deactivateInput1AndInput2OrChangeValueToZero_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XNOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XNOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xnor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xnor_inp))) // must be checked

        // Action
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Test
    fun selectXNORGate_onlyActivateInput1_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XNOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XNOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xnor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xnor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectXNORGate_onlyActivateInput2_verifyTheOutputShouldBeLampOff() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XNOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XNOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xnor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xnor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
    }

    @Test
    fun selectXNORGate_activateAllInput_verifyTheOutputShouldBeLampOn() {
        // Pre-Action
        onView(withId(R.id.til_dropdown)).perform(click())
        onData(anything()).inRoot(isPlatformPopup())
            .atPosition(XNOR_INDEX)
            .perform(click())

        // Arrange
        val gateText = "XNOR Gate"

        onView(withId(R.id.auto_tv_gates)).check(matches(withText(gateText)))
        onView(withId(R.id.til_dropdown)).check(matches(withTextInputHint("Gates")))
        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_PLAY)))
        onView(withId(R.id.til_dropdown)).check(matches(withDrawable(R.drawable.ic_xnor))) // must be checked

        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_OFF)))
        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_OFF)))
        onView(withId(R.id.image_gate)).check(matches(withDrawable(R.drawable.ic_xnor_inp))) // must be checked

        // Action
        onView(withId(R.id.tv_gate_inp1)).perform(click())
        onView(withId(R.id.tv_gate_inp2)).perform(click())
        onView(withId(R.id.image_simulation)).perform(click())

        // Assert
        // for checking toast message, its only work on Android P and below, but won't on Q and above
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            onView(withText(R.string.play_condition))
                .inRoot(RootMatchers.withDecorView(not(mActivityUT.window.decorView)))
                .check(matches(isDisplayed()))
        }

        onView(withId(R.id.image_simulation)).check(matches(withDrawable(IC_STOP)))
        onView(withId(R.id.tv_gate_inp1)).check(matches(withText(SWITCH_ON)))
        onView(withId(R.id.tv_gate_inp2)).check(matches(withText(SWITCH_ON)))

        onView(withId(R.id.image_output)).check(matches(withDrawable(IC_ON)))
    }

    @Suppress("unused")
    companion object {
        private const val SWITCH_ON = "1"
        private const val SWITCH_OFF = "0"

        private const val IC_OFF = R.drawable.ic_lamp_off
        private const val IC_ON = R.drawable.ic_lamp_on
        private const val IC_STOP = R.drawable.ic_stop
        private const val IC_PLAY = R.drawable.ic_play

        private const val OR_INDEX = 1
        private const val NOT_INDEX = 2
        private const val NAND_INDEX = 3
        private const val NOR_INDEX = 4
        private const val XOR_INDEX = 5
        private const val XNOR_INDEX = 6
    }
}
