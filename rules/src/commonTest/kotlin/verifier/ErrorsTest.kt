package games.lmdbg.rules.verifier

import kotlin.test.Test
import kotlin.test.assertEquals
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
        assertEquals("Expected to provide 1 board sets, got 2".hashCode(), underTest.hashCode())

        //toString
        assertEquals("WrongSetCount BOARD: exp 1, act 2", underTest.toString())

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
        assertEquals("Invalid board: 1".hashCode(), underTest.hashCode())

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
        assertEquals("Missing required card sets (BOARD 2), (STARTER 8)", underTest.getMessage())

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
        assertEquals("Missing required card sets (BOARD 2), (STARTER 8)".hashCode(), underTest.hashCode())

        //toString
        assertEquals("MissingRequiredSet (BOARD 2), (STARTER 8)", underTest.toString())

        //getCardSets
        assertEquals(
            listOf(
                TypedCardSet(CardSetType.BOARD, 2),
                TypedCardSet(CardSetType.STARTER, 8)
            ), underTest.getCardSets())
    }
}