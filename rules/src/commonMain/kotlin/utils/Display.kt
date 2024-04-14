package games.lmdbg.rules.utils

import games.lmdbg.rules.model.CardSet
import games.lmdbg.rules.set.*
import games.lmdbg.rules.verifier.PrintableError
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.CardSetType
import kotlin.js.JsExport

/**
 * Converts a list of [PrintableError] to their String representations
 *
 * @param errors to convert
 * @param cardMap Two layer map to lookup card names. It is a mapping of [CardSetType.toString] values to lookup tables that map id to card name.
 * @return The String representations of errors, in the same order as they were passed in.
 */
@JsExport
fun errorsToString(
    errors: List<PrintableError>
): List<String> {
    return errors.map {
        it.getMessage().replace(
            PrintableError.CARDS_PLACEHOLDER, cardSetsToString(
                it.getCardSets() )
        )
    }
}

/**
 * Converts [TypedCardSet] to human-readable strings by looking them up.
 *
 * @param sets Card sets to look up
 * @return The card sets looked up and concatenated as comma separated values
 */
internal fun cardSetsToString(
    sets: List<TypedCardSet>
): String {
    return sets.joinToString(", ") {
        printThing(when (it.setType) {
            CardSetType.HERO -> heroesById
            CardSetType.HENCHMAN -> henchmanById
            CardSetType.VILLAIN -> villainsById
            CardSetType.STARTER -> startersById
            CardSetType.SUPPORT -> supportsById
            CardSetType.SCHEME -> schemesById
            CardSetType.MASTERMIND -> mastermindsById
            CardSetType.BOARD -> boardsById
        }[it.setId]) ?: "${it.setType.toString().lowercase()} ${it.setId}"
    }
}

internal fun printThing(set: CardSet?): String? {
    return if (set == null) null
    else set.artName ?: (set.mcuName ?: set.dxpName)
}
