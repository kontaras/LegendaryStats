package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount

/**
 * Check if a given play follows all game setup rules
 *
 * @param play The play to verify
 * @return A list of issues with this play. Will be empty if there are no issues.
 */
fun verify(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    val setCounts = getPlayerCountRules(play.players)

    errors.addAll(checkCardSetSizes(play, setCounts))

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

    return errors
}

/**
 * Check if each card set index in a play is a valid card set id.
 *
 * @param play The play to check
 * @return [InvalidCardSet] for each invalid card set id
 */
fun checkValuesInRange(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()

    for (hero in play.heroes) {
        if (!checkValidHero(hero)) {
            errors.add(InvalidCardSet("hero", hero))
        }
    }

    //TODO Check other sets

    return errors
}

/**
 * Check if a given hero id is a valid one
 *
 * @param hero Hero ID to check
 * @return true if there is a set that contains that id, false otherwise
 */
private fun checkValidHero(hero: Int): Boolean {
    for (plugin in plugins) {
        if (hero in plugin.heroesRange) {
            return true
        }
    }

    return false
}

/**
 * Get the standard number of card sets of each type that are expected for a given number of players
 *
 * @param playerCount The number of players in a play
 * @return The default number of card sets for the player count
 */
fun getPlayerCountRules(playerCount: PlayerCount): SetCounts {
    return when (playerCount) {
        PlayerCount.SOLO -> SetCounts(3, 1, 1)
        PlayerCount.ADVANCED -> SetCounts(3, 1, 1)
        PlayerCount.TWO -> SetCounts(5, 2, 1)
        PlayerCount.THREE -> SetCounts(5, 3, 1)
        PlayerCount.FOUR -> SetCounts(5, 3, 2)
        PlayerCount.FIVE -> SetCounts(6, 4, 2)
    }
}

/**
 * A mutable structure to store the number of card sets of each type that the play should have
 *
 * @property heroes The number of hero sets that are expected for the play
 * @property villains The number of villain sets that are expected for the play
 * @property henchmen The number of henchmen sets that are expected for the play
 */
data class SetCounts(var heroes: Int, var villains: Int, var henchmen: Int)

