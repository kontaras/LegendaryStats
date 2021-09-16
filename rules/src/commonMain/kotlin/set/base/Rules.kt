package games.lmdbg.rules.set.base

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.verifier.ReleaseRulesPlugin
import games.lmdbg.rules.verifier.SetCounts

class Rules : ReleaseRulesPlugin {
    override val heroesRange: IntRange = Heroes.BLACK_WIDOW..Heroes.WOLVERINE
    override val villainsRange: IntRange = Villains.BROTHERHOOD..Villains.IRON_FOES
    override val henchmenRange: IntRange = Henchmen.DOOMBOT_LEGION..Henchmen.SAVAGE_LAND_MUTATES
    override val schemesRange: IntRange = Schemes.THE_LEGACY_VIRUS..Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE
    override val mastermindRange: IntRange = Masterminds.DR_DOOM..Masterminds.IRON_MONGER

    override fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
        when (play.scheme) {
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT -> setCounts.henchmen++
            Schemes.SUPER_HERO_CIVIL_WAR -> if (play.players == PlayerCount.TWO) setCounts.heroes = 4
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE -> {
            } //No changes to be made
        }
    }
}