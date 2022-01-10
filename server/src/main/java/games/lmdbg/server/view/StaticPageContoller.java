package games.lmdbg.server.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web page controller for static templates that do not take any variables.
 */
@Controller
public class StaticPageContoller {
	
	/**
	 * Serve up the FAQ page
	 * @return The template for the page
	 */
	@GetMapping("faq")
	public String faqPage() {
		return "faq";
	}
}
