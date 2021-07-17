package kon.legendarystatsserver.model;

import org.junit.jupiter.api.Test;

import kon.legendarystatsserver.test.util.PojoTestUtil;

/**
 * Test POJOs in the {@link kon.legendarystatsserver.model} package for coverage and to weed out stupid bugs.
 */
public class ModelPojoTests extends PojoTestUtil {

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
