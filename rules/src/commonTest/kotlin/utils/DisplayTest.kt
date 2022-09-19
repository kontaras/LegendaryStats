package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.*
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.CardSetType

import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.Test

internal class DisplayTest {
    @Test
    fun emptyTest() {
        assertContentEquals(
            listOf(),
            errorsToString(listOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf(), mapOf())
        )
    }

    @Test
    fun singleErrorTest() {
        assertContentEquals(
            listOf("A setup needs to include a recruit granting support."),
            errorsToString(
                listOf(MissingRecruitSupport),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
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
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
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
                mapOf(1 to "Hero Man", 2 to "Bob"),
                mapOf(1 to "Bad Man"),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf()
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
                mapOf(1 to "Hero Man", 2 to "Bob"),
                mapOf(1 to "Bad Man"),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf()
            )
        )
    }

    @Test
    fun cardSetsToStringTest() {
        assertEquals(
            "", cardSetsToString(
                listOf(),
                mapOf(1 to "Hero Man", 2 to "Bob"),
                mapOf(1 to "Hench Man"),
                mapOf(1 to "Villain Man"),
                mapOf(1 to "Starter Man"),
                mapOf(1 to "Support Man"),
                mapOf(1 to "Scheme Man"),
                mapOf(1 to "Mastermind Man"),
                mapOf(1 to "Board Man")
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
                ),
                mapOf(1 to "Hero Man", 2 to "Bob"),
                mapOf(1 to "Hench Man"),
                mapOf(1 to "Villain Man"),
                mapOf(1 to "Starter Man"),
                mapOf(1 to "Support Man"),
                mapOf(1 to "Scheme Man"),
                mapOf(1 to "Mastermind Man"),
                mapOf(1 to "Board Man")
            )
        )

        assertEquals(
            "", cardSetsToString(
                listOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
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
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf(),
                mapOf()
            )
        )
    }
}
