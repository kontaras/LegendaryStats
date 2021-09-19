package games.lmdbg.server.model;

import org.junit.jupiter.api.Test;

import games.lmdbg.server.test.util.PojoTestUtil;

/**
 * Test POJOs in the {@link games.lmdbg.server.model} package for coverage and to weed out stupid bugs.
 */
class ModelPojoTests extends PojoTestUtil {

	/**
	 * Test {@link Play}
	 */
	@Test
	void testPlay() {
		validate(Play.class);
	}

	/**
	 * Test {@link User}
	 */
	@Test
	void testUser() {
		validate(User.class);
	}
}
