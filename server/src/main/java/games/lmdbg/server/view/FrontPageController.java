package games.lmdbg.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.server.service.WinRate;

/**
 * A controller for the index page.
 */
@Controller
public class FrontPageController {
	/** Source of win rates for {@link Hero} */
	@Autowired
	private WinRate<Hero> heroWinRates;

	/** Source of win rates for {@link Villain} */
	@Autowired
	private WinRate<Villain> villainWinRates;

	/** Source of win rates for {@link Mastermind} */
	@Autowired
	private WinRate<Mastermind> mastermindWinRates;

	/** Source of win rates for {@link Henchman} */
	@Autowired
	private WinRate<Henchman> henchmanWinRates;

	/** Source of win rates for {@link Scheme} */
	@Autowired
	private WinRate<Scheme> schemeWinRates;

	/**
	 * Generate data for rendering the front page.
	 * 
	 * @param model Model to put data into
	 * @return The template to create the index page
	 */
	@GetMapping("/")
	public String mainPage(Model model) {
		model.addAttribute("heroes", this.heroWinRates.getWinRates());
		model.addAttribute("villains", this.villainWinRates.getWinRates());
		model.addAttribute("masterminds", this.mastermindWinRates.getWinRates());
		model.addAttribute("henchmen", this.henchmanWinRates.getWinRates());
		model.addAttribute("schemes", this.schemeWinRates.getWinRates());
		return "index";
	}
}
