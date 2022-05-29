package games.lmdbg.server.view;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.tinylog.Logger;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.server.model.AccountRepository;
import games.lmdbg.server.model.Play;
import games.lmdbg.server.model.PlaysRepository;
import games.lmdbg.server.model.game.Board;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Starter;
import games.lmdbg.server.model.game.PlayStarter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.CardCache;
import games.lmdbg.server.service.RulesUtils;

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

	/** All of the game boards */
	@Autowired
	private CardCache<Board> boards;

	@Autowired
	private PlaysRepository plays;

	@Autowired
	private AccountRepository accounts;

	/**
	 * Generate data for rendering a game entry form
	 * 
	 * @param model Model to put data into
	 * @return The template to create the game entry form page
	 */
	@GetMapping("/play")
	public String createForm(Model model) {
		Play emptyPlay = new Play();
		emptyPlay.setSupports(Collections.singleton(supports.getCardsInOrder().get(0)));
		emptyPlay.setBoard(boards.getCardsInOrder().get(0));
		emptyPlay.setStarters(Collections.emptySet());

		fillModel(model, emptyPlay);

		return "play";
	}

	@PostMapping("/play")
	public String newPlay(ServletRequest request, Model model, @Valid @ModelAttribute("playInfo") Play playInfo,
			final BindingResult bindingResult) {
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> param : params.entrySet()) {
			Logger.debug(param.getKey() + Arrays.toString(param.getValue()));
		}

		Set<PlayStarter> playStarters = new HashSet<>();
		String starterPrefix = "starters_";
		params.entrySet().stream().filter(t -> t.getKey().startsWith(starterPrefix)).forEach(t -> {
			String starterIdStr = t.getKey().substring(starterPrefix.length());
			Integer starterId;
			try {
				starterId = Integer.valueOf(starterIdStr);
			} catch (NumberFormatException e) {
				Logger.debug("Malformed starter id param {}", starterIdStr);
				// TODO: Communicate to user?
				return;
			}

			Starter starter = starters.getById(starterId);
			if (starter == null) {
				// TODO: Communicate to user?
				Logger.warn("Unknown starter id {}", starterId);
			}

			Integer starterQuantity;
			try {
				starterQuantity = Integer.valueOf(t.getValue()[0]);
			} catch (NumberFormatException e) {
				Logger.debug("Malformed starter id param {}", t.getValue()[0]);
				// TODO: Communicate to user?
				return;
			}

			playStarters.add(new PlayStarter(starter, playInfo, starterQuantity));
		});

		playInfo.setStarters(playStarters);

		Logger.debug(playInfo);
		Logger.debug(playInfo.getHeroes());

		Logger.debug(
				String.join(", ", bindingResult.getAllErrors().parallelStream().map(ObjectError::toString).toList()));

		if (bindingResult.hasErrors()) {
			// errors
		} else {
			List<PrintableError> verificationResult = RulesUtils.verify(playInfo);
			if (!verificationResult.isEmpty()) {
				// TODO: Card Set
				verificationResult.stream()
						.forEach(t -> bindingResult.addError(new ObjectError("globalError", t.getMessage())));
			} else {
				playInfo.setPlayer(accounts.findById(Long.valueOf(1)).get());
				plays.save(playInfo);
				return "play"; // TODO: view play page
			}
		}

		fillModel(model, playInfo);
		return "play";
	}

	private void fillModel(Model model, Play play) {
		List<String> outcomes = Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
		Collections.sort(outcomes);

		List<String> players = Arrays.stream(PlayerCount.values()).map(PlayerCount::toString).toList();

		model.addAttribute("outcomes", outcomes);
		model.addAttribute("players", players);
		model.addAttribute("schemes", this.schemes.getCardsInOrder());
		model.addAttribute("masterminds", this.masterminds.getCardsInOrder());
		model.addAttribute("heroes", this.heroes.getCardsInOrder());
		model.addAttribute("villains", this.villains.getCardsInOrder());
		model.addAttribute("henchmen", this.henchmen.getCardsInOrder());
		model.addAttribute("starters", this.starters.getCardsInOrder());
		model.addAttribute("supports", this.supports.getCardsInOrder());
		model.addAttribute("boards", this.boards.getCardsInOrder());

		model.addAttribute("playInfo", play);
	}
}
