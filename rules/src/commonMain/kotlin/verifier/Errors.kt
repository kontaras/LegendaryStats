package games.lmdbg.rules.verifier

import kotlinx.serialization.Serializable
import kotlinx.serialization.Polymorphic
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import kotlinx.serialization.modules.subclass
import kotlin.js.JsExport

/**
 * The common error class for all issues that can be found when validating plays.
 *
 * Note: When implementing an error that needs to be sent between the server and client, add it to [errorSerializer]
 */
@Polymorphic
interface PrintableError {
    companion object {
        const val CARDS_PLACEHOLDER = "%CARDS%"
    }

    /* A human-readable explanation of the error */
    fun getMessage(): String

    /** Return any card sets related to the error. The caller can then map them to card set names. */
    fun getCardSets(): List<TypedCardSet> {
        return listOf()
    }
}

/**
 * Serializer for all [PrintableError] types
 */
@JsExport
val errorSerializer = Json {
    serializersModule = SerializersModule {
        polymorphic(PrintableError::class) {
            subclass(WrongSetCount::class)
            subclass(InvalidCardSet::class)
            subclass(MissingRequiredSet::class)
            subclass(InvalidCardQuantity::class)
            subclass(MissingRecruitSupport::class)
            subclass(PlayerSchemeMismatch::class)
            subclass(InvalidValue::class)
        }
    }
}

/**
 * A card set has the wrong number of cards
 *
 * @property setType The card set type
 * @property expected How many card sets of the type are expected
 * @property actual How many card sets are in the play
 */
@Serializable
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
        return toString().hashCode()
    }

    override fun toString(): String {
        return "WrongSetCount $setType: exp $expected, act $actual"
    }
}

@Serializable
class InvalidValue(val setType: String, val value: String) : PrintableError {
    override fun getMessage(): String {
        return "Invalid value provided for $setType: $value"
    }

    override fun equals(other: Any?): Boolean {
        return (other is InvalidValue)
                && this.setType == other.setType
                && this.value == other.value
    }

    override fun hashCode(): Int {
        return toString().hashCode()
    }

    override fun toString(): String {
        return "InvalidValue $setType: $value"
    }
}

/**
 * A card type does not have a set with a given ID
 *
 * @property setType The type of set it is
 * @property id The invalid ID
 */
@Serializable
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
        return toString().hashCode()
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
@Serializable
class MissingRequiredSet(val cards: List<TypedCardSet>) : PrintableError {
    override fun getMessage(): String {
        return "Missing required card sets ${PrintableError.CARDS_PLACEHOLDER}"
    }

    override fun getCardSets(): List<TypedCardSet> {
        return cards
    }

    override fun equals(other: Any?): Boolean {
        return (other is MissingRequiredSet)
                && this.cards == other.cards
    }

    override fun hashCode(): Int {
        return toString().hashCode()
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
@Serializable
class InvalidCardQuantity(val setId: TypedCardSet, val quantity: Int) : PrintableError {
    override fun getMessage(): String {
        return "Invalid quantity of ${PrintableError.CARDS_PLACEHOLDER}: $quantity"
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
        return toString().hashCode()
    }

    override fun toString(): String {
        return "InvalidCardQuantity ${setId.repr()} $quantity"
    }
}

/**
 * A play does not include a recruit support (such as SHIELD Agent or Madame HYDRA)
 */
@Serializable
object MissingRecruitSupport : PrintableError {
    override fun getMessage(): String {
        return "A setup needs to include a recruit granting support."
    }

    override fun toString(): String {
        return "MissingRecruitSupport"
    }
}

/**
 * The scheme does not work for that player count.
 */
@Serializable
object PlayerSchemeMismatch : PrintableError {
    override fun getMessage(): String {
        return "The scheme you selected is not playable with the selected player count."
    }

    override fun toString(): String {
        return "PlayerSchemeMismatch"
    }
}

internal fun TypedCardSet.repr(): String {
    return "($setType $setId)"
}
