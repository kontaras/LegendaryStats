package kon.foo.model

enum class Outcome {
    WIN,
    LOSS,
    DRAW,
    INCOMPLETE
}

enum class PlayerCount {
    SOLO,
    ADVANCED,
    TWO,
    THREE,
    FOUR,
    FIVE
}

data class Play(
    val outcome: Outcome,
    val players: PlayerCount,
    val scheme: Int,
    val mastermind: Int,
    val heroes: Set<Int>,
    val villains: Set<Int>,
    val henchmen: Set<Int>,
    val misc_hero: Int? = null
)

