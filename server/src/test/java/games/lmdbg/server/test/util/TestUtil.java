package games.lmdbg.server.test.util;

import org.junit.jupiter.api.Assertions;

public class TestUtil {

	/**
	 * Test that a given object returns true from {@link Object#equals(Object)} and
	 * has the same {@link Object#hashCode()}.
	 * 
	 * @param expected The object that has equal and the same hash code.
	 * @param actual   The object under test.
	 */
	public static void assertEqualAndHash(Object expected, Object actual) {
		Assertions.assertTrue(actual.equals(expected));
		Assertions.assertEquals(expected.hashCode(), actual.hashCode());
	}
}
