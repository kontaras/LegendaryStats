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

        val counts = getPlayerCountRules(PlayerCount.FOUR)

        assertContentEquals(listOf(), checkCardSetSizes(testPlay, counts))

        heroes.remove(Heroes.GAMBIT)
        assertContentEquals(listOf(WrongSetCount("hero", 5, 4)),
            checkCardSetSizes(testPlay, counts))
        heroes.add(Heroes.GAMBIT)

        heroes.add(Heroes.DEADPOOL)
        assertContentEquals(listOf(WrongSetCount("hero", 5, 6)),
            checkCardSetSizes(testPlay, counts))
        heroes.remove(Heroes.GAMBIT)

        villains.remove(Villains.ENEMIES_OF_ASGARD)
        assertContentEquals(listOf(WrongSetCount("villain", 3, 2)),
            checkCardSetSizes(testPlay, counts))
        villains.add(Villains.ENEMIES_OF_ASGARD)

        villains.add(Villains.MASTERS_OF_EVIL)
        assertContentEquals(listOf(WrongSetCount("villain", 3, 4)),
            checkCardSetSizes(testPlay, counts))
        villains.remove(Villains.ENEMIES_OF_ASGARD)

        henchmen.remove(Henchmen.DOOMBOT_LEGION)
        assertContentEquals(listOf(WrongSetCount("henchman", 2, 1)),
            checkCardSetSizes(testPlay, counts))
        henchmen.add(Henchmen.DOOMBOT_LEGION)

        henchmen.add(Henchmen.HAND_NINJAS)
        assertContentEquals(listOf(WrongSetCount("henchman", 2, 3)),
            checkCardSetSizes(testPlay, counts))
        henchmen.remove(Henchmen.DOOMBOT_LEGION)
    }

    @Test
    fun checkValuesInRangeTest() {
        val plugins = setOf(object : ReleaseRulesPlugin {
            override val heroesRange: IntRange = IntRange.EMPTY
            override val villainsRange: IntRange = IntRange.EMPTY
            override val henchmenRange: IntRange = IntRange.EMPTY
            override val schemesRange: IntRange = 0..0
            override val mastermindRange: IntRange = 0..0
            override fun updateSetCountsFromScheme(scheme: Int, setCounts: SetCounts) {}
        }, object : ReleaseRulesPlugin {
            override val heroesRange: IntRange = 1..2
            override val villainsRange: IntRange = 101..102
            override val henchmenRange: IntRange = 201..202
            override val schemesRange: IntRange = 301..302
            override val mastermindRange: IntRange = 401..402
            override fun updateSetCountsFromScheme(scheme: Int, setCounts: SetCounts) {}
        }, object : ReleaseRulesPlugin {
            override val heroesRange: IntRange = 5..7
            override val villainsRange: IntRange = 105..107
            override val henchmenRange: IntRange = 205..207
            override val schemesRange: IntRange = 305..307
            override val mastermindRange: IntRange = 405..407
            override fun updateSetCountsFromScheme(scheme: Int, setCounts: SetCounts) {}
        })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker()) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = 1)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = 7)) })
        assertContentEquals(
            listOf(InvalidCardSet("hero", -7)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = -7)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = 101)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = 107)) })
        assertContentEquals(
            listOf(InvalidCardSet("villain", -6)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = -6)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = 201)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = 207)) })
        assertContentEquals(
            listOf(InvalidCardSet("henchman", -34)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = -34)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = 301)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = 307)) })
        assertContentEquals(
            listOf(InvalidCardSet("scheme", -123)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = -123)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = 401)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = 407)) })
        assertContentEquals(
            listOf(InvalidCardSet("mastermind", -128)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = -128)) })

        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) {
                checkValuesInRange(
                    playMaker(
                        hero = 7,
                        villain = 101,
                        henchman = 207,
                        scheme = 307,
                        mastermind = 407
                    )
                )
            })
        assertContentEquals(
            listOf(
                InvalidCardSet("hero", -7),
                InvalidCardSet("villain", -6),
                InvalidCardSet("henchman", -34),
                InvalidCardSet("scheme", -123),
                InvalidCardSet("mastermind", -128)
            ),
            runWithPlugins(plugins) {
                checkValuesInRange(
                    playMaker(
                        villain = -6,
                        hero = -7,
                        henchman = -34,
                        scheme = -123,
                        mastermind = -128
                    )
                )
            })
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
            scheme ?: 0,
            mastermind ?: 0,
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

    @Test
    fun updateSetCountsFromSchemeTest() {
        TODO()
    }
}
