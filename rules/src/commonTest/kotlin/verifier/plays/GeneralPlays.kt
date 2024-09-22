package games.lmdbg.rules.verifier.plays

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.verifier.*
import kotlin.test.Test
import kotlin.test.assertContentEquals

/**
 * Tests for plays that handle generic plays and not contents from any set
 */
internal class GeneralPlays {
    @Test
    fun verifyEmptyPlay() {
        assertContentEquals(
            listOf(
                WrongSetCount(CardSetType.HERO, 5, 0),
                WrongSetCount(CardSetType.VILLAIN, 3, 0),
                WrongSetCount(CardSetType.HENCHMAN, 2, 0),
                WrongSetCount(CardSetType.STARTER, 4, 0),
                InvalidCardSet(CardSetType.SCHEME, -1),
                InvalidCardSet(CardSetType.MASTERMIND, -1),
                InvalidCardSet(CardSetType.BOARD, -1),
                MissingRecruitSupport
            ),
            verify(
                Play(
                    Outcome.DRAW_DECK,
                    PlayerCount.FOUR,
                    -1,
                    -1,
                    mutableSetOf(),
                    mutableSetOf(),
                    mutableSetOf(),
                    mutableSetOf(),
                    mutableMapOf(),
                    -1
                )
            )
        )
    }

    @Test
    fun duplicateItemsInSet() {
        val play = "{\n" +
                "  \"scheme\": 108,\n" +
                "  \"mastermind\": 109,\n" +
                "  \"supports\": [101],\n" +
                "  \"starters\": {101: 2, 101: 2},\n" +
                "  \"heroes\": [\n" +
                "    101,\n" +
                "    102,\n" +
                "    103,\n" +
                "    105,\n" +
                "    105\n" +
                "  ],\n" +
                "  \"villains\": [\n" +
                "    110,\n" +
                "    108,\n" +
                "    108\n" +
                "  ],\n" +
                "  \"henchmen\": [\n" +
                "    101,\n" +
                "    101\n" +
                "  ],\n" +
                "  \"outcome\": \"WIN_DEFEAT_MASTERMIND\",\n" +
                "  \"players\": \"FOUR\"\n" +
                "  \"board\": \"101\"\n" +
                "}"

        assertContentEquals(
            listOf(
                WrongSetCount(CardSetType.HERO, 5, 4),
                WrongSetCount(CardSetType.VILLAIN, 3, 2),
                WrongSetCount(CardSetType.HENCHMAN, 2, 1),
                WrongSetCount(CardSetType.STARTER, 4, 2),
            ), verify(Play.playFromString(play))
        )
    }
}