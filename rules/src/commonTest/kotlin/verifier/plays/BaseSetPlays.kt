package games.lmdbg.rules.verifier.plays

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.set.base.*
import games.lmdbg.rules.verifier.*
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
                    Outcome.LOSS_SCHEME,
                    PlayerCount.FOUR,
                    Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
                    Masterminds.LOKI,
                    setOf(Heroes.HULK, Heroes.BLACK_WIDOW, Heroes.HAWKEYE, Heroes.DEADPOOL, Heroes.SPIDER_MAN),
                    setOf(Villains.ENEMIES_OF_ASGARD, Villains.BROTHERHOOD, Villains.MASTERS_OF_EVIL),
                    setOf(Henchmen.DOOMBOT_LEGION, Henchmen.HAND_NINJAS),
                    setOf(Supports.SHIELD_OFFICER),
                    mapOf(Starters.SHIELD to 4)
                )
            )
        )
    }

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
                MissingRecruitSupport
            ),
            verify(
                Play(
                    Outcome.INCOMPLETE,
                    PlayerCount.FOUR,
                    -1,
                    -1,
                    setOf(),
                    setOf(),
                    setOf(),
                    setOf(),
                    mapOf()
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
                    mapOf(Starters.SHIELD to 1)
                )
            )
        )
    }
}