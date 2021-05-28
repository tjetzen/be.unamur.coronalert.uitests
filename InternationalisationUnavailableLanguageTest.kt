package de.rki.coronawarnapp.ui

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
class InternationalisationUnavailableLanguageTest(val locale: Locale) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters
        fun data(): MutableCollection<Array<Any>> {
            val data = arrayOf(
                arrayOf<Any>(Locale("zh", "CN")), // Chinese
                arrayOf<Any>(Locale("es", "ES")), // Spanish
                arrayOf<Any>(Locale("it", "IT")), // Italian
                arrayOf<Any>(Locale("pt", "PT")), // Portuguese
                arrayOf<Any>(Locale("ar", "AE")), // Arabic
                arrayOf<Any>(Locale("pl", "PL")), // Polish
                arrayOf<Any>(Locale("ro", "RO")), // Romanian
                arrayOf<Any>(Locale("tr", "TR")), // Turkish
            )
            return Arrays.asList(*data)
        }
    }

    @Test
    fun test_unavailableLanguageToDefaultEnglish() {
        // arrange
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext

        // act
        ctx.resources.configuration.setLocale(locale)
        ctx.resources.updateConfiguration(ctx.resources.configuration, ctx.resources.displayMetrics)

        // assert
        assertEquals(
            "Let's fight coronavirus together",
            ctx.getString(R.string.onboarding_headline)
        )
    }
}
