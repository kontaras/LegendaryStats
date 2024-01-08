package games.lmdbg.rules.verifier

import games.lmdbg.rules.MockRules
import games.lmdbg.rules.model.*
import games.lmdbg.rules.playMaker
import games.lmdbg.rules.set.*
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
        runWithLookupTable(schemesById, listOf(Scheme(0))) {
            runWithLookupTable(mastermindsById, listOf(Mastermind(0))) {
                runWithLookupTable(boardsById, listOf(Board(0))) {

                    assertContentEquals(listOf(), checkValuesInRange(playMaker()))

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(
                            heroesById,
                            listOf(Hero(1), Hero(2))
                        ) { checkValuesInRange(playMaker(hero = 1)) })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.HERO, -7)),
                        runWithLookupTable(
                            heroesById,
                            listOf(Hero(1), Hero(2))
                        ) { checkValuesInRange(playMaker(hero = -7)) })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(villainsById, listOf(Villain(101), Villain(102))) {
                            checkValuesInRange(playMaker(villain = 101))
                        })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.VILLAIN, -6)),
                        runWithLookupTable(villainsById, listOf(Villain(101), Villain(102))) {
                            checkValuesInRange(
                                playMaker(villain = -6)
                            )
                        })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(henchmanById, listOf(Henchman(201), Henchman(202))) {
                            checkValuesInRange(playMaker(henchman = 201))
                        })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.HENCHMAN, -34)),
                        runWithLookupTable(henchmanById, listOf(Henchman(201), Henchman(202))) {
                            checkValuesInRange(
                                playMaker(henchman = -34)
                            )
                        })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(
                            schemesById,
                            listOf(Scheme(301), Scheme(302))
                        ) { checkValuesInRange(playMaker(scheme = 301)) })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.SCHEME, -123)),
                        runWithLookupTable(
                            schemesById,
                            listOf(Scheme(301), Scheme(302))
                        ) { checkValuesInRange(playMaker(scheme = -123)) })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(
                            mastermindsById,
                            listOf(Mastermind(401), Mastermind(402))
                        ) { checkValuesInRange(playMaker(mastermind = 401)) })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.MASTERMIND, -128)),
                        runWithLookupTable(
                            mastermindsById,
                            listOf(Mastermind(401), Mastermind(402))
                        ) { checkValuesInRange(playMaker(mastermind = -128)) })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(boardsById, listOf(Board(701), Board(702))) {
                            checkValuesInRange(
                                playMaker(board = 701)
                            )
                        })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.BOARD, -867)),
                        runWithLookupTable(boardsById, listOf(Board(701), Board(702))) {
                            checkValuesInRange(
                                playMaker(
                                    board = -867
                                )
                            )
                        })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(supportsById, listOf(Support(502), Support(501))) {
                            checkValuesInRange(playMaker(support = 501))
                        })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.SUPPORT, 192168001)),
                        runWithLookupTable(supportsById, listOf(Support(502), Support(501))) {
                            checkValuesInRange(
                                playMaker(support = 192168001)
                            )
                        })

                    assertContentEquals(
                        listOf(),
                        runWithLookupTable(startersById, listOf(Starter(601), Starter(602))) {
                            checkValuesInRange(
                                playMaker(startingDeck = 601, startingDeckCount = 4)
                            )
                        })
                    assertContentEquals(
                        listOf(InvalidCardSet(CardSetType.STARTER, 8675309)),
                        runWithLookupTable(startersById, listOf(Starter(601), Starter(602))) {
                            checkValuesInRange(
                                playMaker(startingDeck = 8675309, startingDeckCount = 9001)
                            )
                        })

                }
            }
        }
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
        try {
            plugins.clear()
            plugins.addAll(newPlugins)
            return func()
        } finally {
            plugins.clear()
            plugins.addAll(realPlugins)
        }
    }

    private fun <T, R: CardSet > runWithLookupTable(table: MutableMap<Int, R>, newValues: List<R>, func: () ->T): T {
        val realEntries = table.toMap()
        try {
            table.clear()
            table.putAll(newValues.associateBy { it.id })
            return func()
        } finally {
            table.clear()
            table.putAll(realEntries)
        }
    }
}

