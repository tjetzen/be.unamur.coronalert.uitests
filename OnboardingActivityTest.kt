package de.rki.coronawarnapp.ui


import android.content.Context
import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.hamcrest.Matchers.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class OnboardingActivityTest {

    @get: Rule
    val activityScenario : ActivityScenarioRule<OnboardingActivity> = ActivityScenarioRule(OnboardingActivity::class.java)

    @Test
    fun test_onboarding_display_welcome() {
        // arrange
        // act
        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_headline)))
    }

    @Test
    fun test_onboarding_navigate_startPageToPrivacyPage(){
        // arrange
        val nxtBtn:ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // act
        nxtBtn.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_privacy_headline)))
    }

    @Test
    fun test_onboarding_navigate_privacyPageToStartPage(){
        // arrange
        val nxtBtn1:ViewInteraction = onView(withId(R.id.onboarding_button_next))
        nxtBtn1.perform(click())
        val nxtBtn:ViewInteraction = onView(withId(R.id.onboarding_button_back))

        // act
        nxtBtn.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_headline)))
    }

    @Test
    fun test_onboarding_activate_exposureNotificationWithoutWarning() {
        // arrange
        val nxtBtn1:ViewInteraction = onView(withId(R.id.onboarding_button_next))
        nxtBtn1.perform(click())
        val nxtBtn2:ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // act
        nxtBtn2.perform(click())
        val activateBtn:ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // assert
        activateBtn.check(matches(isDisplayed()))
    }

}
