package games.lmdbg.rules

import games.lmdbg.rules.model.Outcome
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.PlayerCount

fun playMaker(
    hero: Int? = null,
    villain: Int? = null,
    henchman: Int? = null,
    mastermind: Int? = null,
    scheme: Int? = null,
    players: PlayerCount? = null
): Play {
    return Play(
        Outcome.WIN,
        players ?: PlayerCount.THREE,
        scheme ?: 0,
        mastermind ?: 0,
        if (hero != null) setOf(hero) else setOf(),
        if (villain != null) setOf(villain) else setOf(),
        if (henchman != null) setOf(henchman) else setOf()
    )
}