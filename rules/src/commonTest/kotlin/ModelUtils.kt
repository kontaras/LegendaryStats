package games.lmdbg.rules

import games.lmdbg.rules.model.*
import games.lmdbg.rules.verifier.*

class PlayBuilder(

) {
    private val play = Play()

    fun addHero(id: Int): PlayBuilder {
        play.heroes.add(id)
        return this
    }

    fun addVillain(id: Int): PlayBuilder {
        play.villains.add(id)
        return this
    }

    fun addHenchman(id: Int): PlayBuilder {
        play.henchmen.add(id)
        return this
    }

    fun setScheme(id: Int): PlayBuilder {
        play.scheme = id
        return this
    }

    fun setMastermind(id: Int): PlayBuilder {
        play.mastermind = id
        return this
    }

    fun setBoard(id: Int): PlayBuilder {
        play.board = id
        return this
    }

    fun addSupport(id: Int): PlayBuilder {
        play.supports.add(id)
        return this
    }

    fun addStarter(id: Int, quantity: Int): PlayBuilder {
        play.starters.put(id, quantity)
        return this
    }

    fun setPlayers(players: PlayerCount): PlayBuilder {
        play.players = players
        return this
    }

    fun build(): Play {
        return play
    }
}

class MockRules(
    release: Release = Release(),
    recruitSupports: Int? = null
) : ReleaseRulesPlugin(release) {

    override val recruitSupports: Set<Int> = if (recruitSupports != null) setOf(recruitSupports) else setOf()

    override fun getAlwaysLead(mastermind: Int): Set<TypedCardSet> {
        return alwaysLeadsLogic(mastermind)
    }

    var alwaysLeadsLogic: (Int) -> Set<TypedCardSet> = { _ ->
        throw Exception("Should not be called")
    }

    override fun updateSetCountsFromScheme(play: Play, setCounts: SetCounts) {
        setCountLogic(play, setCounts)
    }

    var setCountLogic: (Play, SetCounts) -> Unit = { _, _ ->
        throw Exception("Should not be called")
    }

    override fun schemeMandatorySets(scheme: Int): Set<TypedCardSet> {
        return schemeSetsLogic(scheme)
    }

    var schemeSetsLogic: (Int) -> Set<TypedCardSet> = {  _ ->
        throw Exception("Should not be called")
    }

    override fun schemeCheckPlay(scheme: Int, play: Play): List<PrintableError> {
        return schemeCheckPlayLogic(scheme, play)
    }

    var schemeCheckPlayLogic: (scheme: Int, play: Play)-> List<PrintableError> = { _, _ ->
        throw Exception("Should not be called")
    }
}