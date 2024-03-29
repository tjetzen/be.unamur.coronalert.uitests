package de.rki.coronawarnapp.be.unamur.coronalert.uitests

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.main.MainActivity
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule
import org.junit.Test

class EndToEndOnboardingTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<OnboardingActivity> = ActivityScenarioRule(
        OnboardingActivity::class.java
    )

    @Test
    fun test_onboarding_display_startPage() {
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
    fun test_onboarding_navigate_privacyPageToActivationPage_withoutExposureNotificationWarning() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // start page
        nxtBtn1.perform(click())
        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // privacy page

        // act
        nxtBtn2.perform(click())

        val warningBtn: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // warning
        warningBtn.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline)).check(matches(withText(R.string.onboarding_tracing_headline)))
    }

    @Test
    fun test_onboarding_navigate_activationPage_activateExposure() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // start page
        nxtBtn1.perform(click())

        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // privacy page
        nxtBtn2.perform(click())

        val warningBtn: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // warning
        warningBtn.perform(click())

        val enableBtn: ViewInteraction =
            onView(withId(R.id.onboarding_button_next)) // activation page
        enableBtn.perform(click())

        // todo : follow instructions to allow activation; cannot be implemented using the self-signed version (which doesn't allow exposure notifications)

        // act

        // assert
        onView(withId(R.id.onboarding_headline)).check(matches(withText(R.string.onboarding_test_headline))) // diagnosed page
    }

    @Test
    fun test_onboarding_navigate_activationPage_deactivateExposure() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // start page
        nxtBtn1.perform(click())

        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // privacy page
        nxtBtn2.perform(click())

        val warningBtn: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // warning
        warningBtn.perform(click())

        val disableBtn: ViewInteraction =
            onView(withId(R.id.onboarding_button_disable)) // activation page
        disableBtn.perform(click())

        val disableBtn2: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // popup

        // act
        disableBtn2.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline)).check(matches(withText(R.string.onboarding_test_headline))) // diagnosed page
    }

    @Test
    fun test_onboarding_navigate_diagnosedPageToWarningPage() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // start page
        nxtBtn1.perform(click())

        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // privacy page
        nxtBtn2.perform(click())

        val warningBtn: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // warning
        warningBtn.perform(click())

        val disableBtn: ViewInteraction =
            onView(withId(R.id.onboarding_button_disable)) // activation page
        disableBtn.perform(click())

        val disableBtn2: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // popup
        disableBtn2.perform(click())

        val nxtBtn3: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // diagnosed page

        // act
        nxtBtn3.perform(click())

        // assert
        onView(withId(R.id.onboarding_headline)).check(matches(withText(R.string.onboarding_notifications_headline))) // warning page
    }

    @Test
    fun test_onboarding_navigate_warningPageToApplicationMainPage() {
        // arrange
        val nxtBtn1: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // start page
        nxtBtn1.perform(click())

        val nxtBtn2: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // privacy page
        nxtBtn2.perform(click())

        val warningBtn: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // warning
        warningBtn.perform(click())

        val disableBtn: ViewInteraction =
            onView(withId(R.id.onboarding_button_disable)) // activation page
        disableBtn.perform(click())

        val disableBtn2: ViewInteraction = onView(
            allOf(
                withId(android.R.id.button1),
                withParent(withParent(withId(R.id.buttonPanel)))
            )
        ) // popup
        disableBtn2.perform(click())

        val nxtBtn3: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // diagnosed page
        nxtBtn3.perform(click())

        val nxtBtn4: ViewInteraction = onView(withId(R.id.onboarding_button_next)) // warning page
        Intents.init()

        // act
        nxtBtn4.perform(click())
        Thread.sleep(2000)

        // todo: test doesn't finish execution

        // assert
        intended(hasComponent(MainActivity::class.java.getName()))
    }
}
