package games.lmdbg.server.view;

import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Tests for {@link ThymeleafConfig}
 */
public class ThymeleafConfigTest {

	/**
	 * Test to make sure {@link ThymeleafConfig#getTemplateEngine()} returns the
	 * correct {@link ITemplateResolver}
	 */
	@Test
	void testGetTemplateEngine() {
		SpringTemplateEngine engine = new ThymeleafConfig().getTemplateEngine();
		Set<ITemplateResolver> resolvers = engine.getTemplateResolvers();

		Assertions.assertEquals(resolvers.size(), 2);
	}
}
