package games.lmdbg.server.model.game;

import org.junit.jupiter.api.Test;

import games.lmdbg.server.test.util.PojoTestUtil;

/**
 * Test POJOs in the {@link games.lmdbg.server.model.game} package for
 * coverage and to weed out stupid bugs.
 */
class GameModelPojoTests extends PojoTestUtil {
	/**
	 * Test {@link Namable}
	 */
	@Test
	void testNamable() {
		validate(Namable.class);
	}
	
	/**
	 * Test {@link CardSet}
	 */
	@Test
	void testCardSet() {
		validate(CardSet.class);
	}
	
	/**
	 * Test {@link GameRelease}
	 */
	@Test
	void testGameRelease() {
		validate(GameRelease.class);
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
	
	/**
	 * Test {@link Support}
	 */
	@Test
	void testSupport() {
		validate(Support.class);
	}
	
	/**
	 * Test {@link Starter}
	 */
	@Test
	void testStarter() {
		validate(Starter.class);
	}
	
	/**
	 * Test {@link StarterPlay}
	 */
	@Test
	void testStarterPlay() {
		validate(StarterPlay.class);
		validate(StarterPlay.Key.class);
	}
}
