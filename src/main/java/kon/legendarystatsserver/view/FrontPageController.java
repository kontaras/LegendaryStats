package kon.legendarystatsserver.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kon.legendarystatsserver.service.WinRateController;

/**
 * A controller for the index page.
 */
@Controller
public class FrontPageController {
	@Autowired
	private WinRateController winRates;
	
	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("heroes", winRates.getHeroWinRates());
		return "index";
	}
}
