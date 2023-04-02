package games.lmdbg.server.model;

import org.junit.jupiter.api.Test;

import games.lmdbg.server.test.util.PojoTestUtil;

/**
 * Test POJOs in the {@link games.lmdbg.server.model} package for coverage and to weed out stupid bugs.
 */
@SuppressWarnings("static-method")
class ModelPojoTests extends PojoTestUtil {

	/**
	 * Test {@link Play}
	 */
	@Test
	void testPlay() {
		validateGetOnlyPojo(Play.class);
	}

	/**
	 * Test {@link Account}
	 */
	@Test
	void testAccounts() {
		validateGetSetPojo(Account.class);
	}
	
	/**
	 * Test {@link StarterPlay}
	 */
	@Test
	void testStarterPlay() {
		validateGetOnlyPojo(StarterPlay.class);
		validateGetOnlyPojo(StarterPlay.Key.class);
	}
}
