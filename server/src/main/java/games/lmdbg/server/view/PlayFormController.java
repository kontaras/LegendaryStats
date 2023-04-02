package games.lmdbg.server.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.set.CardLookupKt;

/**
 * Controller for creating the game entry form.
 */
@Controller
public class PlayFormController {

	/**
	 * Generate data for rendering a game entry form
	 * 
	 * @param model Model to put data into
	 * @return The template to create the game entry form page
	 */
	@GetMapping("/play")
	public static String createForm(Model model) {
		List<String> outcomes = Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
		Collections.sort(outcomes);
		
		List<String> players = Arrays.stream(PlayerCount.values()).map(PlayerCount::toString).collect(Collectors.toList());

		model.addAttribute("outcomes", outcomes);
		model.addAttribute("players", players);
		model.addAttribute("schemes", sortCards(CardLookupKt.getSchemesById().values()));
		model.addAttribute("masterminds", sortCards(CardLookupKt.getMastermindsById().values()));
		model.addAttribute("heroes", sortCards(CardLookupKt.getHeroesById().values()));
		model.addAttribute("villains", sortCards(CardLookupKt.getVillainsById().values()));
		model.addAttribute("henchmen", sortCards(CardLookupKt.getHenchmanById().values()));
		model.addAttribute("starters", sortCards(CardLookupKt.getStartersById().values()));
		model.addAttribute("supports", sortCards(CardLookupKt.getSupportsById().values()));
		model.addAttribute("boards", sortCards(CardLookupKt.getBoardsById().values()));
		return "playForm";
	}
	
	private static <T extends CardSet> List<T> sortCards(Collection<T> cardSet) {
		List<T> cards = new ArrayList<>(cardSet);
		cards.sort(Comparator.comparing(CardSet::getId));
		return cards;
	}
}
