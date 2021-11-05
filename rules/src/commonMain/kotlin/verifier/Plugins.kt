package games.lmdbg.rules.verifier

import games.lmdbg.rules.model.Play
import games.lmdbg.rules.set.base.Rules

/** A list of all the release rules engine plugins */
val plugins: MutableSet<ReleaseRulesPlugin> = mutableSetOf(Rules())

/** Data about each release that the rules engine needs to validate sets including that release */
interface ReleaseRulesPlugin {
    /** The range of valid ID numbers for hero sets */
    val heroesRange: IntRange

    /** The range of valid ID numbers for villain sets */
    val villainsRange: IntRange

    /** The range of valid ID numbers for henchman villain sets */
    val henchmenRange: IntRange

    /** The range of valid ID numbers for schemes */
    val schemesRange: IntRange

    /** The range of valid ID numbers for masterminds */
    val mastermindsRange: IntRange

    /** The range of valid ID numbers for support cards, if any */
    val supportCardRange: IntRange

    /** The range of valid ID numbers for starter decks, if any */
    val starterRange: IntRange

    /** The recruit granting supports for this set */
    val recruitSupports: Set<Int>

    /**
     * Update count of card sets required based on the scheme.
     *
     * @param play A play including this scheme.
     *      Precondition: [Play.scheme] in [schemesRange]
     * @param setCounts The set counts to update (if applicable)
     */
    fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts)

    /**
     * Get the Always Leads group for a mastermind in the set.
     *
     * @param mastermind A mastermind in [mastermindsRange]
     * @return The villain group(s) that the mastermind could lead, generally 1
     */
    fun getAlwaysLead(mastermind: Int): Set<TypedCardSet>

    fun schemeMandatorySets(scheme: Int): Set<TypedCardSet>
}

/** Card set types in the game that can be a [TypedCardSet] */
enum class CardSetType {
    HERO,
    HENCHMAN,
    VILLAIN,
    STARTER,
    SUPPORT,
    SCHEME,
    MASTERMIND
}

/**
 * An identifier for a card set that includes the card type.
 *
 * @property setType The type of card set
 * @property setId Set id of the cards
 */
data class TypedCardSet(val setType: CardSetType, val setId: Int)