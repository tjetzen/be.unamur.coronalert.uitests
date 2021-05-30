package de.rki.coronawarnapp.be.unamur.coronalert.uitests

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import de.rki.coronawarnapp.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import java.util.Arrays
import java.util.Locale

@RunWith(Parameterized::class)
class InternationalisationAvailableLanguageTest(val locale: Locale, val expectedText: String) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): MutableCollection<Array<Any>> {
            val data = arrayOf(
                arrayOf<Any>(Locale("en", "UK"), "Let's fight coronavirus together"),
                arrayOf<Any>(Locale("fr", "BE"), "Luttons ensemble contre le coronavirus !"),
                arrayOf<Any>(Locale("de", "DE"), "Gemeinsam gegen Corona"),
                arrayOf<Any>(Locale("nl", "BE"), "Samen krijgen we het coronavirus klein")
            )
            return Arrays.asList(*data)
        }
    }

    @Test
    fun test_availableLanguage() {
        // arrange
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // act
        ctx.resources.configuration.setLocale(locale)
        ctx.resources.updateConfiguration(ctx.resources.configuration, ctx.resources.displayMetrics)

        // assert
        assertEquals(expectedText, ctx.getString(R.string.onboarding_headline))
    }
}
