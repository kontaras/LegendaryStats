package games.lmdbg.server.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Web page controller for static templates that do not take any variables.
 */
@Controller
public class StaticPageContoller {

	/**
	 * There should not be reason to instantiate an instance. 
	 */
	private StaticPageContoller() {
		//Nothing to do here
	}
	/**
	 * Serve up the FAQ page
	 * @return The template for the page
	 */
	@GetMapping("faq")
	public static String faqPage() {
		return "faq";
	}
}
