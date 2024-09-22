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
                    Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS.id,
                    Masterminds.LOKI.id,
                    mutableSetOf(Heroes.HULK.id, Heroes.BLACK_WIDOW.id, Heroes.HAWKEYE.id, Heroes.DEADPOOL.id, Heroes.SPIDER_MAN.id),
                    mutableSetOf(Villains.ENEMIES_OF_ASGARD.id, Villains.BROTHERHOOD.id, Villains.MASTERS_OF_EVIL.id),
                    mutableSetOf(Henchmen.DOOMBOT_LEGION.id, Henchmen.HAND_NINJAS.id),
                    mutableSetOf(Supports.SHIELD_OFFICER.id),
                    mutableMapOf(Starters.SHIELD.id to 4),
                    Boards.HQ.id
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
                    Schemes.SUPER_HERO_CIVIL_WAR.id,
                    Masterminds.MAGNETO.id,
                    mutableSetOf(Heroes.CAPTAIN_AMERICA.id, Heroes.SPIDER_MAN.id),
                    mutableSetOf(Villains.SKRULLS.id),
                    mutableSetOf(Henchmen.SENTINEL.id),
                    mutableSetOf(Supports.SHIELD_OFFICER.id),
                    mutableMapOf(Starters.SHIELD.id to 1),
                    Boards.HQ.id
                )
            )
        )
    }
}