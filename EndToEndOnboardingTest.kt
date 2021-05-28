package de.rki.coronawarnapp.ui

import android.content.Context
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Locale

@RunWith(AndroidJUnit4ClassRunner::class)
class EndToEndOnboardingTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<OnboardingActivity> = ActivityScenarioRule(
        OnboardingActivity::class.java
    )

    @Test
    fun test_onboarding_display_welcome() {
        // arrange
        // act
        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_headline)))
    }

    @Test
    fun test_onboarding_navigate_startPageToPrivacyPage() {
        // arrange
        val nxtBtn: ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // act
        nxtBtn.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_privacy_headline)))
    }

    @Test
    fun test_onboarding_navigate_privacyPageToStartPage() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next))
        nxtBtn1.perform(click())
        val nxtBtn: ViewInteraction = onView(withId(R.id.onboarding_button_back))

        // act
        nxtBtn.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline))
            .check(matches(withText(R.string.onboarding_headline)))
    }

    @Test
    fun test_onboarding_activate_exposureNotificationWithoutWarning() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next))
        nxtBtn1.perform(click())
        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // act
        nxtBtn2.perform(click())
        val activateBtn: ViewInteraction = onView(withId(R.id.onboarding_button_next))

        // assert
        activateBtn.check(matches(isDisplayed()))
    }

    @Test
    fun test_onboarding_changeLanguageOnView_toGerman() {
        // TODO: not working
        // arrange
        var ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        //val intent = Intent(ctx, OnboardingActivity::class.java)

        // act
        ctx.resources.configuration.setLocale(Locale.GERMAN)
        ctx.resources.updateConfiguration(ctx.resources.configuration, ctx.resources.displayMetrics)

        // assert
        onView(withId(R.id.onboarding_headline)).check(matches(withText("Gemeinsam gegen Corona")))
    }

    @Test
    fun test_onboarding_changeLanguageInternal_toGerman() {
        // arrange
        var ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // act
        ctx.resources.configuration.setLocale(Locale.GERMAN)
        ctx.resources.updateConfiguration(ctx.resources.configuration, ctx.resources.displayMetrics)

        // assert
        assertEquals("Gemeinsam gegen Corona", ctx.getString(R.string.onboarding_headline))
    }
}
