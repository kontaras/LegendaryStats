package games.lmdbg.rules.verifier

/** A list of all the release rules engine plugins */
val plugins: MutableSet<ReleaseRulesPlugin> = mutableSetOf()

/** Data about each release that the rules engine needs to validate sets including that release */
interface ReleaseRulesPlugin {

    /** The range of valid ID numbers for hero sets */
    val heroesRange: IntRange
}