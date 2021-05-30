package de.rki.coronawarnapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.main.MainActivity
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

class InternationalisationMainTextDisplay {

    @get: Rule
    val activityScenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(
        MainActivity::class.java
    )

    private fun handle_popup_tapButton1(){
        val btn: ViewInteraction = onView(
            Matchers.allOf(
                withId(android.R.id.button1),
                ViewMatchers.withParent(ViewMatchers.withParent(withId(R.id.buttonPanel)))
            )
        ) // popup
        btn.perform(ViewActions.click())
    }

    @Test
    fun test_textIsDisplayedCorrectly(){
        Thread.sleep(2000)
        handle_popup_tapButton1()
        handle_popup_tapButton1()
        onView(withId(R.id.main_tracing_headline))
            .check(matches(isCompletelyDisplayed()))
    }
}
