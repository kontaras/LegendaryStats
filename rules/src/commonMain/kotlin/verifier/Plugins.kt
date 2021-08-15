package kon.foo.verifier

val plugins: MutableSet<ReleaseRulesPlugin> = mutableSetOf()

interface ReleaseRulesPlugin {
    val heroesRange: IntRange
}