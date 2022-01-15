package games.lmdbg.server.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Starter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.CardCache;

/**
 * Controller for creating the game entry form.
 */
@Controller
public class PlayFormController {
	/** All of the schemes */
	@Autowired
	private CardCache<Scheme> schemes;

	/** All of the masterminds */
	@Autowired
	private CardCache<Mastermind> masterminds;

	/** All of the heroes */
	@Autowired
	private CardCache<Hero> heroes;
	
	/** All of the villains */
	@Autowired
	private CardCache<Villain> villains;
	
	/** All of the henchmen */
	@Autowired
	private CardCache<Henchman> henchmen;
	
	/** All of the starting decks */
	@Autowired
	private CardCache<Starter> starters;
	
	/** All of the support piles */
	@Autowired
	private CardCache<Support> supports;

	/**
	 * Generate data for rendering a game entry form
	 * 
	 * @param model Model to put data into
	 * @return The template to create the game entry form page
	 */
	@GetMapping("/play")
	public String createForm(Model model) {
		List<String> outcomes = Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
		Collections.sort(outcomes);
		
		List<String> players = Arrays.stream(PlayerCount.values()).map(PlayerCount::toString).collect(Collectors.toList());

		model.addAttribute("outcomes", outcomes);
		model.addAttribute("players", players);
		model.addAttribute("schemes", this.schemes.getCardsInOrder());
		model.addAttribute("masterminds", this.masterminds.getCardsInOrder());
		model.addAttribute("heroes", this.heroes.getCardsInOrder());
		model.addAttribute("villains", this.villains.getCardsInOrder());
		model.addAttribute("henchmen", this.henchmen.getCardsInOrder());
		model.addAttribute("starters", this.starters.getCardsInOrder());
		model.addAttribute("supports", this.supports.getCardsInOrder());
		return "playForm";
	}
}
