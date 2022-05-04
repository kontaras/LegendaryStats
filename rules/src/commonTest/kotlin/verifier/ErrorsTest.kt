package games.lmdbg.rules.verifier

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

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
    }
}