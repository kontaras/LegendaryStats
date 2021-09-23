package games.lmdbg.rules.set.base

import kotlin.test.Test
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
            ).all { it in rules.heroesRange })
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
            ).all { it in rules.villainsRange })
    }

    @Test
    fun henchmenRangeTest() {
        assertTrue(
            listOf(
                Henchmen.DOOMBOT_LEGION,
                Henchmen.HAND_NINJAS,
                Henchmen.SAVAGE_LAND_MUTATES,
                Henchmen.SENTINEL
            ).all { it in rules.henchmenRange })
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
                Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE
            ).all { it in rules.schemesRange })
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
            ).all { it in rules.mastermindsRange })
    }
}