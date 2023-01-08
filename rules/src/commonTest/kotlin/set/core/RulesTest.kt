package games.lmdbg.rules.set.core

import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.model.Scheme
import games.lmdbg.rules.playMaker
import games.lmdbg.rules.verifier.CardSetType
import games.lmdbg.rules.verifier.SetCounts
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.PlayerSchemeMismatch
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

internal class RulesTest {
    private val rules: Rules = Rules()

    @Test
    fun heroesRangeTest() {
        assertTrue(
            listOf(
                Heroes.BLACK_WIDOW,
                Heroes.CAPTAIN_AMERICA,
                Heroes.CAPTAIN_AMERICA,
                Heroes.CYCLOPS,
                Heroes.DEADPOOL,
                Heroes.EMMA_FROST,
                Heroes.GAMBIT,
                Heroes.HAWKEYE,
                Heroes.HULK,
                Heroes.IRON_MAN,
                Heroes.NICK_FURY,
                Heroes.ROGUE,
                Heroes.SPIDER_MAN,
                Heroes.STORM,
                Heroes.THOR,
                Heroes.WOLVERINE
            ).all { it.id in rules.heroesRange })
    }

    @Test
    fun villainsRangeTest() {
        assertTrue(
            listOf(
                Villains.BROTHERHOOD,
                Villains.ENEMIES_OF_ASGARD,
                Villains.HYDRA,
                Villains.MASTERS_OF_EVIL,
                Villains.RADIATION,
                Villains.SKRULLS,
                Villains.SPIDER_FOES,
                Villains.CHITAURI,
                Villains.GAMMA_HUNTERS,
                Villains.IRON_FOES
            ).all { it.id in rules.villainsRange })
    }

    @Test
    fun henchmenRangeTest() {
        assertTrue(
            listOf(
                Henchmen.DOOMBOT_LEGION,
                Henchmen.HAND_NINJAS,
                Henchmen.SAVAGE_LAND_MUTATES,
                Henchmen.SENTINEL
            ).all { it.id in rules.henchmenRange })
    }

    @Test
    fun schemesRangeTest() {
        assertTrue(
            listOf(
                Schemes.THE_LEGACY_VIRUS,
                Schemes.MIDTOWN_BANK_ROBBERY,
                Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
                Schemes.PORTALS_TO_THE_DARK_DIMENSION,
                Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
                Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
                Schemes.SUPER_HERO_CIVIL_WAR,
                Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE,
                Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER
            ).all { it.id in rules.schemesRange })
    }

    @Test
    fun mastermindsRangeTest() {
        assertTrue(
            listOf(
                Masterminds.DR_DOOM,
                Masterminds.EPIC_DURISSA_THE_DISPOSSESSED,
                Masterminds.LOKI,
                Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES,
                Masterminds.MAGNETO,
                Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG,
                Masterminds.RED_SKULL,
                Masterminds.EPIC_KELILA_BENDER_OF_WILLS,
                Masterminds.IRON_MONGER
            ).all { it.id in rules.mastermindsRange })
    }

    @Test
    fun supportRangeTest() {
        assertTrue(
            listOf(
                Supports.SHIELD_OFFICER
            ).all { it.id in rules.supportCardRange })
        assertTrue(rules.recruitSupports.all { it in rules.supportCardRange })
    }

    @Test
    fun starterRangeTest() {
        assertTrue(
            listOf(
                Starters.SHIELD
            ).all { it.id in rules.starterRange })
    }

