package games.lmdbg.rules.set.core

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.verifier.CardSetType
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.ReleaseRulesPlugin
import games.lmdbg.rules.verifier.SetCounts
import games.lmdbg.rules.verifier.PrintableError
import games.lmdbg.rules.verifier.PlayerSchemeMismatch
import org.lighthousegames.logging.logging

class Rules : ReleaseRulesPlugin {
    val log = logging()
    
    override val heroesRange: IntRange = Heroes.BLACK_WIDOW..Heroes.WOLVERINE
    override val villainsRange: IntRange = Villains.BROTHERHOOD..Villains.IRON_FOES
    override val henchmenRange: IntRange = Henchmen.DOOMBOT_LEGION..Henchmen.SENTINEL
    override val schemesRange: IntRange = Schemes.THE_LEGACY_VIRUS..Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER
    override val mastermindsRange: IntRange = Masterminds.DR_DOOM..Masterminds.IRON_MONGER
    override val supportCardRange: IntRange = Supports.SHIELD_OFFICER..Supports.SHIELD_OFFICER
    override val recruitSupports: Set<Int> = setOf(Supports.SHIELD_OFFICER)
    override val starterRange: IntRange = Starters.SHIELD..Starters.SHIELD
    override val boardRange: IntRange = Boards.HQ ..Boards.HQ

    override fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
        when (play.scheme) {
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT -> setCounts.henchmen++
            Schemes.SUPER_HERO_CIVIL_WAR -> if (play.players == PlayerCount.TWO) setCounts.heroes = 4
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE -> {
            } //No changes to be made
            else -> {
                log.error { "Base set unknown scheme ${play.scheme} in updateSetCounts" }
            }
        }
    }

    override fun getAlwaysLead(mastermind: Int): Set<TypedCardSet> {
        val group: TypedCardSet? = when(mastermind) {
            Masterminds.DR_DOOM, Masterminds.EPIC_DURISSA_THE_DISPOSSESSED -> TypedCardSet(CardSetType.HENCHMAN, Henchmen.DOOMBOT_LEGION)
            Masterminds.LOKI, Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES -> TypedCardSet(CardSetType.VILLAIN, Villains.ENEMIES_OF_ASGARD)
            Masterminds.MAGNETO, Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG -> TypedCardSet(CardSetType.VILLAIN, Villains.BROTHERHOOD)
            Masterminds.RED_SKULL, Masterminds.EPIC_KELILA_BENDER_OF_WILLS -> TypedCardSet(CardSetType.VILLAIN, Villains.HYDRA)
            Masterminds.IRON_MONGER -> TypedCardSet(CardSetType.VILLAIN, Villains.IRON_FOES)
            else -> {
                log.error { "Base set always leads got an invalid mastermind value $mastermind" }
                null
            }
        }

        return group?.let { setOf(it) } ?: setOf()
    }

    override fun schemeMandatorySets(scheme: Int): Set<TypedCardSet> {
        return when (scheme) {
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS -> setOf(TypedCardSet(CardSetType.VILLAIN, Villains.SKRULLS))
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER -> setOf(TypedCardSet(CardSetType.VILLAIN, Villains.CHITAURI))
            else -> setOf()
        }
    }

    override fun schemeCheckPlay(scheme: Int, play: Play): List<PrintableError> {
        when (scheme) {
            Schemes.SUPER_HERO_CIVIL_WAR,
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT -> {
                return if (play.players in listOf(PlayerCount.SOLO, PlayerCount.ADVANCED_SOLO)) {
                    listOf(PlayerSchemeMismatch)
                } else {
                    listOf()
                }
            }
            Schemes.THE_LEGACY_VIRUS,
            Schemes.MIDTOWN_BANK_ROBBERY,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER -> {
                return listOf()
            }
            else -> {
                log.error { "Base set unknown scheme ${play.scheme} in schemeCheckPlay" }
                return listOf()
            }
        }
    }
}