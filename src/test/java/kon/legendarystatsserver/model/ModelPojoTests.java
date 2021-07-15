package kon.legendarystatsserver.model;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

/**
 * Test POJOs in the {@link kon.legendarystatsserver.model} package for coverage and to weed out stupid bugs.
 */
public class ModelPojoTests {

	/**
	 * Test {@link Play}
	 */
	@Test
	void testPlay() {
		Assertions.assertPojoMethodsFor(Play.class).testing(Method.GETTER).areWellImplemented();
	}

	/**
	 * Test {@link User}
	 */
	@Test
	void testUser() {
		Assertions.assertPojoMethodsFor(User.class).testing(Method.GETTER).areWellImplemented();
	}
}
