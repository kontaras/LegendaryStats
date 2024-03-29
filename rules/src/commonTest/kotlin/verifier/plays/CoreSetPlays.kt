package games.lmdbg.rules.verifier.plays

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.set.core.*
import games.lmdbg.rules.verifier.*
import kotlin.test.Test
import kotlin.test.assertContentEquals

/** Verify tests using the core set */
internal class CoreSetPlays {
    @Test
    fun verifyRealPlays() {
        assertContentEquals(
            listOf(),
            verify(
                Play(
                    Outcome.LOSS_SCHEME,
                    PlayerCount.FOUR,
                    Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
                    Masterminds.LOKI,
                    setOf(Heroes.HULK, Heroes.BLACK_WIDOW, Heroes.HAWKEYE, Heroes.DEADPOOL, Heroes.SPIDER_MAN),
                    setOf(Villains.ENEMIES_OF_ASGARD, Villains.BROTHERHOOD, Villains.MASTERS_OF_EVIL),
                    setOf(Henchmen.DOOMBOT_LEGION, Henchmen.HAND_NINJAS),
                    setOf(Supports.SHIELD_OFFICER),
                    mapOf(Starters.SHIELD to 4),
                    Boards.HQ
                )
            )
        )
    }

    @Test
    fun verifyCivilWarSchemeError() {
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.HERO, 3, 2), PlayerSchemeMismatch),
            verify(
                Play(
                    Outcome.LOSS_SCHEME,
                    PlayerCount.ADVANCED_SOLO,
                    Schemes.SUPER_HERO_CIVIL_WAR,
                    Masterminds.MAGNETO,
                    setOf(Heroes.CAPTAIN_AMERICA, Heroes.SPIDER_MAN),
                    setOf(Villains.SKRULLS),
                    setOf(Henchmen.SENTINEL),
                    setOf(Supports.SHIELD_OFFICER),
                    mapOf(Starters.SHIELD to 1),
                    Boards.HQ
                )
            )
        )
    }
}