    @Test
    fun updateSetCountsFromSchemeTest() {
        var original = SetCounts(0, 0, 0, 0)
        rules.updateSetCountsFromScheme(
            playMaker(scheme = Schemes.SUPER_HERO_CIVIL_WAR.id, players = PlayerCount.TWO),
            original
        )
        assertEquals(SetCounts(4, 0, 0, 0), original)

        original = SetCounts(0, 0, 0, 0)
        rules.updateSetCountsFromScheme(
            playMaker(scheme = Schemes.SUPER_HERO_CIVIL_WAR.id, players = PlayerCount.FOUR),
            original
        )
        assertEquals(SetCounts(0, 0, 0, 0), original)

        val invalidScheme = Scheme(-1, null, null, null)
        for (scheme: Scheme in listOf(
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER,
            invalidScheme
        )) {
            original = SetCounts(0, 0, 0, 0)
            rules.updateSetCountsFromScheme(playMaker(scheme = scheme.id), original)
            assertEquals(SetCounts(0, 0, 0, 0), original)
        }

        original = SetCounts(0, 0, 0, 0)
        rules.updateSetCountsFromScheme(
            playMaker(scheme = Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT.id),
            original
        )
        assertEquals(SetCounts(0, 0, 1, 0), original)
    }

    @Test
    fun getAlwaysLeadTest() {
        assertEquals(
            setOf(TypedCardSet(CardSetType.HENCHMAN, Henchmen.DOOMBOT_LEGION.id)),
            rules.getAlwaysLead(Masterminds.DR_DOOM.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.ENEMIES_OF_ASGARD.id)),
            rules.getAlwaysLead(Masterminds.LOKI.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.BROTHERHOOD.id)),
            rules.getAlwaysLead(Masterminds.MAGNETO.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.HYDRA.id)),
            rules.getAlwaysLead(Masterminds.RED_SKULL.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.IRON_FOES.id)),
            rules.getAlwaysLead(Masterminds.IRON_MONGER.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.HENCHMAN, Henchmen.DOOMBOT_LEGION.id)),
            rules.getAlwaysLead(Masterminds.EPIC_DURISSA_THE_DISPOSSESSED.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.HYDRA.id)),
            rules.getAlwaysLead(Masterminds.EPIC_KELILA_BENDER_OF_WILLS.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.BROTHERHOOD.id)),
            rules.getAlwaysLead(Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.ENEMIES_OF_ASGARD.id)),
            rules.getAlwaysLead(Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES.id)
        )

        val invalidMastermind = -1
        assertEquals(
            setOf(), rules.getAlwaysLead(invalidMastermind)
        )
    }

    @Test
    fun schemeMandatorySetsTest() {
        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.CHITAURI.id)),
            rules.schemeMandatorySets(Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER.id)
        )

        assertEquals(
            setOf(TypedCardSet(CardSetType.VILLAIN, Villains.SKRULLS.id)),
            rules.schemeMandatorySets(Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS.id)
        )

        val invalidScheme = Scheme(-1, null, null, null)
        for (scheme in listOf(
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.SUPER_HERO_CIVIL_WAR,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE,
            invalidScheme
        )) {
            assertEquals(
                setOf(),
                rules.schemeMandatorySets(scheme.id)
            )
        }
    }

    @Test
    fun schemeCheckPlayTest() {
        for (scheme in listOf(
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE
        )) {
            assertEquals(
                listOf(),
                rules.schemeCheckPlay(scheme.id, playMaker())
            )
        }

        for (scheme in listOf(
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
            Schemes.SUPER_HERO_CIVIL_WAR,
        )) {
            for (players in listOf(PlayerCount.SOLO, PlayerCount.ADVANCED_SOLO)) {
                assertEquals(
                    listOf(PlayerSchemeMismatch),
                    rules.schemeCheckPlay(scheme.id, playMaker(players = players))
                )
            }
        }

        for (scheme in listOf(
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
            Schemes.SUPER_HERO_CIVIL_WAR,
        )) {
            for (players in listOf(PlayerCount.TWO, PlayerCount.THREE, PlayerCount.FOUR, PlayerCount.FIVE)) {
                assertEquals(
                    listOf(),
                    rules.schemeCheckPlay(scheme.id, playMaker(players = players))
                )
            }
        }

        for (scheme in listOf(
            -1 //Invalid scheme
        )) {
            assertEquals(
                listOf(),
                rules.schemeCheckPlay(scheme, playMaker())
            )
        }
    }
}