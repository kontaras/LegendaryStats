package kon.foo.verifier

import kon.foo.model.Play
import kon.foo.model.PlayerCount

fun verify(play: Play): List<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    val setCounts = getPlayerCountRules(play.players)

    errors.addAll(checkSetSizes(play, setCounts))

    return errors
}

private fun checkSetSizes(play: Play, setCounts: SetCounts,): MutableList<PrintableError> {
    val errors = mutableListOf<PrintableError>()
    if (play.heroes.size != setCounts.heroes) {
        errors.add(WrongSetCount("hero", setCounts.heroes, play.heroes.size))
    }

    if (play.villains.size != setCounts.villains) {
        errors.add(WrongSetCount("villain", setCounts.villains, play.villains.size))
    }

    if (play.henchmen.size != setCounts.henchmen) {
        errors.add(WrongSetCount("henchman", setCounts.henchmen, play.henchmen.size))
    }

    return errors
}

fun getPlayerCountRules(playerCount: PlayerCount): SetCounts {
    return when (playerCount) {
        PlayerCount.SOLO -> SetCounts(3, 1, 1)
        PlayerCount.ADVANCED -> SetCounts(3, 1, 1)
        PlayerCount.TWO -> SetCounts(5, 2, 1)
        PlayerCount.THREE -> SetCounts(5, 3, 1)
        PlayerCount.FOUR -> SetCounts(5, 3, 2)
        PlayerCount.FIVE -> SetCounts(6, 4, 2)
    }
}

data class SetCounts(var heroes: Int, var villains: Int, var henchmen: Int)
