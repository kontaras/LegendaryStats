package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import kotlin.js.JsName

/**
 * Check if a given play follows all game setup rules
 *
 * @param play The play to verify
 * @return A list of issues with this play. Will be empty if there are no issues.
 */
@JsName("verify") //Prevent mangling
fun verify(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    val setCounts = getPlayerCountRules(play.players)

    updateSetCountsFromScheme(play, setCounts)

    errors.addAll(checkCardSetSizes(play, setCounts))
    errors.addAll(checkValuesInRange(play))
    errors.addAll(checkRequiredCardSets(play))

    return errors
}

/**
 * Check if each of the card sets from the play has the correct amount of items.
 *
 * @param play The play to check
 * @param setCounts The expected number of items in each set
 * @return A list containing [WrongSetCount]s for sets that have the wrong number of items
 */
fun checkCardSetSizes(play: Play, setCounts: SetCounts): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    if (play.heroes.size != setCounts.heroes) {
        errors.add(WrongSetCount("hero", setCounts.heroes, play.heroes.size))
    }

    if (play.villains.size != setCounts.villains) {
        errors.add(WrongSetCount("villain", setCounts.villains, play.villains.size))
    }

    if (play.henchmen.size != setCounts.henchmen) {
        errors.add(WrongSetCount("henchman", setCounts.henchmen, play.henchmen.size))
    }

    var totalStarters = 0
    for ((deckType, deckCount) in play.starters) {
        if (deckCount <= 0) {
            errors.add(InvalidCardQuantity("starting deck", deckType, deckCount))
        }
        totalStarters += deckCount
    }
    if (totalStarters != setCounts.starters) {
        errors.add(WrongSetCount("starting deck", setCounts.starters, totalStarters))
    }

    return errors
}

/**
 * Check if each card set index in a play is a valid card set id.
 *
 * @param play The play to check
 * @return [InvalidCardSet] for each invalid card set id
 */
internal fun checkValuesInRange(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    for (hero in play.heroes) {
        if (!checkValidValue(hero, ReleaseRulesPlugin::heroesRange)) {
            errors.add(InvalidCardSet("hero", hero))
        }
    }

    for (villain in play.villains) {
        if (!checkValidValue(villain, ReleaseRulesPlugin::villainsRange)) {
            errors.add(InvalidCardSet("villain", villain))
        }
    }

    for (henchman in play.henchmen) {
        if (!checkValidValue(henchman, ReleaseRulesPlugin::henchmenRange)) {
            errors.add(InvalidCardSet("henchman", henchman))
        }
    }

    for (support in play.supports) {
        if (!checkValidValue(support, ReleaseRulesPlugin::supportCardRange)) {
            errors.add(InvalidCardSet("support", support))
        }
    }

    if (!checkValidValue(play.scheme, ReleaseRulesPlugin::schemesRange)) {
        errors.add(InvalidCardSet("scheme", play.scheme))
    }

    if (!checkValidValue(play.mastermind, ReleaseRulesPlugin::mastermindsRange)) {
        errors.add(InvalidCardSet("mastermind", play.mastermind))
    }

    for (starter in play.starters.keys) {
        if (!checkValidValue(starter, ReleaseRulesPlugin::starterRange)) {
            errors.add(InvalidCardSet("starting deck", starter))
        }
    }

    return errors
}

/**
 * Check if a given card set id is a valid one
 *
 * @param setId Card Set ID to check
 * @param field The plugin field accessor to get the valid values for the set id
 * @return true if there is a set that contains that id, false otherwise
 */
private fun checkValidValue(setId: Int, field: (ReleaseRulesPlugin) -> IntRange): Boolean {
    return plugins.any { plugin -> setId in field(plugin) }
}

/**
 * Look at the scheme and decide if any of the set counts need to be adjusted.
 *
 * @param play The play of the scheme, in case other factors, like player count, need to be examined.
 * @param setCounts The set counts to update, if there are any changes to be made
 */
internal fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
    for (plugin in plugins) {
        if (play.scheme in plugin.schemesRange) {
            plugin.updateSetCountsFromScheme(play, setCounts)
            return
        }
    } //It is possible that the scheme is not in any plugin, but that will be caught by checkValuesInRange
}

/**
 * Get the standard number of card sets of each type that are expected for a given number of players
 *
 * @param playerCount The number of players in a play
 * @return The default number of card sets for the player count
 */
internal fun getPlayerCountRules(playerCount: PlayerCount): SetCounts {
    return when (playerCount) {
        PlayerCount.SOLO -> SetCounts(3, 1, 1, 1)
        PlayerCount.ADVANCED -> SetCounts(3, 1, 1, 1)
        PlayerCount.TWO -> SetCounts(5, 2, 1, 2)
        PlayerCount.THREE -> SetCounts(5, 3, 1,3)
        PlayerCount.FOUR -> SetCounts(5, 3, 2, 4)
        PlayerCount.FIVE -> SetCounts(6, 4, 2,5)
    }
}

/**
 * A mutable structure to store the number of card sets of each type that the play should have
 *
 * @property heroes The number of hero sets that are expected for the play
 * @property villains The number of villain sets that are expected for the play
 * @property henchmen The number of henchmen sets that are expected for the play
 * @property starters The number of starting decks for the play
 */
data class SetCounts(var heroes: Int, var villains: Int, var henchmen: Int, var starters: Int)

/**
 * Check if the play includes all card sets required by the scheme and mastermind.
 *
 * @param play The play to check
 * @return A list of [MissingRequiredSet] or [MissingRecruitSupport] containing which card set is missing
 */
internal fun checkRequiredCardSets(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()

    errors.addAll(checkAlwaysLeads(play))

    var noSupport = true
    for (plugin in plugins) {
        if (plugin.recruitSupports.intersect(play.supports).isNotEmpty()) {
            noSupport = false
            break
        }
    }

    if (noSupport) {
        errors.add(MissingRecruitSupport)
    }

    return errors
}

/**
 * Check if the play includes the mastermind's always leads group.
 *
 * @param play The play to check
 * @return A list of [MissingRequiredSet] containing which card set is missing
 */
internal fun checkAlwaysLeads(
    play: Play
): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    if (play.players !in listOf(PlayerCount.SOLO, PlayerCount.ADVANCED)) {
        val alwaysLead = getMastermindAlwaysLeads(play.mastermind)
        if (alwaysLead.isNotEmpty()) {
            var leadFound = false
            for (set in alwaysLead) {
                if (set.setType == CardSetTypes.HENCHMAN) {
                    if (set.setId in play.henchmen) {
                        leadFound = true
                        break
                    }
                } else if (set.setType == CardSetTypes.VILLAIN) {
                    if (set.setId in play.villains) {
                        leadFound = true
                        break
                    }
                }
            }

            if (!leadFound) {
                for (set in alwaysLead) {
                    errors.add(MissingRequiredSet(set.setType.name.lowercase(), set.setId))
                }
            }
        }
    }
    return errors
}

/**
 * Get the Always Leads group for a mastermind.
 *
 * @param mastermind the mastermind
 * @return The villain group(s) that the mastermind could lead, generally 1
 */
internal fun getMastermindAlwaysLeads(mastermind: Int): Set<MandatoryCardSet> {
    for (plugin in plugins) {
        if (mastermind in plugin.mastermindsRange) {
            return plugin.getAlwaysLead(mastermind)
        }
    }
    //It is possible that the mastermind is not in any plugin, but that will be caught by checkValuesInRange
    return setOf()
}
