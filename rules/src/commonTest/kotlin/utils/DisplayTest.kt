package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.*
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.CardSetType

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.Test

internal class DisplayTest {

    /**
     * Test displaying an empty list of errors
     */
    @Test
    fun emptyTest() {
        assertContentEquals(
            listOf(),
            errorsToString(listOf(), mapOf())
        )
    }

    @Test
    fun singleErrorTest() {
        assertContentEquals(
            listOf("A setup needs to include a recruit granting support."),
            errorsToString(
                listOf(MissingRecruitSupport),
                mapOf()
            )
        )
    }

    @Test
    fun multipleErrorsTest() {
        assertContentEquals(
            listOf(
                "A setup needs to include a recruit granting support.",
                "The scheme you selected is not playable with the selected player count."
            ),
            errorsToString(
                listOf(MissingRecruitSupport, PlayerSchemeMismatch),
                mapOf()
            )
        )
    }

    @Test
    fun singleCardSetTest() {
        assertContentEquals(
            listOf("Missing required card sets Hero Man"),
            errorsToString(
                listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.HERO, 1)))),
                mapOf(CardSetType.HERO.toString() to mapOf(1 to "Hero Man", 2 to "Bob"),
                    CardSetType.HENCHMAN.toString() to mapOf(1 to "Bad Man"))
            )
        )
    }

    @Test
    fun multipleCardSetTest() {
        assertContentEquals(
            listOf("Missing required card sets Hero Man, Bad Man"),
            errorsToString(
                listOf(
                    MissingRequiredSet(
                        listOf(
                            TypedCardSet(CardSetType.HERO, 1),
                            TypedCardSet(CardSetType.HENCHMAN, 1)
                        )
                    )
                ),
                mapOf(CardSetType.HERO.toString() to mapOf(1 to "Hero Man", 2 to "Bob"),
                    CardSetType.HENCHMAN.toString() to mapOf(1 to "Bad Man"))
            )
        )
    }

    @Test
    fun cardSetsToStringTest() {
        assertEquals(
            "", cardSetsToString(
                listOf(),
                mapOf(CardSetType.HERO.toString() to mapOf(1 to "Hero Man", 2 to "Bob"),
                    CardSetType.HENCHMAN.toString() to mapOf(1 to "Hench Man"),
                    CardSetType.VILLAIN.toString() to mapOf(1 to "Villain Man"),
                    CardSetType.STARTER.toString() to mapOf(1 to "Starter Man"),
                    CardSetType.SUPPORT.toString() to mapOf(1 to "Support Man"),
                    CardSetType.SCHEME.toString() to mapOf(1 to "Scheme Man"),
                    CardSetType.MASTERMIND.toString() to mapOf(1 to "Mastermind Man"),
                    CardSetType.BOARD.toString() to mapOf(1 to "Board Man"))
            )
        )

        assertEquals(
            "Mastermind Man, Scheme Man, Villain Man, Hench Man, Hero Man, Support Man, Starter Man, Board Man", cardSetsToString(
                listOf(
                    TypedCardSet(CardSetType.MASTERMIND, 1),
                    TypedCardSet(CardSetType.SCHEME, 1),
                    TypedCardSet(CardSetType.VILLAIN, 1),
                    TypedCardSet(CardSetType.HENCHMAN, 1),
                    TypedCardSet(CardSetType.HERO, 1),
                    TypedCardSet(CardSetType.SUPPORT, 1),
                    TypedCardSet(CardSetType.STARTER, 1),
                    TypedCardSet(CardSetType.BOARD, 1)
                ), mapOf(CardSetType.HERO.toString() to mapOf(1 to "Hero Man", 2 to "Bob"),
                    CardSetType.HENCHMAN.toString() to mapOf(1 to "Hench Man"),
                    CardSetType.VILLAIN.toString() to mapOf(1 to "Villain Man"),
                    CardSetType.STARTER.toString() to mapOf(1 to "Starter Man"),
                    CardSetType.SUPPORT.toString() to mapOf(1 to "Support Man"),
                    CardSetType.SCHEME.toString() to mapOf(1 to "Scheme Man"),
                    CardSetType.MASTERMIND.toString() to mapOf(1 to "Mastermind Man"),
                    CardSetType.BOARD.toString() to mapOf(1 to "Board Man"))
            )
        )

        assertEquals(
            "", cardSetsToString(
                listOf(),
                mapOf()
            )
        )

        assertEquals(
            "MASTERMIND 1, SCHEME 1, VILLAIN 1, HENCHMAN 1, HERO 1, SUPPORT 1, STARTER 1, BOARD 1", cardSetsToString(
                listOf(
                    TypedCardSet(CardSetType.MASTERMIND, 1),
                    TypedCardSet(CardSetType.SCHEME, 1),
                    TypedCardSet(CardSetType.VILLAIN, 1),
                    TypedCardSet(CardSetType.HENCHMAN, 1),
                    TypedCardSet(CardSetType.HERO, 1),
                    TypedCardSet(CardSetType.SUPPORT, 1),
                    TypedCardSet(CardSetType.STARTER, 1),
                    TypedCardSet(CardSetType.BOARD, 1)
                ),
                mapOf()
            )
        )
    }
}
