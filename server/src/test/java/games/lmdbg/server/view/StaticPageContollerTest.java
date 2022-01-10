package games.lmdbg.server.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StaticPageContoller}
 */
class StaticPageContollerTest {

	/** Instance under test. */
	private StaticPageContoller testInstance;

	/**
	 * Test {@link StaticPageContoller#faqPage()}
	 */
	@Test
	void testFaqPage() {
		testInstance = new StaticPageContoller();
		Assertions.assertEquals("faq", testInstance.faqPage());
	}
}
