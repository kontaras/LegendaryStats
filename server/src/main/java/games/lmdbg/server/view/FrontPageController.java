package games.lmdbg.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.WinRate;

/**
 * A controller for the index page.
 */
@Controller
public class FrontPageController {
	@Autowired
	private WinRate<Hero> heroWinRates;
	
	@Autowired
	private WinRate<Villain> villainWinRates;

	@Autowired
	private WinRate<Mastermind> mastermindWinRates;
	
	@Autowired
	private WinRate<Henchman> henchmanWinRates;
	
	@Autowired
	private WinRate<Scheme> schemeWinRates;
	
	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("heroes", heroWinRates.getWinRates());
		model.addAttribute("villains", villainWinRates.getWinRates());
		model.addAttribute("masterminds", mastermindWinRates.getWinRates());
		model.addAttribute("henchmen", henchmanWinRates.getWinRates());
		model.addAttribute("schemes", schemeWinRates.getWinRates());
		return "index";
	}
}
