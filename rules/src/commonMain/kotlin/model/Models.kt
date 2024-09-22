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
@JsExport
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
    var outcome: Outcome? = null,
    /** How many players where there? */
    var players: PlayerCount?= null,
    /** Which scheme was used? */
    var scheme: Int?= null,
    /** What was the mastermind faced? */
    var mastermind: Int?= null,
    /** What heroes made up the hero deck? */
    var heroes: MutableSet<Int> = mutableSetOf(),
    /** What villain groups where in the villain deck? */
    var villains: MutableSet<Int> = mutableSetOf(),
    /** What henchmen villain groups where in the villain deck? */
    var henchmen: MutableSet<Int> = mutableSetOf(),
    /** What support cards where included? */
    var supports: MutableSet<Int> = mutableSetOf(),
    /** How many of each starting decks was used in the play? */
    var starters: MutableMap<Int, Int> = mutableMapOf(),
    /** What game board was used? */
    var board: Int?= null
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
        var result = outcome?.hashCode() ?: 0
        result = 31 * result + (players?.hashCode() ?: 0)
        result = 31 * result + (scheme ?: 0)
        result = 31 * result + (mastermind ?: 0)
        result = 31 * result + heroes.hashCode()
        result = 31 * result + villains.hashCode()
        result = 31 * result + henchmen.hashCode()
        result = 31 * result + supports.hashCode()
        result = 31 * result + starters.hashCode()
        result = 31 * result + (board ?: 0)
        return result
    }

    companion object {
        fun playFromString(encoded: String): Play {
            return Json.decodeFromString(encoded)
        }

        fun playToString(play: Play): String {
            return Json.encodeToString(play)
        }

        /**
         * Utility function to create a Kotlin [Set] from JavaScript.
         */
        fun <TValue> createSetFromJsArray(array: Array<TValue>) = array.toSet()
    }
}

