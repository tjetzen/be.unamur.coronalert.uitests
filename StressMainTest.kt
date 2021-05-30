package de.rki.coronawarnapp.be.unamur.coronalert.uitests

import android.content.Context
import android.content.Context.WINDOW_SERVICE
import android.util.DisplayMetrics
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import de.rki.coronawarnapp.ui.main.MainActivity
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

class StressMainTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(
        MainActivity::class.java
    )

    @Test
    fun test_simulate_randomActions() {

        // arrange
        val context: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val windowManager = context.getSystemService(WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        val screenWidth = displayMetrics.widthPixels
        val screenHeight = displayMetrics.heightPixels

        // act
        var i = 0
        var randomX1: Int
        var randomX2: Int
        var randomY1: Int
        var randomY2: Int
        var isClick: Int
        while (i < 50) {
            randomX1 = Random.nextInt(0, screenWidth)
            randomX2 = Random.nextInt(0, screenWidth)
            randomY1 = Random.nextInt(0, screenHeight)
            randomY2 = Random.nextInt(0, screenHeight)
            isClick = Random.nextInt(0, 2) // 1 = click ; 0 = swipe

            if (isClick == 1) { // Simulate click at random position
                Runtime.getRuntime()
                    .exec("input tap $randomX1 $randomY1")
                Log.i("STRESS TEST", "[$i] CLICK ($randomX1, $randomY1)")

            } else { // Simulate random swipe
                Runtime.getRuntime()
                    .exec("input swipe $randomX1 $randomY1 $randomX2 $randomY2")
                Log.i("STRESS TEST", "[$i] SWIPE ($randomX1, $randomY1) to ($randomX2, $randomY2)")
            }
            i += 1
            Thread.sleep(250) // Wait few ms
        }

        // asserts
        Assert.assertTrue(activityScenario.scenario.state.isAtLeast(Lifecycle.State.INITIALIZED)) // In case of error, the activity is destroyed and lose its INITIALIZED status.
    }
}
