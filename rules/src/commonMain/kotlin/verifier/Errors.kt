package games.lmdbg.rules.verifier

interface PrintableError {
    fun getMessage(): String
}

private const val LARGE_ENOUGH_PRIME = 13

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