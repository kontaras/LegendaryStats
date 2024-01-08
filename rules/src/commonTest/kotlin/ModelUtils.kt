package games.lmdbg.rules

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount
import games.lmdbg.rules.model.Release
import games.lmdbg.rules.verifier.PrintableError
import games.lmdbg.rules.verifier.TypedCardSet
import games.lmdbg.rules.verifier.ReleaseRulesPlugin
import games.lmdbg.rules.verifier.SetCounts

fun playMaker(
    hero: Int? = null,
    villain: Int? = null,
    henchman: Int? = null,
    mastermind: Int? = null,
    scheme: Int? = null,
    players: PlayerCount? = null,
    support: Int? = null,
    startingDeck: Int? = null,
    startingDeckCount: Int? = null,
    board: Int? = null,
): Play {
    return Play(
        Outcome.WIN_DEFEAT_MASTERMIND,
        players ?: PlayerCount.THREE,
        scheme ?: 0,
        mastermind ?: 0,
        if (hero != null) setOf(hero) else setOf(),
        if (villain != null) setOf(villain) else setOf(),
        if (henchman != null) setOf(henchman) else setOf(),
        if (support != null) setOf(support) else setOf(),
        if (startingDeck != null && startingDeckCount != null) mapOf(startingDeck to startingDeckCount) else mapOf(),
        board ?: 0,
    )
}

class MockRules(
    release: Release = Release(),
    recruitSupports: Int? = null
) : ReleaseRulesPlugin(release) {

    override val recruitSupports: Set<Int> = if (recruitSupports != null) setOf(recruitSupports) else setOf()

    override fun getAlwaysLead(mastermind: Int): Set<TypedCardSet> {
        return alwaysLeadsLogic(mastermind)
    }

    var alwaysLeadsLogic: (Int) -> Set<TypedCardSet> = { _ ->
        throw Exception("Should not be called")
    }

    override fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
        setCountLogic(play, setCounts)
    }

    var setCountLogic: (Play, SetCounts) -> Unit = { _, _ ->
        throw Exception("Should not be called")
    }

    override fun schemeMandatorySets(scheme: Int): Set<TypedCardSet> {
        return schemeSetsLogic(scheme)
    }

    var schemeSetsLogic: (Int) -> Set<TypedCardSet> = {  _ ->
        throw Exception("Should not be called")
    }

    override fun schemeCheckPlay(scheme: Int, play: Play): List<PrintableError> {
        return schemeCheckPlayLogic(scheme, play)
    }

    var schemeCheckPlayLogic: (scheme: Int, play: Play)-> List<PrintableError> = { _, _ ->
        throw Exception("Should not be called")
    }
}