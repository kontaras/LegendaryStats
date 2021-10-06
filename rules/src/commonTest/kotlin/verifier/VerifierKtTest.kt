package games.lmdbg.rules.verifier

import games.lmdbg.rules.MockRules
import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.playMaker
import games.lmdbg.rules.set.base.*
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
        val starters: MutableMap<Int, Int> = mutableMapOf(Starters.SHIELD to 4)
        val testPlay = Play(
            Outcome.DRAW,
            PlayerCount.FOUR,
            Schemes.THE_LEGACY_VIRUS,
            Masterminds.LOKI,
            heroes,
            villains,
            henchmen,
            setOf(),
            starters
        )

        val counts = getPlayerCountRules(PlayerCount.FOUR)

        assertContentEquals(listOf(), checkCardSetSizes(testPlay, counts))

        heroes.remove(Heroes.GAMBIT)
        assertContentEquals(
            listOf(WrongSetCount("hero", 5, 4)),
            checkCardSetSizes(testPlay, counts)
        )
        heroes.add(Heroes.GAMBIT)

        heroes.add(Heroes.DEADPOOL)
        assertContentEquals(
            listOf(WrongSetCount("hero", 5, 6)),
            checkCardSetSizes(testPlay, counts)
        )
        heroes.remove(Heroes.GAMBIT)

        villains.remove(Villains.ENEMIES_OF_ASGARD)
        assertContentEquals(
            listOf(WrongSetCount("villain", 3, 2)),
            checkCardSetSizes(testPlay, counts)
        )
        villains.add(Villains.ENEMIES_OF_ASGARD)

        villains.add(Villains.MASTERS_OF_EVIL)
        assertContentEquals(
            listOf(WrongSetCount("villain", 3, 4)),
            checkCardSetSizes(testPlay, counts)
        )
        villains.remove(Villains.ENEMIES_OF_ASGARD)

        henchmen.remove(Henchmen.DOOMBOT_LEGION)
        assertContentEquals(
            listOf(WrongSetCount("henchman", 2, 1)),
            checkCardSetSizes(testPlay, counts)
        )
        henchmen.add(Henchmen.DOOMBOT_LEGION)

        henchmen.add(Henchmen.HAND_NINJAS)
        assertContentEquals(
            listOf(WrongSetCount("henchman", 2, 3)),
            checkCardSetSizes(testPlay, counts)
        )
        henchmen.remove(Henchmen.DOOMBOT_LEGION)

        starters[Starters.SHIELD] = 3
        assertContentEquals(
            listOf(WrongSetCount("starting deck", 4, 3)),
            checkCardSetSizes(testPlay, counts)
        )

        starters[Starters.SHIELD] = 5
        assertContentEquals(
            listOf(WrongSetCount("starting deck", 4, 5)),
            checkCardSetSizes(testPlay, counts)
        )

        starters[Starters.SHIELD] = 0
        assertContentEquals(
            listOf(InvalidCardQuantity("starting deck", Starters.SHIELD, 0), WrongSetCount("starting deck", 4, 0)),
            checkCardSetSizes(testPlay, counts)
        )
        starters[Starters.SHIELD] = 4
    }

    @Test
    fun checkValuesInRangeTest() {
        val plugins = setOf(
            MockRules(schemesRange = 0..0, mastermindsRange = 0..0),
            MockRules(
                heroesRange = 1..2,
                villainsRange = 101..102,
                henchmenRange = 201..202,
                schemesRange = 301..302,
                mastermindsRange = 401..402,
                supportCardRange = 501..502,
                starterDeckRange = 601..602
            ),
            MockRules(
                heroesRange = 5..7,
                villainsRange = 105..107,
                henchmenRange = 205..207,
                schemesRange = 305..307,
                mastermindsRange = 405..407,
                supportCardRange = 505..507,
                starterDeckRange = 605..607
            )
        )

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

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 501)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 507)) })
        assertContentEquals(
            listOf(InvalidCardSet("support", 192168001)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 192168001)) })

        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(startingDeck = 601, startingDeckCount = 4)) })
        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(startingDeck = 607, startingDeckCount = 5)) })
        assertContentEquals(
            listOf(InvalidCardSet("starting deck", 8675309)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(startingDeck = 8675309, startingDeckCount = 9001)) })

        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) {
                checkValuesInRange(
                    playMaker(
                        hero = 7,
                        villain = 101,
                        henchman = 207,
                        scheme = 307,
                        mastermind = 407,
                        support = 507,
                        startingDeck = 607, startingDeckCount = 5
                    )
                )
            })
        assertContentEquals(
            listOf(
                InvalidCardSet("hero", -7),
                InvalidCardSet("villain", -6),
                InvalidCardSet("henchman", -34),
                InvalidCardSet("support", 192168001),
                InvalidCardSet("scheme", -123),
                InvalidCardSet("mastermind", -128),
                InvalidCardSet("starting deck", 8675309)
            ),
            runWithPlugins(plugins) {
                checkValuesInRange(
                    playMaker(
                        villain = -6,
                        hero = -7,
                        henchman = -34,
                        scheme = -123,
                        mastermind = -128,
                        support = 192168001,
                        startingDeck = 8675309, startingDeckCount = 9001
                    )
                )
            })
    }

    @Test
    fun getPlayerCountRulesTest() {
        assertEquals(SetCounts(3, 1, 1, 1), getPlayerCountRules(PlayerCount.SOLO))
        assertEquals(SetCounts(3, 1, 1, 1), getPlayerCountRules(PlayerCount.ADVANCED))
        assertEquals(SetCounts(5, 2, 1, 2), getPlayerCountRules(PlayerCount.TWO))
        assertEquals(SetCounts(5, 3, 1, 3), getPlayerCountRules(PlayerCount.THREE))
        assertEquals(SetCounts(5, 3, 2, 4), getPlayerCountRules(PlayerCount.FOUR))
        assertEquals(SetCounts(6, 4, 2, 5), getPlayerCountRules(PlayerCount.FIVE))
    }

    @Test
    fun updateSetCountsFromSchemeTest() {
        val plugin = MockRules(schemesRange = 0..10)

        val plugins = setOf(plugin)

        plugin.setCountLogic = { _, setCount -> setCount.heroes++ }

        val setCounts = SetCounts(0, 0, 0, 0)
        runWithPlugins(plugins) {
            updateSetCountsFromScheme(playMaker(scheme = 3), setCounts)
        }
        assertEquals(SetCounts(1, 0, 0, 0), setCounts)

        plugin.setCountLogic = { _, _ -> throw Exception("This code should not be reached") }
        runWithPlugins(plugins) {
            updateSetCountsFromScheme(playMaker(scheme = -1), setCounts)
        }
    }

    @Test
    fun checkAlwaysLeadsTest() {
        val plugin = MockRules(mastermindsRange = 0..10)

        val plugins = setOf(plugin)

        plugin.alwaysLeadsLogic = { _ -> setOf() }
        runWithPlugins(plugins) {
            assertEquals(listOf(), checkAlwaysLeads(playMaker(mastermind = 10)))
        }

        plugin.alwaysLeadsLogic = { _ -> setOf(MandatoryCardSet(CardSetTypes.VILLAIN, 3)) }
        runWithPlugins(plugins) {
            assertEquals(
                listOf(MissingRequiredSet("villain", 3)),
                checkAlwaysLeads(playMaker(mastermind = 10))
            )
        }

        plugin.alwaysLeadsLogic = { _ -> setOf(MandatoryCardSet(CardSetTypes.VILLAIN, 3)) }
        runWithPlugins(plugins) {
            assertEquals(listOf(), checkAlwaysLeads(playMaker(mastermind = 10, villain = 3)))
        }

        plugin.alwaysLeadsLogic = { _ -> setOf(MandatoryCardSet(CardSetTypes.HENCHMAN, 3)) }
        runWithPlugins(plugins) {
            assertEquals(
                listOf(MissingRequiredSet("henchman", 3)),
                checkAlwaysLeads(playMaker(mastermind = 10))
            )
        }

        plugin.alwaysLeadsLogic = { _ -> setOf(MandatoryCardSet(CardSetTypes.HENCHMAN, 3)) }
        runWithPlugins(plugins) {
            assertEquals(listOf(), checkAlwaysLeads(playMaker(mastermind = 10, henchman = 3)))
        }

        plugin.alwaysLeadsLogic = { _ ->
            setOf(
                MandatoryCardSet(CardSetTypes.VILLAIN, 3),
                MandatoryCardSet(CardSetTypes.HENCHMAN, 3)
            )
        }
        runWithPlugins(plugins) {
            assertEquals(listOf(), checkAlwaysLeads(playMaker(mastermind = 10, henchman = 3)))
        }

        plugin.alwaysLeadsLogic = { _ ->
            setOf(
                MandatoryCardSet(CardSetTypes.VILLAIN, 3),
                MandatoryCardSet(CardSetTypes.HENCHMAN, 3)
            )
        }
        runWithPlugins(plugins) {
            assertEquals(listOf(), checkAlwaysLeads(playMaker(mastermind = 10, villain = 3)))
        }

        plugin.alwaysLeadsLogic = { _ ->
            setOf(
                MandatoryCardSet(CardSetTypes.VILLAIN, 3),
                MandatoryCardSet(CardSetTypes.HENCHMAN, 3)
            )
        }
        runWithPlugins(plugins) {
            assertEquals(
                listOf(MissingRequiredSet("henchman", 3), MissingRequiredSet("villain", 3)).sortedBy { it.toString() },
                checkAlwaysLeads(playMaker(mastermind = 10)).sortedBy { it.toString() }
            )
        }
    }

    @Test
    fun checkRecruitSupport() {
        val plugin = MockRules(recruitSupports = 101)

        val plugins = setOf(plugin)

        runWithPlugins(setOf()) {
            assertEquals(
                listOf(MissingRecruitSupport),
                checkRequiredCardSets(playMaker())
            )
        }

        runWithPlugins(setOf()) {
            assertEquals(
                listOf(MissingRecruitSupport),
                checkRequiredCardSets(playMaker(support = 101))
            )
        }

        runWithPlugins(plugins) {
            assertEquals(
                listOf(MissingRecruitSupport),
                checkRequiredCardSets(playMaker())
            )
        }

        runWithPlugins(plugins) {
            assertEquals(
                listOf(),
                checkRequiredCardSets(playMaker(support = 101))
            )
        }
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
}

