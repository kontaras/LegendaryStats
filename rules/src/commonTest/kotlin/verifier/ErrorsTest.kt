package games.lmdbg.rules.verifier

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertContentEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

@Suppress("ReplaceCallWithBinaryOperator")
internal class ErrorsTest {
    @Test
    fun wrongSetCountTest() {
        val underTest = WrongSetCount(CardSetType.BOARD, 1, 2)

        //getMessage
        assertEquals("Expected to provide 1 board sets, got 2", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(WrongSetCount(CardSetType.BOARD, 1, 2)))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertFalse(underTest.equals(WrongSetCount(CardSetType.HERO, 1, 2)))
        assertFalse(underTest.equals(WrongSetCount(CardSetType.BOARD, 4, 2)))
        assertFalse(underTest.equals(WrongSetCount(CardSetType.BOARD, 1, 8)))

        //hashCode
        assertEquals("WrongSetCount BOARD: exp 1, act 2".hashCode(), underTest.hashCode())

        //toString
        assertEquals("WrongSetCount BOARD: exp 1, act 2", underTest.toString())

        //getCardSets
        assertEquals(listOf(), underTest.getCardSets())
    }

    @Test
    fun invalidValueTest() {
        val underTest = InvalidValue("board", "null")

        //getMessage
        assertEquals("Invalid value provided for board: null", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(InvalidValue("board", "null")))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertFalse(underTest.equals(InvalidValue("villain", "null")))
        assertFalse(underTest.equals(InvalidValue("board", "nil")))

        //hashCode
        assertEquals("InvalidValue board: null".hashCode(), underTest.hashCode())

        //toString
        assertEquals("InvalidValue board: null", underTest.toString())

        //getCardSets
        assertEquals(listOf(), underTest.getCardSets())
    }

    @Test
    fun invalidCardSetTest() {
        val underTest = InvalidCardSet(CardSetType.BOARD, 1)

        //getMessage
        assertEquals("Invalid board: 1", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(InvalidCardSet(CardSetType.BOARD, 1)))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertFalse(underTest.equals(InvalidCardSet(CardSetType.HERO, 1)))
        assertFalse(underTest.equals(InvalidCardSet(CardSetType.BOARD, 4)))

        //hashCode
        assertEquals("InvalidCardSet BOARD 1".hashCode(), underTest.hashCode())

        //toString
        assertEquals("InvalidCardSet BOARD 1", underTest.toString())

        //getCardSets
        assertEquals(listOf(), underTest.getCardSets())
    }

