package games.lmdbg.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import games.lmdbg.server.service.PlaySerializer;
import games.lmdbg.server.service.SqlWinRate;

/**
 * A controller for the index page.
 */
@Controller
public class FrontPageController {
	@Autowired
	SqlWinRate winRates;

	/**
	 * Generate data for rendering the front page.
	 * 
	 * @param model Model to put data into
	 * @return The template to create the index page
	 */
	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("heroes", this.winRates.getSetWinRates(PlaySerializer.ComponentType.HERO));
		model.addAttribute("villains", this.winRates.getSetWinRates(PlaySerializer.ComponentType.VILLAIN));
		model.addAttribute("masterminds", this.winRates.getSetWinRates(PlaySerializer.ComponentType.MASTERMIND));
		model.addAttribute("henchmen", this.winRates.getSetWinRates(PlaySerializer.ComponentType.HENCHMAN));
		model.addAttribute("schemes", this.winRates.getSetWinRates(PlaySerializer.ComponentType.SCHEME));
		return "index";
	}
}
