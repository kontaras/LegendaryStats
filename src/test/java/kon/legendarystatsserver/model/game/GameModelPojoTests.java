package kon.legendarystatsserver.model.game;

import org.junit.jupiter.api.Test;

import pl.pojo.tester.api.assertion.Assertions;
import pl.pojo.tester.api.assertion.Method;

/**
 * Test POJOs in the {@link kon.legendarystatsserver.model.game} package for coverage and to weed out stupid bugs.
 */
public class GameModelPojoTests {
	/**
	 * Test {@link GameSet}
	 */
	@Test
	void testGameSet() {
		Assertions.assertPojoMethodsFor(GameSet.class).testing(Method.GETTER).areWellImplemented();
	}
	
	/**
	 * Test {@link Henchman}
	 */
	@Test
	void testHenchman() {
		Assertions.assertPojoMethodsFor(Henchman.class).testing(Method.GETTER).areWellImplemented();
	}
	
	/**
	 * Test {@link Hero}
	 */
	@Test
	void testHero() {
		Assertions.assertPojoMethodsFor(Hero.class).testing(Method.GETTER).areWellImplemented();
	}
	
	/**
	 * Test {@link Mastermind}
	 */
	@Test
	void testMastermind() {
		Assertions.assertPojoMethodsFor(Mastermind.class).testing(Method.GETTER).areWellImplemented();
	}
	
	/**
	 * Test {@link Scheme}
	 */
	@Test
	void testScheme() {
		Assertions.assertPojoMethodsFor(Scheme.class).testing(Method.GETTER).areWellImplemented();
	}
	
	/**
	 * Test {@link Villain}
	 */
	@Test
	void testVillain() {
		Assertions.assertPojoMethodsFor(Villain.class).testing(Method.GETTER).areWellImplemented();
	}
}
