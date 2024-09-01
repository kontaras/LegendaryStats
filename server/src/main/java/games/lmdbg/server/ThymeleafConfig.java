
package games.lmdbg.server;

import java.util.Collections;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity6.dialect.SpringSecurityDialect;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Configuration overrides Thymeleaf
 */
@Configuration
public class ThymeleafConfig {
	/** Needed by Spring */
	public ThymeleafConfig() {
		// Nothing here
	}
	
	/**
	 * Create a Template Engine that can handle HTNL and JavaScript files
	 * 
	 * @return The template engine
	 */
	@Bean
	public static SpringTemplateEngine getTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		templateEngine.addTemplateResolver(jsTemplateResolver());
		
		templateEngine.addDialect(new SpringSecurityDialect());
		
		return templateEngine;
	}
	
	/**
	 * Create a template resolver for HTML templates
	 * 
	 * @return The template resolver
	 */
	private static ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	/**
	 * Create a template resolver for JavaScript templates
	 * 
	 * @return The template resolver
	 */
	private static ITemplateResolver jsTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
		templateResolver.setResolvablePatterns(Collections.singleton("scripts/*"));
		templateResolver.setPrefix("/templates/");
		templateResolver.setSuffix(".js");
		templateResolver.setTemplateMode(TemplateMode.JAVASCRIPT);
		templateResolver.setCharacterEncoding("UTF-8");
		return templateResolver;
	}
	
	@Bean
	public SpringSecurityDialect springSecurityDialect() {
	    return new SpringSecurityDialect();
	}
}
