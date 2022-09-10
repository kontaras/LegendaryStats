package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.*

import kotlin.test.assertEquals
import kotlin.test.Test

internal class DisplayTest {
    @Test
    fun testEmpty() {
        assertEquals("", errorsToString(listOf()))
    }

    @Test
    fun testSingleError() {
        assertEquals("A setup needs to include a recruit granting support.",
            errorsToString(listOf(MissingRecruitSupport)))
    }

    @Test
    fun testMultipleErrors() {
        assertEquals("A setup needs to include a recruit granting support.\n" +
            "The scheme you selected is not playable with the selected player count.",
            errorsToString(listOf(MissingRecruitSupport, PlayerSchemeMismatch)))
    }
}