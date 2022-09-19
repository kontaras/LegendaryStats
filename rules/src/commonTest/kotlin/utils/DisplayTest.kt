package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.*
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.CardSetType

import kotlin.test.assertContentEquals
import kotlin.test.Test

internal class DisplayTest {
    @Test
    fun testEmpty() {
        assertContentEquals(listOf(), errorsToString(listOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf()))
    }

    @Test
    fun testSingleError() {
        assertContentEquals(listOf("A setup needs to include a recruit granting support."),
            errorsToString(listOf(MissingRecruitSupport), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf()))
    }

    @Test
    fun testMultipleErrors() {
        assertContentEquals(listOf("A setup needs to include a recruit granting support.",
            "The scheme you selected is not playable with the selected player count."),
            errorsToString(listOf(MissingRecruitSupport, PlayerSchemeMismatch), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf()))
    }

    @Test
    fun testSingleCardSet() {
        assertContentEquals(listOf("Missing required card sets Hero Man"),
            errorsToString(listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.HERO, 1)))), mapOf(1 to "Hero Man", 2 to "Bob"), mapOf(1 to "Bad Man"), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf()))
    }

    @Test
    fun testMultipleCardSet() {
        assertContentEquals(listOf("Missing required card sets Hero Man, Bad Man"),
            errorsToString(listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.HERO, 1), TypedCardSet(CardSetType.HENCHMAN, 1)))), mapOf(1 to "Hero Man", 2 to "Bob"), mapOf(1 to "Bad Man"), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf()))
    }
}