package games.lmdbg.rules.verifier

import games.lmdbg.rules.MockRules
import games.lmdbg.rules.model.*
import games.lmdbg.rules.playMaker
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

internal class VerifierKtTest {
    @Test
    fun checkCardSetSizesTest() {
        val heroes: MutableSet<Int> =
            mutableSetOf(1, 2, 3, 4, 5)
        val villains: MutableSet<Int> = mutableSetOf(6, 7, 8)
        val henchmen: MutableSet<Int> = mutableSetOf(9, 10)
        val starters: MutableMap<Int, Int> = mutableMapOf(11 to 4)
        val testPlay = Play(
            Outcome.DRAW_DECK,
            PlayerCount.FOUR,
            12,
           13,
            heroes,
            villains,
            henchmen,
            setOf(),
            starters,
            14
        )

        val counts = getPlayerCountRules(PlayerCount.FOUR)

        assertContentEquals(listOf(), checkCardSetSizes(testPlay, counts))

        heroes.remove(5)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.HERO, 5, 4)),
            checkCardSetSizes(testPlay, counts)
        )
        heroes.add(5)

        heroes.add(15)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.HERO, 5, 6)),
            checkCardSetSizes(testPlay, counts)
        )
        heroes.remove(5)

        villains.remove(6)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.VILLAIN, 3, 2)),
            checkCardSetSizes(testPlay, counts)
        )
        villains.add(6)

        villains.add(16)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.VILLAIN, 3, 4)),
            checkCardSetSizes(testPlay, counts)
        )
        villains.remove(6)

        henchmen.remove(10)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.HENCHMAN, 2, 1)),
            checkCardSetSizes(testPlay, counts)
        )
        henchmen.add(10)

        henchmen.add(17)
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.HENCHMAN, 2, 3)),
            checkCardSetSizes(testPlay, counts)
        )
        henchmen.remove(10)

        starters[11] = 3
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.STARTER, 4, 3)),
            checkCardSetSizes(testPlay, counts)
        )

        starters[11] = 5
        assertContentEquals(
            listOf(WrongSetCount(CardSetType.STARTER, 4, 5)),
            checkCardSetSizes(testPlay, counts)
        )

        starters[11] = -1
        assertContentEquals(
            listOf(
                InvalidCardQuantity(TypedCardSet(CardSetType.STARTER, 11), -1),
                WrongSetCount(CardSetType.STARTER, 4, -1)
            ),
            checkCardSetSizes(testPlay, counts)
        )
        starters[11] = 4
    }

    @Test
    fun checkValuesInRangeTest() {
        val plugins = setOf(
            MockRules(
                release = Release(
                    schemes = listOf(Scheme(0)),
                    masterminds = listOf(Mastermind(0)),
                    boards = listOf(Board(0))
                )
            ),
            MockRules(
                release = Release(
                    heroes = listOf(Hero(1), Hero(2)),
                    villains = listOf(Villain(101), Villain(102)),
                    henchmen = listOf(Henchman(201), Henchman(202)),
                    schemes = listOf(Scheme(301), Scheme(302)),
                    masterminds = listOf(Mastermind(401), Mastermind(402)),
                    supports = listOf(Support(502), Support(501)),
                    starters = listOf(Starter(601), Starter(602)),
                    boards = listOf(Board(701), Board(702))
                )
            ),
            MockRules(
                release = Release(
                    heroes = listOf(Hero(5), Hero(7)),
                    villains = listOf(Villain(105), Villain(107)),
                    henchmen = listOf(Henchman(205), Henchman(207)),
                    schemes = listOf(Scheme(305), Scheme(307)),
                    masterminds = listOf(Mastermind(405), Mastermind(407)),
                    supports = listOf(Support(505), Support(5017)),
                    starters = listOf(Starter(605), Starter(607)),
                    boards = listOf(Board(705), Board(707))
                )
            )
        )

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker()) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = 1)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = 7)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.HERO, -7)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(hero = -7)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = 101)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = 107)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.VILLAIN, -6)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(villain = -6)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = 201)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = 207)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.HENCHMAN, -34)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(henchman = -34)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = 301)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = 307)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.SCHEME, -123)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(scheme = -123)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = 401)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = 407)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.MASTERMIND, -128)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(mastermind = -128)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(board = 701)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(board = 707)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.BOARD, -867)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(board = -867)) })

        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 501)) })
        assertContentEquals(listOf(), runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 507)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.SUPPORT, 192168001)),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(support = 192168001)) })

        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(startingDeck = 601, startingDeckCount = 4)) })
        assertContentEquals(
            listOf(),
            runWithPlugins(plugins) { checkValuesInRange(playMaker(startingDeck = 607, startingDeckCount = 5)) })
        assertContentEquals(
            listOf(InvalidCardSet(CardSetType.STARTER, 8675309)),
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
                        startingDeck = 607,
                        startingDeckCount = 5,
                        board = 707
                    )
                )
            })
        assertContentEquals(
            listOf(
                InvalidCardSet(CardSetType.HERO, -7),
                InvalidCardSet(CardSetType.VILLAIN, -6),
                InvalidCardSet(CardSetType.HENCHMAN, -34),
                InvalidCardSet(CardSetType.SUPPORT, 192168001),
                InvalidCardSet(CardSetType.SCHEME, -123),
                InvalidCardSet(CardSetType.MASTERMIND, -128),
                InvalidCardSet(CardSetType.STARTER, 8675309),
                InvalidCardSet(CardSetType.BOARD, -867)
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
                        startingDeck = 8675309,
                        startingDeckCount = 9001,
                        board = -867
                    )
                )
            })
    }

    @Test
    fun getPlayerCountRulesTest() {
        assertEquals(SetCounts(3, 1, 1, 1), getPlayerCountRules(PlayerCount.SOLO))
        assertEquals(SetCounts(3, 1, 1, 1), getPlayerCountRules(PlayerCount.ADVANCED_SOLO))
        assertEquals(SetCounts(5, 2, 1, 2), getPlayerCountRules(PlayerCount.TWO))
        assertEquals(SetCounts(5, 3, 1, 3), getPlayerCountRules(PlayerCount.THREE))
        assertEquals(SetCounts(5, 3, 2, 4), getPlayerCountRules(PlayerCount.FOUR))
        assertEquals(SetCounts(6, 4, 2, 5), getPlayerCountRules(PlayerCount.FIVE))
    }

    @Test
    fun updateSetCountsFromSchemeTest() {
        var setCounts = SetCounts(0, 0, 0, 0)
        runWithPlugins(setOf()) {
            updateSetCountsFromScheme(playMaker(scheme = -1), setCounts)
        }
        assertEquals(SetCounts(0, 0, 0, 0), setCounts)

        val plugin = MockRules(release = Release(schemes = listOf(Scheme(0), Scheme(10))))

        val plugins = setOf(plugin)

        plugin.setCountLogic = { _, setCount -> setCount.heroes++ }

        setCounts = SetCounts(0, 0, 0, 0)
        runWithPlugins(plugins) {
            updateSetCountsFromScheme(playMaker(scheme = 3), setCounts)
        }
        assertEquals(SetCounts(1, 0, 0, 0), setCounts)

        setCounts = SetCounts(0, 0, 0, 0)
        plugin.setCountLogic = { _, _ -> throw Exception("This code should not be reached") }
        runWithPlugins(plugins) {
            updateSetCountsFromScheme(playMaker(scheme = -1), setCounts)
        }
        assertEquals(SetCounts(0, 0, 0, 0), setCounts)
    }

    @Test
    fun checkMandatorySetsTest() {
        assertEquals(listOf(), checkMandatorySets(playMaker(mastermind = 10), listOf()))

        assertEquals(
            listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.VILLAIN, 3)))),
            checkMandatorySets(playMaker(mastermind = 10), listOf(setOf(TypedCardSet(CardSetType.VILLAIN, 3))))
        )

        assertEquals(
            listOf(),
            checkMandatorySets(
                playMaker(villain = 3),
                listOf(setOf(TypedCardSet(CardSetType.VILLAIN, 3)))
            )
        )

        assertEquals(
            listOf(),
            checkMandatorySets(
                playMaker(mastermind = 10, villain = 3),
                listOf(setOf(TypedCardSet(CardSetType.VILLAIN, 3)))
            )
        )

        assertEquals(
            listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.HENCHMAN, 3)))),
            checkMandatorySets(playMaker(mastermind = 10), listOf(setOf(TypedCardSet(CardSetType.HENCHMAN, 3))))
        )

        assertEquals(
            listOf(), checkMandatorySets(
                playMaker(mastermind = 10, henchman = 3),
                listOf(setOf(TypedCardSet(CardSetType.HENCHMAN, 3)))
            )
        )

        assertEquals(
            listOf(), checkMandatorySets(
                playMaker(mastermind = 10, henchman = 3), listOf(
                    setOf(
                        TypedCardSet(CardSetType.VILLAIN, 3),
                        TypedCardSet(CardSetType.HENCHMAN, 3)
                    )
                )
            )
        )

        assertEquals(
            listOf(), checkMandatorySets(
                playMaker(mastermind = 10, villain = 3), listOf(
                    setOf(
                        TypedCardSet(CardSetType.VILLAIN, 3),
                        TypedCardSet(CardSetType.HENCHMAN, 3)
                    )
                )
            )
        )

        assertEquals(
            listOf(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.HENCHMAN, 3),
                        TypedCardSet(CardSetType.VILLAIN, 3)
                    ).sortedBy { it.toString() })
            ),
            checkMandatorySets(
                playMaker(mastermind = 10), listOf(
                    setOf(
                        TypedCardSet(CardSetType.VILLAIN, 3),
                        TypedCardSet(CardSetType.HENCHMAN, 3)
                    )
                )
            )
        )

        assertEquals(
            listOf(
                MissingRequiredSet(listOf(TypedCardSet(CardSetType.HENCHMAN, 3))),
                MissingRequiredSet(listOf(TypedCardSet(CardSetType.HENCHMAN, 5)))
            ),
            checkMandatorySets(
                playMaker(mastermind = 10),
                listOf(setOf(TypedCardSet(CardSetType.HENCHMAN, 3)), setOf(TypedCardSet(CardSetType.HENCHMAN, 5)))
            )
        )

        // Check logic of unknown card set types. Will break if we ever have required board logic.
        assertEquals(
            listOf(MissingRequiredSet(listOf(TypedCardSet(CardSetType.BOARD, 3)))),
            checkMandatorySets(playMaker(board = 3), listOf(setOf(TypedCardSet(CardSetType.BOARD, 3))))
        )

        assertEquals(
            listOf(),
            checkMandatorySets(
                playMaker(villain = 3),
                listOf(setOf(TypedCardSet(CardSetType.BOARD, 4), TypedCardSet(CardSetType.VILLAIN, 3)))
            )
        )
    }

    @Test
    fun checkRecruitSupportTest() {
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

    @Test
    fun checkRequiredCardSetsTest() {
        val plugin = MockRules(release = Release(masterminds = listOf(Mastermind(1)), schemes = listOf(Scheme(1))))
        plugin.alwaysLeadsLogic = { _ ->
            setOf(TypedCardSet(CardSetType.HENCHMAN, 123))
        }

        plugin.schemeSetsLogic = { _ ->
            setOf(TypedCardSet(CardSetType.VILLAIN, 456))
        }

        val plugins = setOf(plugin)

        runWithPlugins(plugins) {
            assertEquals(
                listOf(
                    MissingRequiredSet(listOf(TypedCardSet(CardSetType.HENCHMAN, 123))),
                    MissingRequiredSet(listOf(TypedCardSet(CardSetType.VILLAIN, 456))),
                    MissingRecruitSupport
                ),
                checkRequiredCardSets(playMaker(mastermind = 1, scheme = 1))
            )
        }
    }

    @Test
    fun checkPlayValidForSchemeTest() {
        val plugin = MockRules(release = Release(schemes = listOf(Scheme(1))))
        val plugins = setOf(plugin)

        runWithPlugins(plugins) {
            assertEquals(
                listOf(),
                checkPlayValidForScheme(playMaker(scheme = 2))
            )
        }

        val testPlay = playMaker(scheme = 1)
        plugin.schemeCheckPlayLogic = { scheme, play ->
            assertEquals(1, scheme)
            assertEquals(testPlay, play)
            listOf()
        }
        runWithPlugins(plugins) {
            assertEquals(
                listOf(),
                checkPlayValidForScheme(testPlay)
            )
        }

        val testError = object : PrintableError {
            override fun getMessage(): String {
                throw Exception("Should not be called")
            }
        }
        plugin.schemeCheckPlayLogic = { scheme, play ->
            assertEquals(1, scheme)
            assertEquals(testPlay, play)
            listOf(testError)
        }
        runWithPlugins(plugins) {
            assertEquals(
                listOf(testError),
                checkPlayValidForScheme(testPlay)
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

