package games.lmdbg.rules.verifier

/** The common error class for all issues that can be found when validating plays */
interface PrintableError {
    /* A human-readable explanation of the error */
    fun getMessage(): String
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
 * @property setType The type of card set that is missing
 * @property setId The ID of the set that is expected
 */
class MissingRequiredSet(val setType: CardSetType, val setId: Int): PrintableError {
    override fun getMessage(): String {
        return "Missing required ${setType.toString().lowercase()} $setId"
    }

    override fun equals(other: Any?): Boolean {
        return (other is MissingRequiredSet)
                && this.setId == other.setId
                && this.setType == other.setType
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "MissingRequiredSet $setType $setId"
    }
}

class InvalidCardQuantity(val setType: CardSetType, val setId: Int, val quantity: Int): PrintableError {
    override fun getMessage(): String {
        return "Invalid quantity of ${setType.toString().lowercase()} $setId: $quantity"
    }

    override fun equals(other: Any?): Boolean {
        return (other is InvalidCardQuantity)
                && this.setId == other.setId
                && this.setType == other.setType
                && this.quantity == other.quantity
    }

    override fun hashCode(): Int {
        return getMessage().hashCode()
    }

    override fun toString(): String {
        return "InvalidCardQuantity $setType $setId $quantity"
    }
}

object MissingRecruitSupport: PrintableError {
    override fun getMessage(): String {
        return "A setup needs to include a recruit granting support."
    }
}
