package games.lmdbg.rules.verifier

/** The common error class for all issues that can be found when validating plays */
interface PrintableError {
    /* A human-readable explanation of the error */
    fun getMessage(): String

    /** Return any card sets related to the error. The caller can then map them to card set names. */
    fun getCardSets(): List<TypedCardSet> {
        return listOf()
    }
}

/**
 * A card set has the wrong number of cards
 *
 * @property setType The card set type
 * @property expected How many card sets of the type are expected
 * @property actual How many card sets are in the play
 */
class WrongSetCount(val setType: CardSetType, val expected: Int, val actual: Int) : PrintableError {
    override fun getMessage(): String {
        return "Expected to provide $expected ${setType.toString().lowercase()} sets, got $actual"
    }

    override fun equals(other: Any?): Boolean {
        return (other is WrongSetCount)
                && this.setType == other.setType
                && this.actual == other.actual
                && this.expected == other.expected
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "WrongSetCount $setType: exp $expected, act $actual"
    }
}

/**
 * A card type does not have a set with a given ID
 *
 * @property setType The type of set it is
 * @property id The invalid ID
 */
class InvalidCardSet(val setType: CardSetType, val id: Int) : PrintableError {
    override fun getMessage(): String {
        return "Invalid ${setType.toString().lowercase()}: $id"
    }

    override fun equals(other: Any?): Boolean {
        return (other is InvalidCardSet)
                && this.setType == other.setType
                && this.id == other.id
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "InvalidCardSet $setType $id"
    }
}

/**
 * A card set that is required by the setup is not present.
 *
 * @property cards The ID of the set that is expected
 */
class MissingRequiredSet(val cards: List<TypedCardSet>) : PrintableError {
    override fun getMessage(): String {
        return "Missing required card sets ${cardsToString()}"
    }

    override fun getCardSets(): List<TypedCardSet> {
        return cards
    }

    override fun equals(other: Any?): Boolean {
        return (other is MissingRequiredSet)
                && this.cards == other.cards
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "MissingRequiredSet ${cardsToString()}"
    }

    private fun cardsToString(): String {
        return cards.joinToString(", ", transform = TypedCardSet::repr)
    }
}

/**
 * The play contains an invalid number for the quantity of a card.
 *
 * @property setId  The card set
 * @property quantity The invalid quantity
 */
class InvalidCardQuantity(val setId: TypedCardSet, val quantity: Int) : PrintableError {
    override fun getMessage(): String {
        return "Invalid quantity of ${setId.setType.toString().lowercase()}: $quantity"
    }

    override fun getCardSets(): List<TypedCardSet> {
        return listOf(setId)
    }

    override fun equals(other: Any?): Boolean {
        return (other is InvalidCardQuantity)
                && this.setId == other.setId
                && this.quantity == other.quantity
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "InvalidCardQuantity $setId $quantity"
    }
}

/**
 * A play does not include a recruit support (such as SHIELD Agent or Madame HYDRA)
 */
object MissingRecruitSupport : PrintableError {
    override fun getMessage(): String {
        return "A setup needs to include a recruit granting support."
    }
}

object PlayerSchemeMismatch : PrintableError {
    override fun getMessage(): String {
        return "The scheme you selected is not playable with the selected player count."
    }
}

fun TypedCardSet.repr():String {
    return "($setType $setId)"
}
