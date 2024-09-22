package games.lmdbg.server;

import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Tests for {@link ThymeleafConfig}
 */
@SuppressWarnings("static-method")
class ThymeleafConfigTest {

	/**
	 * Test to make sure {@link ThymeleafConfig#getTemplateEngine()} returns the
	 * correct {@link ITemplateResolver}
	 */
	@Test
	void testGetTemplateEngine() {
		SpringTemplateEngine engine = ThymeleafConfig.getTemplateEngine();
		Set<ITemplateResolver> resolvers = engine.getTemplateResolvers();

		Assertions.assertEquals(2, resolvers.size());
	}
}
