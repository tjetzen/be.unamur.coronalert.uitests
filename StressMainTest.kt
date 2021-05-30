package de.rki.coronawarnapp.be.unamur.coronalert.uitests

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.ui.main.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class StressMainTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun test_simulate_randomActions() {

        // arrange + act
        StressTestHelper.simulateRandomActions(50)

        // asserts
        // In case of error, the activity is destroyed and loses its INITIALIZED status.
        Assert.assertTrue(activityScenario.scenario.state.isAtLeast(Lifecycle.State.INITIALIZED))
    }
}
