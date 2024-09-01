package games.lmdbg.server.model;

import games.lmdbg.server.test.util.PojoTestUtil;
import games.lmdbg.server.test.util.TestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Test POJOs in the {@link games.lmdbg.server.model} package for coverage and
 * to weed out stupid bugs.
 */
@SuppressWarnings("static-method")
class ModelPojoTests extends PojoTestUtil {
	/**
	 * Test {@link Account}
	 */
	@Test
	void testAccounts() {
		validateGetSetPojo(Account.class);
	}

	/**
	 * Test {@link ServerPlay}
	 */
	@Test
	void testPlays() {
		validateGetSetPojo(ServerPlay.class);

		// Test equals and hashCode
		ServerPlay underTest = new ServerPlay();
		ServerPlay other = new ServerPlay();
		TestUtil.assertEqualAndHash(underTest, underTest);
		TestUtil.assertEqualAndHash(other, underTest);

		Assertions.assertFalse(underTest.equals(null));

		underTest.setBoard(1);
		Assertions.assertFalse(underTest.equals(other));
		other.setBoard(1);

		underTest.setId(10);
		Assertions.assertFalse(underTest.equals(other));
		other.setId(10);

		underTest.setNotes("Something");
		Assertions.assertFalse(underTest.equals(other));
		other.setNotes("Something");

		underTest.setUser(100);
		Assertions.assertFalse(underTest.equals(other));
		other.setUser(100);

		TestUtil.assertEqualAndHash(other, underTest);
	}
}
