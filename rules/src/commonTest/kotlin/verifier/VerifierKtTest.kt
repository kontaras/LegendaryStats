package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.set.base.Masterminds
import games.lmdbg.rules.set.base.Schemes
import games.lmdbg.rules.set.base.Heroes
import games.lmdbg.rules.set.base.Henchmen
import games.lmdbg.rules.set.base.Villains
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

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
            henchmen
        )

        assertContentEquals(listOf(), verify(testPlay))

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
    fun checkValuesInRangeTest() {
        val plugins = setOf(object : ReleaseRulesPlugin {
            override val heroesRange: IntRange = 1..2
        })

        assertContentEquals(listOf(), runWithPlugins(setOf()) { checkValuesInRange(playMaker())})
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker())})

        assertContentEquals(
            listOf(InvalidCardSet("hero", -7)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = -7)) })

    }

    private fun <T> runWithPlugins(newPlugins: Set<ReleaseRulesPlugin>, func: () -> T): T {
        val realPlugins = plugins.toSet()
        plugins.clear()
        plugins.addAll(newPlugins)
        try {
            return func()
        } finally {
            plugins.clear()
            plugins.addAll(realPlugins)
        }
    }

    private fun playMaker(
        hero: Int? = null,
        villain: Int? = null,
        henchman: Int? = null,
        mastermind: Int? = null,
        scheme: Int? = null
    ): Play {
        return Play(
            Outcome.WIN,
            PlayerCount.THREE,
            scheme ?: 1,
            mastermind ?: 1,
            if (hero != null) setOf(hero) else setOf(),
            if (villain != null) setOf(villain) else setOf(),
            if (henchman != null) setOf(henchman) else setOf()
        )
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
