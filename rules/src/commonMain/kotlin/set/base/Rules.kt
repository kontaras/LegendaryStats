package games.lmdbg.rules.set.base

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.verifier.CardSetTypes
import games.lmdbg.rules.verifier.MandatoryCardSet
import games.lmdbg.rules.verifier.ReleaseRulesPlugin
import games.lmdbg.rules.verifier.SetCounts
import org.lighthousegames.logging.logging

class Rules : ReleaseRulesPlugin {
    override val heroesRange: IntRange = Heroes.BLACK_WIDOW..Heroes.WOLVERINE
    override val villainsRange: IntRange = Villains.BROTHERHOOD..Villains.IRON_FOES
    override val henchmenRange: IntRange = Henchmen.DOOMBOT_LEGION..Henchmen.SENTINEL
    override val schemesRange: IntRange = Schemes.THE_LEGACY_VIRUS..Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE
    override val mastermindsRange: IntRange = Masterminds.DR_DOOM..Masterminds.IRON_MONGER
    override val supportCardRange: IntRange = Supports.SHIELD_OFFICER..Supports.SHIELD_OFFICER
    override val recruitSupports: Set<Int> = setOf(Supports.SHIELD_OFFICER)
    override val starterRange: IntRange = Starters.SHIELD..Starters.SHIELD

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
            else -> {
                log.error { "Base set unknown scheme ${play.scheme} in updateSetCounts" }
            }
        }
    }

    override fun getAlwaysLead(mastermind: Int): Set<MandatoryCardSet> {
        val group: MandatoryCardSet? = when(mastermind) {
            Masterminds.DR_DOOM, Masterminds.EPIC_DURISSA_THE_DISPOSSESSED -> MandatoryCardSet(CardSetTypes.HENCHMAN, Henchmen.DOOMBOT_LEGION)
            Masterminds.LOKI, Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES -> MandatoryCardSet(CardSetTypes.VILLAIN, Villains.ENEMIES_OF_ASGARD)
            Masterminds.MAGNETO, Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG -> MandatoryCardSet(CardSetTypes.VILLAIN, Villains.BROTHERHOOD)
            Masterminds.RED_SKULL, Masterminds.EPIC_KELILA_BENDER_OF_WILLS -> MandatoryCardSet(CardSetTypes.VILLAIN, Villains.HYDRA)
            Masterminds.IRON_MONGER -> MandatoryCardSet(CardSetTypes.VILLAIN, Villains.IRON_FOES)
            else -> {
                log.error { "Base set always leads got an invalid mastermind value $mastermind" }
                null
            }
        }

        return group?.let { setOf(it) } ?: setOf()
    }

    companion object {
        val log = logging()
    }
}