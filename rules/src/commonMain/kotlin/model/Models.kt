package games.lmdbg.rules.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.js.JsExport
import kotlin.js.JsName

/** The possible game outcomes */
enum class Outcome {
    /** Players won by defeating the mastermind */
    WIN_DEFEAT_MASTERMIND,

    /** Players lost due to the scheme */
    LOSS_SCHEME,

    /** Game was a draw (hero or villain deck ran out) */
    DRAW_DECK
}

/** Possible player counts */
enum class PlayerCount {
    SOLO,
    ADVANCED_SOLO,
    TWO,
    THREE,
    FOUR,
    FIVE
}

/** All of the data about a play that can be validated */
@Serializable
@JsExport
open class Play(
    /** How did the game end? */
    var outcome: Outcome,
    /** How many players where there? */
    var players: PlayerCount,
    /** Which scheme was used? */
    var scheme: Int,
    /** What was the mastermind faced? */
    var mastermind: Int,
    /** What heroes made up the hero deck? */
    var heroes: Set<Int>,
    /** What villain groups where in the villain deck? */
    var villains: Set<Int>,
    /** What henchmen villain groups where in the villain deck? */
    var henchmen: Set<Int>,
    /** What support cards where included? */
    var supports: Set<Int>,
    /** How many of each starting decks was used in the play? */
    var starters: Map<Int, Int>,
    /** What game board was used? */
    var board: Int
) {


    override fun toString(): String {
        return "Play(outcome=$outcome, players=$players, scheme=$scheme, mastermind=$mastermind, heroes=$heroes, villains=$villains, henchmen=$henchmen, supports=$supports, starters=$starters, board=$board)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as Play

        if (outcome != other.outcome) return false
        if (players != other.players) return false
        if (scheme != other.scheme) return false
        if (mastermind != other.mastermind) return false
        if (heroes != other.heroes) return false
        if (villains != other.villains) return false
        if (henchmen != other.henchmen) return false
        if (supports != other.supports) return false
        if (starters != other.starters) return false
        if (board != other.board) return false

        return true
    }

    override fun hashCode(): Int {
        var result = outcome.hashCode()
        result = 31 * result + players.hashCode()
        result = 31 * result + scheme
        result = 31 * result + mastermind
        result = 31 * result + heroes.hashCode()
        result = 31 * result + villains.hashCode()
        result = 31 * result + henchmen.hashCode()
        result = 31 * result + supports.hashCode()
        result = 31 * result + starters.hashCode()
        result = 31 * result + board
        return result
    }

    companion object {
        fun playFromString(encoded: String): Play {
            return Json.decodeFromString(encoded)
        }

        fun playToString(play: Play): String {
            return Json.encodeToString(play)
        }

        fun newPlay(): Play {
            return Play(Outcome.DRAW_DECK, PlayerCount.SOLO, -1, -1, setOf(), setOf(),setOf(),setOf(), mapOf(), -1)
        }
    }
}

