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
		validateGetOnlyPojo(Namable.class);
	}
	
	/**
	 * Test {@link CardSet}
	 */
	@Test
	void testCardSet() {
		validateGetOnlyPojo(CardSet.class);
	}
	
	/**
	 * Test {@link GameRelease}
	 */
	@Test
	void testGameRelease() {
		validateGetOnlyPojo(GameRelease.class);
	}

	/**
	 * Test {@link Henchman}
	 */
	@Test
	void testHenchman() {
		validateGetOnlyPojo(Henchman.class);
	}

	/**
	 * Test {@link Hero}
	 */
	@Test
	void testHero() {
		validateGetOnlyPojo(Hero.class);
	}

	/**
	 * Test {@link Mastermind}
	 */
	@Test
	void testMastermind() {
		validateGetOnlyPojo(Mastermind.class);
	}

	/**
	 * Test {@link Scheme}
	 */
	@Test
	void testScheme() {
		validateGetOnlyPojo(Scheme.class);
	}

	/**
	 * Test {@link Villain}
	 */
	@Test
	void testVillain() {
		validateGetOnlyPojo(Villain.class);
	}
	
	/**
	 * Test {@link Support}
	 */
	@Test
	void testSupport() {
		validateGetOnlyPojo(Support.class);
	}
	
	/**
	 * Test {@link Starter}
	 */
	@Test
	void testStarter() {
		validateGetOnlyPojo(Starter.class);
	}
	
	/**
	 * Test {@link PlayStarter}
	 */
	@Test
	void testStarterPlay() {
		validateGetOnlyPojo(PlayStarter.class);
		validateGetOnlyPojo(PlayStarter.Key.class);
	}
	
	/**
	 * Test {@link Board}
	 */
	@Test
	void testBoard() {
		validateGetOnlyPojo(Board.class);
	}
}
