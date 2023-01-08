package games.lmdbg.rules.utils

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
    errors: List<PrintableError>,
    cardMap: Map<String, Map<Int, String>>
): List<String> {
    return errors.map {
        it.getMessage().replace(
            PrintableError.CARDS_PLACEHOLDER, cardSetsToString(
                it.getCardSets(), cardMap )
        )
    }
}

@JsExport
fun errorsToStringJsHack(errors: List<PrintableError>
): String {
    return errorsToString(errors, mapOf()).joinToString("\n")
}


/**
 * Converts [TypedCardSet] to human-readable strings by looking them up.
 *
 * @param sets Card sets to look up
 * @param cardMap Two layer map to lookup card names. It is a mapping of [CardSetType.toString] values to lookup tables that map id to card name.
 * @return The card sets looked up and concatenated as comma separated values
 */
internal fun cardSetsToString(
    sets: List<TypedCardSet>,
    cardMap: Map<String, Map<Int, String>>
): String {
    return sets.joinToString(", ") {
        cardMap[it.setType.toString()]?.get(it.setId) ?: "${it.setType} ${it.setId}"
    }
}
