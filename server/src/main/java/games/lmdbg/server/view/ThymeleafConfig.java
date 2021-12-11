package games.lmdbg.server.view;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class ThymeleafConfig {

	@Bean
	public SpringTemplateEngine getTemplateEngine() {
		final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
		
		templateEngine.addTemplateResolver(htmlTemplateResolver());
		templateEngine.addTemplateResolver(jsTemplateResolver());
		
		return templateEngine;
	}
	
	private ITemplateResolver htmlTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(2));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
	}
	
	private ITemplateResolver jsTemplateResolver() {
		final ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setOrder(Integer.valueOf(1));
        templateResolver.setResolvablePatterns(Collections.singleton("scripts/*"));
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".js");
        templateResolver.setTemplateMode(TemplateMode.JAVASCRIPT);
        templateResolver.setCharacterEncoding("UTF-8");
        return templateResolver;
	}
}
