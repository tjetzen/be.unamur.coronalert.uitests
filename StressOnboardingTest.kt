package de.rki.coronawarnapp.be.unamur.coronalert.uitests

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class StressOnboardingTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<OnboardingActivity> = ActivityScenarioRule(
        OnboardingActivity::class.java
    )

    @Test
    fun test_simulate_randomActions() {

        // arrange + act
        StressTestHelper.simulateRandomActions(50)

        // asserts
        // In case of error, the activity is destroyed and lose its INITIALIZED status.
        Assert.assertTrue(activityScenario.scenario.state.isAtLeast(Lifecycle.State.INITIALIZED))
    }
}
