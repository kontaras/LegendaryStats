package kon.foo.verifier

import kon.foo.set.base.Heroes
import kon.foo.set.base.Villains
import kon.foo.set.base.Henchmen
import kon.foo.set.base.Schemes
import kon.foo.set.base.Masterminds
import kon.foo.model.Outcome
import kon.foo.model.Play
import kon.foo.model.PlayerCount
import kon.foo.verifier.SetCounts
import kon.foo.verifier.WrongHeroCount
import kon.foo.verifier.getPlayerCountRules
import kon.foo.verifier.verify
import kotlin.test.assertEquals
import kotlin.test.Test

internal class VerifierKtTest {

    @Test
    fun verifyCardSetCounts() {
        val heroes: MutableSet<Int> =
            mutableSetOf(Heroes.BLACK_WIDOW, Heroes.CAPTAIN_AMERICA, Heroes.CYCLOPS, Heroes.IRON_MAN, Heroes.GAMBIT)
        val villains: MutableSet<Int> = mutableSetOf(Villains.ENEMIES_OF_ASGARD, Villains.BROTHERHOOD)
        val henchmen: MutableSet<Int> = mutableSetOf(Henchmen.SENTINEL)
        val testPlay = Play(
            Outcome.DRAW,
            PlayerCount.TWO,
            Schemes.THE_LEGACY_VIRUS,
            Masterminds.LOKI,
            heroes,
            villains,
            henchmen,
            null
        );

        heroes.remove(Heroes.GAMBIT)
        assertEquals(listOf(WrongHeroCount(5, 4)), verify(testPlay))

        heroes.add(Heroes.GAMBIT)
        heroes.add(Heroes.DEADPOOL)
        assertEquals(listOf(WrongHeroCount(5, 6)), verify(testPlay))
    }

    @Test
    fun getPlayerCountRulesTest() {
        assertEquals(SetCounts(3, 1, 1), getPlayerCountRules(PlayerCount.SOLO))
        assertEquals(SetCounts(3, 1, 1), getPlayerCountRules(PlayerCount.ADVANCED))
        assertEquals(SetCounts(5, 2, 1), getPlayerCountRules(PlayerCount.TWO))
        assertEquals(SetCounts(5, 3, 1), getPlayerCountRules(PlayerCount.THREE))
        assertEquals(SetCounts(5, 3, 2), getPlayerCountRules(PlayerCount.FOUR))
        assertEquals(SetCounts(6, 4, 2), getPlayerCountRules(PlayerCount.FIVE))
    }
}