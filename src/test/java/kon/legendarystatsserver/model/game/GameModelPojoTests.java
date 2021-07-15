package kon.legendarystatsserver.model.game;

import org.junit.jupiter.api.Test;

import kon.legendarystatsserver.test.util.PojoTest;

/**
 * Test POJOs in the {@link kon.legendarystatsserver.model.game} package for
 * coverage and to weed out stupid bugs.
 */
public class GameModelPojoTests extends PojoTest {
	/**
	 * Test {@link GameSet}
	 */
	@Test
	void testGameSet() {
		validate(GameSet.class);
	}

	/**
	 * Test {@link Henchman}
	 */
	@Test
	void testHenchman() {
		validate(Henchman.class);
	}

	/**
	 * Test {@link Hero}
	 */
	@Test
	void testHero() {
		validate(Hero.class);
	}

	/**
	 * Test {@link Mastermind}
	 */
	@Test
	void testMastermind() {
		validate(Mastermind.class);
	}

	/**
	 * Test {@link Scheme}
	 */
	@Test
	void testScheme() {
		validate(Scheme.class);
	}

	/**
	 * Test {@link Villain}
	 */
	@Test
	void testVillain() {
		validate(Villain.class);
	}
}
