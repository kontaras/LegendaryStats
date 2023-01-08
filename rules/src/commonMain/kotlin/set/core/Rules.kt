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
    
    override val heroesRange: IntRange = Heroes.BLACK_WIDOW.id..Heroes.WOLVERINE.id
    override val villainsRange: IntRange = Villains.BROTHERHOOD.id..Villains.IRON_FOES.id
    override val henchmenRange: IntRange = Henchmen.DOOMBOT_LEGION.id..Henchmen.SENTINEL.id
    override val schemesRange: IntRange = Schemes.THE_LEGACY_VIRUS.id..Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER.id
    override val mastermindsRange: IntRange = Masterminds.DR_DOOM.id..Masterminds.IRON_MONGER.id
    override val supportCardRange: IntRange = Supports.SHIELD_OFFICER.id..Supports.SHIELD_OFFICER.id
    override val recruitSupports: Set<Int> = setOf(Supports.SHIELD_OFFICER.id)
    override val starterRange: IntRange = Starters.SHIELD.id..Starters.SHIELD.id
    override val boardRange: IntRange = Boards.HQ.id ..Boards.HQ.id

    override fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
        when (play.scheme) {
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT.id -> setCounts.henchmen++
            Schemes.SUPER_HERO_CIVIL_WAR.id -> if (play.players == PlayerCount.TWO) setCounts.heroes = 4
            Schemes.THE_LEGACY_VIRUS.id,
            Schemes.MIDTOWN_BANK_ROBBERY.id,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION.id,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS.id,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS.id,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER.id,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE.id -> {
            } //No changes to be made
            else -> {
                log.error { "Base set unknown scheme ${play.scheme} in updateSetCounts" }
            }
        }
    }

    override fun getAlwaysLead(mastermind: Int): Set<TypedCardSet> {
        val group: TypedCardSet? = when(mastermind) {
            Masterminds.DR_DOOM.id, Masterminds.EPIC_DURISSA_THE_DISPOSSESSED.id -> TypedCardSet(CardSetType.HENCHMAN, Henchmen.DOOMBOT_LEGION.id)
            Masterminds.LOKI.id, Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES.id -> TypedCardSet(CardSetType.VILLAIN, Villains.ENEMIES_OF_ASGARD.id)
            Masterminds.MAGNETO.id, Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG.id -> TypedCardSet(CardSetType.VILLAIN, Villains.BROTHERHOOD.id)
            Masterminds.RED_SKULL.id, Masterminds.EPIC_KELILA_BENDER_OF_WILLS.id -> TypedCardSet(CardSetType.VILLAIN, Villains.HYDRA.id)
            Masterminds.IRON_MONGER.id -> TypedCardSet(CardSetType.VILLAIN, Villains.IRON_FOES.id)
            else -> {
                log.error { "Base set always leads got an invalid mastermind value $mastermind" }
                null
            }
        }

        return group?.let { setOf(it) } ?: setOf()
    }

    override fun schemeMandatorySets(scheme: Int): Set<TypedCardSet> {
        return when (scheme) {
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS.id -> setOf(TypedCardSet(CardSetType.VILLAIN, Villains.SKRULLS.id))
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER.id -> setOf(TypedCardSet(CardSetType.VILLAIN, Villains.CHITAURI.id))
            else -> setOf()
        }
    }

    override fun schemeCheckPlay(scheme: Int, play: Play): List<PrintableError> {
        when (scheme) {
            Schemes.SUPER_HERO_CIVIL_WAR.id,
            Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT.id -> {
                return if (play.players in listOf(PlayerCount.SOLO, PlayerCount.ADVANCED_SOLO)) {
                    listOf(PlayerSchemeMismatch)
                } else {
                    listOf()
                }
            }
            Schemes.THE_LEGACY_VIRUS.id,
            Schemes.MIDTOWN_BANK_ROBBERY.id,
            Schemes.PORTALS_TO_THE_DARK_DIMENSION.id,
            Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS.id,
            Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS.id,
            Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE.id,
            Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER.id -> {
                return listOf()
            }
            else -> {
                log.error { "Base set unknown scheme ${play.scheme} in schemeCheckPlay" }
                return listOf()
            }
        }
    }
}