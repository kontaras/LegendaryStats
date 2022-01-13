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
	static void testNamable() {
		validateSetOnlyPojo(Namable.class);
	}
	
	/**
	 * Test {@link CardSet}
	 */
	@Test
	static void testCardSet() {
		validateSetOnlyPojo(CardSet.class);
	}
	
	/**
	 * Test {@link GameRelease}
	 */
	@Test
	static void testGameRelease() {
		validateSetOnlyPojo(GameRelease.class);
	}

	/**
	 * Test {@link Henchman}
	 */
	@Test
	static void testHenchman() {
		validateSetOnlyPojo(Henchman.class);
	}

	/**
	 * Test {@link Hero}
	 */
	@Test
	static void testHero() {
		validateSetOnlyPojo(Hero.class);
	}

	/**
	 * Test {@link Mastermind}
	 */
	@Test
	static void testMastermind() {
		validateSetOnlyPojo(Mastermind.class);
	}

	/**
	 * Test {@link Scheme}
	 */
	@Test
	static void testScheme() {
		validateSetOnlyPojo(Scheme.class);
	}

	/**
	 * Test {@link Villain}
	 */
	@Test
	static void testVillain() {
		validateSetOnlyPojo(Villain.class);
	}
	
	/**
	 * Test {@link Support}
	 */
	@Test
	static void testSupport() {
		validateSetOnlyPojo(Support.class);
	}
	
	/**
	 * Test {@link Starter}
	 */
	@Test
	static void testStarter() {
		validateSetOnlyPojo(Starter.class);
	}
	
	/**
	 * Test {@link StarterPlay}
	 */
	@Test
	static void testStarterPlay() {
		validateSetOnlyPojo(StarterPlay.class);
		validateSetOnlyPojo(StarterPlay.Key.class);
	}
}
