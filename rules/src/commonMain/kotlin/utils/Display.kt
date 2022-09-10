package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.PrintableError

fun errorsToString(errors:List<PrintableError>) : String {
    return errors.map{it.getMessage()}.joinToString("\n")
}