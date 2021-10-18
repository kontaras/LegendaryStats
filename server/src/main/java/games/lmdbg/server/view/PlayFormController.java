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

@Controller
public class PlayFormController {
	@Autowired
	private CardCache<Scheme> schemes;

	@Autowired
	private CardCache<Mastermind> masterminds;

	@Autowired
	private CardCache<Hero> heroes;
	
	@Autowired
	private CardCache<Villain> villains;
	
	@Autowired
	private CardCache<Henchman> henchmen;
	
	@Autowired
	private CardCache<Starter> starters;
	
	@Autowired
	private CardCache<Support> supports;

	@GetMapping("/play")
	public String createForm(Model model) {
		List<String> outcomes = Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
		Collections.sort(outcomes);
		
		List<String> players = Arrays.stream(PlayerCount.values()).map(PlayerCount::toString).collect(Collectors.toList());

		model.addAttribute("outcomes", outcomes);
		model.addAttribute("players", players);
		model.addAttribute("schemes", schemes.getCardsInOrder());
		model.addAttribute("masterminds", masterminds.getCardsInOrder());
		model.addAttribute("heroes", heroes.getCardsInOrder());
		model.addAttribute("villains", villains.getCardsInOrder());
		model.addAttribute("henchmen", henchmen.getCardsInOrder());
		model.addAttribute("starters", starters.getCardsInOrder());
		model.addAttribute("supports", supports.getCardsInOrder());
		return "playForm";
	}
}
