package de.rki.coronawarnapp.ui


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withContentDescription
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withParent
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import de.rki.coronawarnapp.R
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EndToEndLauncherTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(LauncherActivity::class.java)

    @Test
    fun launcherActivityTestPopup() {
        // Added a sleep statement to match the app's execution delay.
        // The recommended way to handle such scenarios is to use Espresso idling resources:
        // https://google.github.io/android-testing-support-library/docs/espresso/idling-resource/index.html
        Thread.sleep(7000)

        val appCompatButton = onView(
            allOf(
                withId(R.id.onboarding_button_next), withText("Let’s Get Started"),
                childAtPosition(
                    allOf(
                        withId(R.id.onboarding_container),
                        withContentDescription("Onboarding page 1 of 5: Fighting coronavirus together"),
                        childAtPosition(
                            withId(R.id.onboarding_fragment_container),
                            0
                        )
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        appCompatButton.perform(click())

        val appCompatButton2 = onView(
            allOf(
                withId(R.id.onboarding_button_next), withText("Next"),
                childAtPosition(
                    allOf(
                        withId(R.id.onboarding_privacy_container),
                        withContentDescription("Onboarding page 2 of 5: Data Privacy. A long text follows. To proceed at any time, use the button at the bottom of the screen."),
                        childAtPosition(
                            withId(R.id.onboarding_fragment_container),
                            0
                        )
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        appCompatButton2.perform(click())

        val textView = onView(
            allOf(
                withId(R.id.alertTitle), withText("CAUSE: 3\nSomething went wrong."),
                withParent(
                    allOf(
                        withId(R.id.title_template),
                        withParent(withId(R.id.topPanel))
                    )
                ),
                isDisplayed()
            )
        )
        textView.check(matches(withText("CAUSE: 3 Something went wrong.")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