    @Test
    fun missingRequiredSetTest() {
        val underTest = MissingRequiredSet(
            listOf(
                TypedCardSet(CardSetType.BOARD, 2),
                TypedCardSet(CardSetType.STARTER, 8)
            )
        )

        //getMessage
        assertEquals("Missing required card sets %CARDS%", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(
            underTest.equals(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.BOARD, 2),
                        TypedCardSet(CardSetType.STARTER, 8)
                    )
                )
            )
        )

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertFalse(
            underTest.equals(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.BOARD, 2)
                    )
                )
            )
        )
        assertFalse(
            underTest.equals(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.BOARD, 2),
                        TypedCardSet(CardSetType.STARTER, 7)
                    )
                )
            )
        )
        assertFalse(
            underTest.equals(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.HERO, 2),
                        TypedCardSet(CardSetType.STARTER, 8)
                    )
                )
            )
        )
        assertFalse(
            underTest.equals(
                MissingRequiredSet(
                    listOf(
                        TypedCardSet(CardSetType.BOARD, 2),
                        TypedCardSet(CardSetType.STARTER, 8),
                        TypedCardSet(CardSetType.HERO, 5)
                    )
                )
            )
        )

        //hashCode
        assertEquals("MissingRequiredSet (BOARD 2), (STARTER 8)".hashCode(), underTest.hashCode())

        //toString
        assertEquals("MissingRequiredSet (BOARD 2), (STARTER 8)", underTest.toString())

        //getCardSets
        assertEquals(
            listOf(
                TypedCardSet(CardSetType.BOARD, 2),
                TypedCardSet(CardSetType.STARTER, 8)
            ), underTest.getCardSets()
        )
    }

    @Test
    fun invalidCardQuantityTest() {
        val underTest = InvalidCardQuantity(TypedCardSet(CardSetType.HERO, 1), -4)

        //getMessage
        val expectedMessage = "Invalid quantity of %CARDS%: -4"
        assertEquals(expectedMessage, underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(InvalidCardQuantity(TypedCardSet(CardSetType.HERO, 1), -4)))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertFalse(underTest.equals(InvalidCardQuantity(TypedCardSet(CardSetType.HENCHMAN, 1), -4)))
        assertFalse(underTest.equals(InvalidCardQuantity(TypedCardSet(CardSetType.HERO, 2), -4)))
        assertFalse(underTest.equals(InvalidCardQuantity(TypedCardSet(CardSetType.HERO, 1), -3)))

        //hashCode
        assertEquals("InvalidCardQuantity (HERO 1) -4".hashCode(), underTest.hashCode())

        //toString
        assertEquals("InvalidCardQuantity (HERO 1) -4", underTest.toString())

        //getCardSets
        assertEquals(listOf(TypedCardSet(CardSetType.HERO, 1)), underTest.getCardSets())
    }

    @Test
    fun missingRecruitSupportTest() {
        val underTest = MissingRecruitSupport

        //getMessage
        assertEquals("A setup needs to include a recruit granting support.", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(MissingRecruitSupport))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))


        //hashCode
        assertEquals(MissingRecruitSupport.hashCode(), underTest.hashCode())

        //toString
        assertEquals("MissingRecruitSupport", underTest.toString())

        //getCardSets
        assertEquals(listOf(), underTest.getCardSets())
    }

    @Test
    fun playerSchemeMismatchTest() {
        val underTest = PlayerSchemeMismatch

        //getMessage
        assertEquals("The scheme you selected is not playable with the selected player count.", underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(PlayerSchemeMismatch))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))


        //hashCode
        assertEquals(PlayerSchemeMismatch.hashCode(), underTest.hashCode())

        //toString
        assertEquals("PlayerSchemeMismatch", underTest.toString())

        //getCardSets
        assertEquals(listOf(), underTest.getCardSets())
    }

    fun missingFieldTest() {
        val underTest = MissingField("mastermind")

        //getMessage
        val expectedMessage = "No value provided for mastermind."
        assertEquals(expectedMessage, underTest.getMessage())

        //equals
        assertTrue(underTest.equals(underTest))
        assertTrue(underTest.equals(MissingField("mastermind")))

        assertFalse(underTest.equals(null))
        assertFalse(underTest.equals(3))
        assertTrue(underTest.equals(MissingField("board")))

        //hashCode
        assertEquals("mastermind".hashCode(), underTest.hashCode())

        //toString
        assertEquals("MissingField: mastermind", underTest.toString())
    }

    /**
     * Test that all of the [PrintableError] types can be serialized and read again
     */
    @Test
    fun serializabilityTest() {
        val errors: List<PrintableError> = listOf(
            WrongSetCount(CardSetType.HENCHMAN, 1, 2),
            InvalidValue("starter", "Chinchilla"),
            InvalidCardSet(CardSetType.SUPPORT, -5),
            MissingRequiredSet(listOf(TypedCardSet(CardSetType.VILLAIN, 7), TypedCardSet(CardSetType.HERO, 4))),
            InvalidCardQuantity(
                TypedCardSet(CardSetType.HENCHMAN, 17), 123444
            ),
            MissingField("grass"),
            MissingRecruitSupport,
            PlayerSchemeMismatch
        )
        val encoded: String = errorSerializer.encodeToString(errors)

        println(encoded)

        val decoded: List<PrintableError> = errorSerializer.decodeFromString(encoded)

        assertContentEquals(errors, decoded)
    }
}
