package games.lmdbg.server.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link StaticPageContoller}
 */
@SuppressWarnings("static-method")
class StaticPageContollerTest {

	/**
	 * Test {@link StaticPageContoller#faqPage()}
	 */
	@Test
	void testFaqPage() {
		Assertions.assertEquals("faq", StaticPageContoller.faqPage());
	}
	
	/**
	 * Test {@link StaticPageContoller#loginPage()}
	 */
	@Test
	void testLoginPage() {
		Assertions.assertEquals("login", StaticPageContoller.loginPage());
	}
}
