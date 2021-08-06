package kon.foo.verifier

import kon.foo.model.Play
import kon.foo.model.PlayerCount

fun verify(play: Play): List<PrintableError>{
    var errors = mutableListOf<PrintableError>()
    val setCounts = getPlayerCountRules(play.players)

    if (play.heroes.size != setCounts.heroes) {
        errors.add(WrongHeroCount(setCounts.heroes, play.heroes.size))
    }

    return errors
}

public fun getPlayerCountRules(playerCount: PlayerCount): SetCounts {
    return when(playerCount) {
        PlayerCount.SOLO -> SetCounts(3, 1, 1)
        PlayerCount.ADVANCED -> SetCounts(3, 1, 1)
        PlayerCount.TWO -> SetCounts(5,2,1)
        PlayerCount.THREE -> SetCounts(5,3,1)
        PlayerCount.FOUR -> SetCounts(5,3,2)
        PlayerCount.FIVE -> SetCounts(6,4,2)
    }
}

data class SetCounts(var heroes: Int, var villains: Int, var henchmen: Int)

public interface PrintableError {
    fun getMessage(): String
}

class WrongHeroCount(val expected: Int, val actual: Int) : PrintableError {
    override fun getMessage(): String {
        return "Expected to provide $expected heroes, got $actual"
    }

    override fun equals(other: Any?): Boolean {
        return (other is WrongHeroCount) && this.actual == other.actual && this.expected == other.expected
    }

    override fun hashCode(): Int {
        val largeEnoughPrime = 13
        return actual * largeEnoughPrime + expected
    }
}