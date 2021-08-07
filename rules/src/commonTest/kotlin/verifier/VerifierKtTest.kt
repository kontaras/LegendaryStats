package kon.foo.verifier

import kon.foo.set.base.Heroes
import kon.foo.set.base.Villains
import kon.foo.set.base.Henchmen
import kon.foo.set.base.Schemes
import kon.foo.set.base.Masterminds
import kon.foo.model.Outcome
import kon.foo.model.Play
import kon.foo.model.PlayerCount
import kotlin.test.assertEquals
import kotlin.test.Test
import kotlin.test.assertContentEquals

internal class VerifierKtTest {

    @Test
    fun verifyCardSetCounts() {
        val heroes: MutableSet<Int> =
            mutableSetOf(Heroes.BLACK_WIDOW, Heroes.CAPTAIN_AMERICA, Heroes.CYCLOPS, Heroes.IRON_MAN, Heroes.GAMBIT)
        val villains: MutableSet<Int> = mutableSetOf(Villains.ENEMIES_OF_ASGARD, Villains.BROTHERHOOD, Villains.HYDRA)
        val henchmen: MutableSet<Int> = mutableSetOf(Henchmen.SENTINEL, Henchmen.DOOMBOT_LEGION)
        val testPlay = Play(
            Outcome.DRAW,
            PlayerCount.FOUR,
            Schemes.THE_LEGACY_VIRUS,
            Masterminds.LOKI,
            heroes,
            villains,
            henchmen,
            null
        )

        heroes.remove(Heroes.GAMBIT)
        assertContentEquals(listOf(WrongSetCount("hero", 5, 4)), verify(testPlay))
        heroes.add(Heroes.GAMBIT)

        heroes.add(Heroes.DEADPOOL)
        assertContentEquals(listOf(WrongSetCount("hero", 5, 6)), verify(testPlay))
        heroes.remove(Heroes.GAMBIT)

        villains.remove(Villains.ENEMIES_OF_ASGARD)
        assertContentEquals(listOf(WrongSetCount("villain", 3, 2)), verify(testPlay))
        villains.add(Villains.ENEMIES_OF_ASGARD)

        villains.add(Villains.MASTERS_OF_EVIL)
        assertContentEquals(listOf(WrongSetCount("villain", 3, 4)), verify(testPlay))
        villains.remove(Villains.ENEMIES_OF_ASGARD)

        henchmen.remove(Henchmen.DOOMBOT_LEGION)
        assertContentEquals(listOf(WrongSetCount("henchman", 2, 1)), verify(testPlay))
        henchmen.add(Henchmen.DOOMBOT_LEGION)

        henchmen.add(Henchmen.HAND_NINJAS)
        assertContentEquals(listOf(WrongSetCount("henchman", 2, 3)), verify(testPlay))
        henchmen.remove(Henchmen.DOOMBOT_LEGION)
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
