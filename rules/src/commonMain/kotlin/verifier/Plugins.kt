package games.lmdbg.rules.verifier

val plugins: MutableSet<ReleaseRulesPlugin> = mutableSetOf()

interface ReleaseRulesPlugin {
    val heroesRange: IntRange
}