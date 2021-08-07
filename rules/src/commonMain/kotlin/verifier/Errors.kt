package kon.foo.verifier

interface PrintableError {
    fun getMessage(): String
}

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
        val largeEnoughPrime = 13
        return setType.hashCode() * largeEnoughPrime * largeEnoughPrime + actual * largeEnoughPrime + expected
    }

    override fun toString(): String {
        return "WrongSetCount $setType: exp $expected, act $actual"
    }
}