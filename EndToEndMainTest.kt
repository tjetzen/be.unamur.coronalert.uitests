package de.rki.coronawarnapp.ui


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import de.rki.coronawarnapp.R
import de.rki.coronawarnapp.ui.main.MainActivity
import de.rki.coronawarnapp.ui.onboarding.OnboardingActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.joda.time.DateTime
import org.junit.Rule
import org.junit.Test
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class EndToEndMainTest {

    @get: Rule
    val activityScenario: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(
        MainActivity::class.java
    )

    private fun handle_popup_tapButton1(){
        val btn: ViewInteraction = onView(allOf(withId(android.R.id.button1), withParent(withParent(withId(R.id.buttonPanel))))) // popup
        btn.perform(click())
    }

    private fun handle_popup_tapButton2(){
        val btn: ViewInteraction = onView(allOf(withId(android.R.id.button2), withParent(withParent(withId(R.id.buttonPanel))))) // popup
        btn.perform(click())
    }

    private fun handle_popup_tapYes() {
        handle_popup_tapButton1()
    }

    private fun handle_popup_tapNo(){
        handle_popup_tapButton2()
    }

    private fun handle_popup_tapOk() {
        handle_popup_tapButton1()
    }

    private fun handle_popup_tapReset(){
        handle_popup_tapButton1()
    }

    @Test
    fun test_generateCode_generatesValidDate(){
        // arrange
        Thread.sleep(2000)
        handle_popup_tapOk(); handle_popup_tapOk()

        val receiveTestBtn: ViewInteraction = onView(withId(R.id.submission_status_card_unregistered_button)) // main page
        receiveTestBtn.perform(scrollTo(), click())

        val nxtBtn: ViewInteraction = onView(withId(R.id.submission_intro_button_next)) // receive test page
        nxtBtn.perform(click())

        val expectedDate:String = SimpleDateFormat("EEEE, d MMM y").format(DateTime().minusDays(2).toDate())

        // act
        handle_popup_tapNo() // popup symptoms checking

        //assert
        onView(withId(R.id.submission_test_request_save_date)).check(matches(withText(expectedDate)))
    }

    @Test
    fun test_reinitialization(){
        // arrange
        Intents.init()

        // act
        resetApplicationStateAtMainPage()

        // assert
        Intents.intended(IntentMatchers.hasComponent(OnboardingActivity::class.java.getName()))
    }

    private fun resetApplicationStateAtMainPage(){
        // handle popup if exposure notifications are not activated
        Thread.sleep(2000)
        handle_popup_tapOk();handle_popup_tapOk()

        val menuBtn = onView(withId(R.id.main_header_options_menu))
        menuBtn.perform(scrollTo(), click())

        val menuItem = onView(allOf(withId(android.R.id.title), withText("Settings")))
        menuItem.perform(click())

        // handle popup if exposure notifications are not activated
        handle_popup_tapOk()

        val resetBtn = onView(withId(R.id.settings_reset))
        resetBtn.perform(scrollTo(), click())

        val resetConfirmBtn = onView(withId(R.id.settings_reset_button_delete))
        resetConfirmBtn.perform(click())

        handle_popup_tapReset()
    }

}
