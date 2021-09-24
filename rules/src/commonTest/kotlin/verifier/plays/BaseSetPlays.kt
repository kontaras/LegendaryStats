package games.lmdbg.rules.verifier.plays

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.set.base.*
import games.lmdbg.rules.verifier.InvalidCardSet
import games.lmdbg.rules.verifier.WrongSetCount
import games.lmdbg.rules.verifier.verify
import kotlin.test.Test
import kotlin.test.assertContentEquals

/** Verify tests using the base set */
internal class BaseSetPlays {
    @Test
    fun verifyRealPlays() {
        assertContentEquals(
            listOf(),
            verify(
                Play(
                    Outcome.WIN_DEFEAT_MASTERMIND,
                    PlayerCount.FOUR,
                    Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
                    Masterminds.LOKI,
                    setOf(Heroes.HULK, Heroes.BLACK_WIDOW, Heroes.HAWKEYE, Heroes.DEADPOOL, Heroes.SPIDER_MAN),
                    setOf(Villains.ENEMIES_OF_ASGARD, Villains.BROTHERHOOD, Villains.MASTERS_OF_EVIL),
                    setOf(Henchmen.DOOMBOT_LEGION, Henchmen.HAND_NINJAS)
                )
            )
        )
    }

    @Test
    fun verifyEmptyPlay() {
        assertContentEquals(
            listOf(
                WrongSetCount("hero", 5, 0),
                WrongSetCount("villain", 3, 0),
                WrongSetCount("henchman", 2, 0),
                InvalidCardSet("scheme", -1),
                InvalidCardSet("mastermind", -1)
            ),
            verify(
                Play(
                    Outcome.WIN_DEFEAT_MASTERMIND,
                    PlayerCount.FOUR,
                    -1,
                    -1,
                    setOf(),
                    setOf(),
                    setOf()
                )
            )
        )
    }
}