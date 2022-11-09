package games.lmdbg.server.model.game;

import org.junit.jupiter.api.Test;

import games.lmdbg.server.test.util.PojoTestUtil;

/**
 * Test POJOs in the {@link games.lmdbg.server.model.game} package for
 * coverage and to weed out stupid bugs.
 */
@SuppressWarnings("static-method")
class GameModelPojoTests extends PojoTestUtil {
	/**
	 * Test {@link Namable}
	 */
	@Test
	void testNamable() {
		validateSetOnlyPojo(Namable.class);
	}
	
	/**
	 * Test {@link CardSet}
	 */
	@Test
	void testCardSet() {
		validateSetOnlyPojo(CardSet.class);
	}
	
	/**
	 * Test {@link GameRelease}
	 */
	@Test
	void testGameRelease() {
		validateSetOnlyPojo(GameRelease.class);
	}

	/**
	 * Test {@link Henchman}
	 */
	@Test
	void testHenchman() {
		validateSetOnlyPojo(Henchman.class);
	}

	/**
	 * Test {@link Hero}
	 */
	@Test
	void testHero() {
		validateSetOnlyPojo(Hero.class);
	}

	/**
	 * Test {@link Mastermind}
	 */
	@Test
	void testMastermind() {
		validateSetOnlyPojo(Mastermind.class);
	}

	/**
	 * Test {@link Scheme}
	 */
	@Test
	void testScheme() {
		validateSetOnlyPojo(Scheme.class);
	}

	/**
	 * Test {@link Villain}
	 */
	@Test
	void testVillain() {
		validateSetOnlyPojo(Villain.class);
	}
	
	/**
	 * Test {@link Support}
	 */
	@Test
	void testSupport() {
		validateSetOnlyPojo(Support.class);
	}
	
	/**
	 * Test {@link Starter}
	 */
	@Test
	void testStarter() {
		validateSetOnlyPojo(Starter.class);
	}
	
	/**
	 * Test {@link PlayStarter}
	 */
	@Test
	void testStarterPlay() {
		validateSetOnlyPojo(PlayStarter.class);
		validateSetOnlyPojo(PlayStarter.Key.class);
	}
	
	/**
	 * Test {@link Board}
	 */
	@Test
	void testBoard() {
		validateSetOnlyPojo(Board.class);
	}
}
