package games.lmdbg.rules.model

open class Nameable (
    /** Name of the thing in comic art releases */
    val artName: String?,
    /** Name of the thing in Legendary DXP releases */
    val dxpName: String?,
    /** Name of the thing in movie still releases */
    val mcuName: String?
)

class Release (
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null,
    /** Heroes in the release */
    val heroes: List<Hero> = listOf(),
    /** Schemes in the release */
    val schemes: List<Scheme> = listOf(),
    /** Masterminds in the release */
    val masterminds: List<Mastermind> = listOf(),
    /** Villains in the release */
    val villains: List<Villain> = listOf(),
    /** Henchmen in the release, if any */
    val henchmen: List<Henchman> = listOf(),
    /** Supports in the release, if any */
    val supports: List<Support> = listOf(),
    /** Starters in the release, if any */
    val starters: List<Starter> = listOf(),
    /** Boards in the release, if any */
    val boards: List<Board> = listOf()
) : Nameable(artName, dxpName, mcuName)

open class CardSet (
    /** ID of the card set */
    val id: Int,
    /** Name of the thing in comic art releases */
    artName: String?,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String?,
    /** Name of the thing in movie still releases */
    mcuName: String?
) : Nameable(artName, dxpName, mcuName) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as CardSet

        return id == other.id
    }

    override fun hashCode(): Int {
        return id
    }
}

class Scheme (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Mastermind (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Hero (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null,
    /** Team(s) that the hero is part of */
    val team: List<Team> = listOf()
) : CardSet(id, artName, dxpName, mcuName)

class Team (
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : Nameable(artName, dxpName, mcuName)

class Villain (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Henchman (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Support (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Starter (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)

class Board (
    /** ID of the card set */
    id: Int,
    /** Name of the thing in comic art releases */
    artName: String? = null,
    /** Name of the thing in Legendary DXP releases */
    dxpName: String? = null,
    /** Name of the thing in movie still releases */
    mcuName: String? = null
) : CardSet(id, artName, dxpName, mcuName)
