package games.lmdbg.rules.utils

import games.lmdbg.rules.set.core.*
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
            errorsToString(listOf())
        )
    }

    @Test
    fun singleErrorTest() {
        assertContentEquals(
            listOf("A setup needs to include a recruit granting support."),
            errorsToString(
                listOf(MissingRecruitSupport)
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
                listOf(MissingRecruitSupport, PlayerSchemeMismatch)
            )
        )
    }

    @Test
    fun singleCardSetTest() {
        assertContentEquals(
            listOf("Missing required card sets Iron Man"),
            errorsToString(
                listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.HERO, Heroes.IRON_MAN.id))))
            )
        )
    }

    @Test
    fun multipleCardSetTest() {
        assertContentEquals(
            listOf("Missing required card sets Gambit, Skrulls"),
            errorsToString(
                listOf(
                    MissingRequiredSet(
                        listOf(
                            TypedCardSet(CardSetType.HERO, Heroes.GAMBIT.id),
                            TypedCardSet(CardSetType.VILLAIN, Villains.SKRULLS.id)
                        )
                    )
                )
            )
        )
    }

    @Test
    fun cardSetsToStringTest() {
        assertEquals(
            "", cardSetsToString(
                listOf())
        )

        assertEquals(
            "Loki, Super Hero Civil War, HYDRA, Sentinel, Hulk, S.H.I.E.L.D. Officer, S.H.I.E.L.D., HQ", cardSetsToString(
                listOf(
                    TypedCardSet(CardSetType.MASTERMIND, Masterminds.LOKI.id),
                    TypedCardSet(CardSetType.SCHEME, Schemes.SUPER_HERO_CIVIL_WAR.id),
                    TypedCardSet(CardSetType.VILLAIN, Villains.HYDRA.id),
                    TypedCardSet(CardSetType.HENCHMAN, Henchmen.SENTINEL.id),
                    TypedCardSet(CardSetType.HERO, Heroes.HULK.id),
                    TypedCardSet(CardSetType.SUPPORT, Supports.SHIELD_OFFICER.id),
                    TypedCardSet(CardSetType.STARTER, Starters.SHIELD.id),
                    TypedCardSet(CardSetType.BOARD, Boards.HQ.id)
                )
            )
        )

        assertEquals(
            "mastermind 1, scheme 1, villain 1, henchman 1, hero 1, support 1, starter 1, board 1", cardSetsToString(
                listOf(
                    TypedCardSet(CardSetType.MASTERMIND, 1),
                    TypedCardSet(CardSetType.SCHEME, 1),
                    TypedCardSet(CardSetType.VILLAIN, 1),
                    TypedCardSet(CardSetType.HENCHMAN, 1),
                    TypedCardSet(CardSetType.HERO, 1),
                    TypedCardSet(CardSetType.SUPPORT, 1),
                    TypedCardSet(CardSetType.STARTER, 1),
                    TypedCardSet(CardSetType.BOARD, 1)
                )
            )
        )
    }
}
