package kon.legendarystatsserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import kon.legendarystatsserver.controller.WinRateController;

@Controller
public class FrontPageController {
	@Autowired
	WinRateController winRates;
	
	@GetMapping("/")
	public String mainPAge(Model model) {
		model.addAttribute("heroes", winRates.getHeroWinRates());
		return "index";
	}
}
