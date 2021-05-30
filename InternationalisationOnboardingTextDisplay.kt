package de.rki.coronawarnapp.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.junit.Rule
import org.junit.Test

class InternationalisationOnboardingTextDisplay {

    @get: Rule
    val activityScenario: ActivityScenarioRule<OnboardingActivity> = ActivityScenarioRule(
        OnboardingActivity::class.java
    )

    @Test
    fun test_textIsDisplayedCorrectly(){
        onView(withId(R.id.onboarding_headline))
            .check(matches(isCompletelyDisplayed()))
    }
}
