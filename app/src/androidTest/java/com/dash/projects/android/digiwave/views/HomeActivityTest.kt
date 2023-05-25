package com.dash.projects.android.digiwave.views

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.utils.TestUtils.atPosition
import com.dash.projects.android.digiwave.views.home.HomeActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class HomeActivityTest {
    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
    }

    @Test
    fun checkAllFeaturesIsSuccessfullyDisplayedAndVerifyEachFeatureNameIsCorrect() {
        var length = 0
        val expectedItemTitle = mapOf(
            0 to Pair("Number System", R.string.numberSystemFeatureDesc),
            1 to Pair("Karnaugh Map", R.string.karnaughMapFeatureDesc),
            2 to Pair("Logic Gate Simulator", R.string.logicGateFeatureDesc),
        )

        onView(withId(R.id.rv_features)).check(matches(isDisplayed()))
        expectedItemTitle.forEach {
            onView(withId(R.id.rv_features))
                .check(matches(atPosition(it.key, hasDescendant(withText(it.value.first)))))
                .check(matches(atPosition(it.key, hasDescendant(withText(it.value.second)))))

            length += 1
        }

        assertEquals(3, length)
    }

    @Test
    fun clickFirstFeature_numberSystemFeature() {
        onView(withId(R.id.rv_features))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
    }

    @Test
    fun clickSecondFeature_karnaughMapFeature() {
        onView(withId(R.id.rv_features))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(1, click()))
    }

    @Test
    fun clickThirdFeature_logicGateSimulatorFeature() {
        onView(withId(R.id.rv_features))
            .check(matches(isDisplayed()))
            .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }
}
