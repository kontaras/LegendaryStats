package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import org.lighthousegames.logging.logging
import kotlin.js.JsName

val log = logging()

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
    errors.addAll(checkPlayValidForScheme(play))

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
        errors.add(WrongSetCount(CardSetType.HERO, setCounts.heroes, play.heroes.size))
    }

    if (play.villains.size != setCounts.villains) {
        errors.add(WrongSetCount(CardSetType.VILLAIN, setCounts.villains, play.villains.size))
    }

    if (play.henchmen.size != setCounts.henchmen) {
        errors.add(WrongSetCount(CardSetType.HENCHMAN, setCounts.henchmen, play.henchmen.size))
    }

    var totalStarters = 0
    for ((setId, deckCount) in play.starters) {
        if (deckCount < 0) {
            errors.add(InvalidCardQuantity(TypedCardSet(CardSetType.STARTER, setId), deckCount))
        }
        totalStarters += deckCount
    }
    if (totalStarters != setCounts.starters) {
        errors.add(WrongSetCount(CardSetType.STARTER, setCounts.starters, totalStarters))
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
            errors.add(InvalidCardSet(CardSetType.HERO, hero))
        }
    }

    for (villain in play.villains) {
        if (!checkValidValue(villain, ReleaseRulesPlugin::villainsRange)) {
            errors.add(InvalidCardSet(CardSetType.VILLAIN, villain))
        }
    }

    for (henchman in play.henchmen) {
        if (!checkValidValue(henchman, ReleaseRulesPlugin::henchmenRange)) {
            errors.add(InvalidCardSet(CardSetType.HENCHMAN, henchman))
        }
    }

    for (support in play.supports) {
        if (!checkValidValue(support, ReleaseRulesPlugin::supportCardRange)) {
            errors.add(InvalidCardSet(CardSetType.SUPPORT, support))
        }
    }

    if (!checkValidValue(play.scheme, ReleaseRulesPlugin::schemesRange)) {
        errors.add(InvalidCardSet(CardSetType.SCHEME, play.scheme))
    }

    if (!checkValidValue(play.mastermind, ReleaseRulesPlugin::mastermindsRange)) {
        errors.add(InvalidCardSet(CardSetType.MASTERMIND, play.mastermind))
    }

    for (starter in play.starters.keys) {
        if (!checkValidValue(starter, ReleaseRulesPlugin::starterRange)) {
            errors.add(InvalidCardSet(CardSetType.STARTER, starter))
        }
    }

    if (!checkValidValue(play.board, ReleaseRulesPlugin::boardRange)) {
        errors.add(InvalidCardSet(CardSetType.BOARD, play.board))
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
        PlayerCount.ADVANCED_SOLO -> SetCounts(3, 1, 1, 1)
        PlayerCount.TWO -> SetCounts(5, 2, 1, 2)
        PlayerCount.THREE -> SetCounts(5, 3, 1, 3)
        PlayerCount.FOUR -> SetCounts(5, 3, 2, 4)
        PlayerCount.FIVE -> SetCounts(6, 4, 2, 5)
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

    val requiredSets = mutableListOf<Set<TypedCardSet>>()
    if (play.players !in listOf(PlayerCount.SOLO, PlayerCount.ADVANCED_SOLO)) {
        requiredSets.add(getMastermindAlwaysLeads(play.mastermind))
    }

    requiredSets.add(getSchemeRequiredSets(play.scheme))

    errors.addAll(checkMandatorySets(play, requiredSets))

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
 * Check if the play includes all of the required sets
 *
 * @param play The play to check
 * @param sets Card sets to look for
 * @return A list of [MissingRequiredSet] containing which card sets are missing
 */
internal fun checkMandatorySets(play: Play, sets: List<Set<TypedCardSet>>): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    for (set in sets) {
        if (set.isEmpty()) {
            continue
        }

        var leadFound = false
        for (card in set) {
            val usedCards = when (card.setType) {
                CardSetType.VILLAIN -> play.villains
                CardSetType.HENCHMAN -> play.henchmen
                else -> {
                    log.error { "No logic to check for a mandatory set of ${card.setType}" }
                    setOf()
                }
            }
            if (card.setId in usedCards) {
                leadFound = true
            }
        }

        if (!leadFound) {
            errors.add(MissingRequiredSet(set.toList().sortedBy { it.toString() })) //Sort for ease of tests
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
internal fun getMastermindAlwaysLeads(mastermind: Int): Set<TypedCardSet> {
    for (plugin in plugins) {
        if (mastermind in plugin.mastermindsRange) {
            return plugin.getAlwaysLead(mastermind)
        }
    }
    //It is possible that the mastermind is not in any plugin, but that will be caught by checkValuesInRange
    return setOf()
}

/**
 * Get any card sets the scheme mandates
 *
 * @param scheme the scheme
 * @return The card set the scheme requires, if any
 */
internal fun getSchemeRequiredSets(scheme: Int): Set<TypedCardSet> {
    for (plugin in plugins) {
        if (scheme in plugin.schemesRange) {
            return plugin.schemeMandatorySets(scheme)
        }
    }
    //It is possible that the scheme is not in any plugin, but that will be caught by checkValuesInRange
    return setOf()
}

internal fun checkPlayValidForScheme(play: Play): List<PrintableError> {
    val scheme = play.scheme
    for (plugin in plugins) {
        if (scheme in plugin.schemesRange) {
             return plugin.schemeCheckPlay(scheme, play)
        }
    }
    return listOf()
}
