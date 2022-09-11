package games.lmdbg.rules.utils

import games.lmdbg.rules.verifier.PrintableError
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.CardSetType

fun errorsToString(errors:List<PrintableError>,
    heroSets:Map<Int, String>, 
    henchmenSets:Map<Int, String>,
    villainSets:Map<Int, String>,
    starterSets:Map<Int, String>,
    supportSets:Map<Int, String>,
    schemeSets:Map<Int, String>,
    mastermindSets:Map<Int, String>,
    boardSets:Map<Int, String>) : List<String> {
    return errors.map{
        it.getMessage() + cardSetsToString(it.getCardSets(), heroSets, henchmenSets, villainSets, starterSets,
        supportSets, schemeSets, mastermindSets, boardSets)
        }
}

fun cardSetsToString(sets:List<TypedCardSet>,
    heroSets:Map<Int, String>, 
    henchmenSets:Map<Int, String>,
    villainSets:Map<Int, String>,
    starterSets:Map<Int, String>,
    supportSets:Map<Int, String>,
    schemeSets:Map<Int, String>,
    mastermindSets:Map<Int, String>,
    boardSets:Map<Int, String>) : String {
    if (sets.isEmpty()) {
        return ""    
    } else {
        return " " + sets.map {
            when(it.setType) {
                CardSetType.HERO -> heroSets
                CardSetType.HENCHMAN -> henchmenSets
                CardSetType.VILLAIN -> villainSets
                CardSetType.STARTER -> starterSets
                CardSetType.SUPPORT -> supportSets
                CardSetType.SCHEME -> schemeSets
                CardSetType.MASTERMIND -> mastermindSets
                CardSetType.BOARD -> boardSets
            }.get(it.setId)}.joinToString(", ")
    }
}