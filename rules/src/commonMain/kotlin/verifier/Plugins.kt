package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.CardSet
import games.lmdbg.rules.model.Play
import games.lmdbg.rules.model.Release
import games.lmdbg.rules.set.core.Rules
import kotlinx.serialization.Serializable

/** A list of all the release rules engine plugins */
val plugins: MutableSet<ReleaseRulesPlugin> = mutableSetOf(Rules())

/** Data about each release that the rules engine needs to validate sets including that release */
abstract class ReleaseRulesPlugin(release: Release) {
    /** The range of valid ID numbers for schemes */
    val schemesRange: IntRange = cardSetsRange(release.schemes)

    /** The range of valid ID numbers for masterminds */
    val mastermindsRange: IntRange = cardSetsRange(release.masterminds)

    /** The recruit granting supports for this set */
    abstract val recruitSupports: Set<Int>

    /**
     * Update count of card sets required based on the scheme.
     *
     * @param play A play including this scheme.
     *      Precondition: [Play.scheme] in [schemesRange]
     * @param setCounts The set counts to update (if applicable)
     */
    abstract fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts)

    /**
     * Get the Always Leads group for a mastermind in the set.
     *
     * @param mastermind A mastermind in [mastermindsRange]
     * @return The villain group(s) that the mastermind could lead, generally 1
     */
    abstract fun getAlwaysLead(mastermind: Int): Set<TypedCardSet>

    /**
     * Get any required card sets for a scheme in the set.
     *
     * @param scheme A scheme in [schemesRange]
     * @return The card sets required for the scheme, if any
     */
    abstract fun schemeMandatorySets(scheme: Int): Set<TypedCardSet>

    /**
     * Check to see if a play is valid for a scheme.
     *
     * @param play The play to check
     * @param scheme The scheme that the play should comply to
     * @return Issues, if any, in the scheme
     */
    abstract fun schemeCheckPlay(scheme: Int, play: Play): List<PrintableError>

    companion object {
        fun cardSetsRange(sets: List<CardSet>): IntRange {
            if (sets.isEmpty()) {
                return IntRange.EMPTY
            }
            val firstId = sets.first().id
            var minId = firstId
            var maxId = firstId
            for (set in sets.drop(1)) {
                val id = set.id
                if (id < minId) {
                    minId = id
                } else if (id > maxId) {
                    maxId = id
                }
            }

            return minId..maxId
        }
    }
}

/** Card set types in the game that can be a [TypedCardSet] */
@Serializable
enum class CardSetType {
    HERO,
    HENCHMAN,
    VILLAIN,
    STARTER,
    SUPPORT,
    SCHEME,
    MASTERMIND,
    BOARD
}

/**
 * An identifier for a card set that includes the card type.
 *
 * @property setType The type of card set
 * @property setId Set id of the cards
 */
@Serializable
data class TypedCardSet(val setType: CardSetType, val setId: Int)