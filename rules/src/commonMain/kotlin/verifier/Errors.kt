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
class WrongSetCount(val setType: String, val expected: Int, val actual: Int) : PrintableError {
    override fun getMessage(): String {
        return "Expected to provide $expected $setType sets, got $actual"
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
class InvalidCardSet(val setType: String, val id: Int) : PrintableError {
    override fun getMessage(): String {
        return "Invalid $setType: $id"
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
class MissingRequiredSet(val setType: String, val setId: Int): PrintableError {
    override fun getMessage(): String {
        return "Missing required $setType $setId"
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



object MissingRecruitSupport: PrintableError {
    override fun getMessage(): String {
        return "A setup needs to include a recruit support."
    }
}
