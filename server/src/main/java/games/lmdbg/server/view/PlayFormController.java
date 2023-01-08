
package games.lmdbg.server.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.google.common.annotations.VisibleForTesting;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.utils.DisplayKt;
import games.lmdbg.rules.verifier.CardSetType;
import games.lmdbg.rules.verifier.InvalidCardSet;
import games.lmdbg.rules.verifier.InvalidValue;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;
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
	
	/** All of the plays */
	@Autowired
	private PlaysRepository plays;
	
	/** All of the accounts */
	@Autowired
	private AccountsRepository accounts;
	
	/**
	 * Generate data for rendering a game entry form
	 * 
	 * @param model Model to put data into
	 * @return The template to create the game entry form page
	 */
	@GetMapping("/play")
	public String createForm(Model model) {
		Play emptyPlay = new Play();
		emptyPlay.setSupports(Collections.singleton(this.supports.getCardsInOrder().get(0)));
		emptyPlay.setBoard(this.boards.getCardsInOrder().get(0));
		emptyPlay.setStarters(Collections.emptySet());
		
		fillModel(model, emptyPlay);
		
		return "play";
	}
	
	/**
	 * Validate and persist a play
	 * 
	 * @param request http POST request
	 * @param model Model to initialize
	 * @param playInfo play to persist
	 * @param bindingResult Automated validation
	 * @return Next page template to display
	 */
	@PostMapping("/play")
	public String newPlay(HttpServletRequest request, Model model,
			@Valid @ModelAttribute("playInfo") Play playInfo, final BindingResult bindingResult) {
		Map<String, String[]> params = request.getParameterMap();
		
		final List<PrintableError> verificationResult = new ArrayList<>();
		
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				verificationResult
						.add(new InvalidValue(error.getField(), String.valueOf(error.getRejectedValue())));
			}
		}
		else {
			Set<PlayStarter> playStarters = extractStaters(params, playInfo, verificationResult);
			
			playInfo.setStarters(playStarters);
			
			if (verificationResult.isEmpty()) {
				verificationResult.addAll(RulesUtils.verify(playInfo));
			}
		}


		if (!bindingResult.hasGlobalErrors() && verificationResult.isEmpty()){
			String logingError = "Error with login. Please log out and log back in.";
			String name = request.getRemoteUser();
			if (name == null) {
				bindingResult.addError(new ObjectError("globalError", logingError));
			} else {
				Account user = this.accounts.findByUserName(name);
				if (user == null) {
					bindingResult.addError(new ObjectError("globalError", logingError));
				} else {
					playInfo.setPlayer(user);
					this.plays.save(playInfo);
					return "redirect:/plays/" + playInfo.getId(); // TODO: view play page
				}
			}
		}
		
		fillModel(model, playInfo);
		model.addAttribute("verificationErrors", DisplayKt.errorsToString(verificationResult, Map.of())); //TODO: Deref map
		return "play";
	}

	/**
	 * Go through the form parameters and extract the starters used.
	 * 
	 * @param params parameters to extract plays from
	 * @param playInfo play to associate the starters with
	 * @param verificationResult <i>output variable</i> List of errors to report
	 *          back to the user
	 * @return The starters for this play object
	 */
	@VisibleForTesting
	Set<PlayStarter> extractStaters(Map<String, String[]> params, Play playInfo,
			final List<PrintableError> verificationResult) {
		Set<PlayStarter> playStarters = new HashSet<>();
		String starterPrefix = "starters_";
		params.entrySet().stream().filter(t -> t.getKey().startsWith(starterPrefix)).forEach(t -> {
			String starterIdStr = t.getKey().substring(starterPrefix.length());
			Integer starterId;
			try {
				starterId = Integer.valueOf(starterIdStr);
			}
			catch (NumberFormatException e) {
				verificationResult.add(new InvalidValue("starter", starterIdStr));
				return;
			}
			
			Starter starter = this.starters.getById(starterId);
			if (starter == null) {
				verificationResult.add(new InvalidCardSet(CardSetType.STARTER, starterId.intValue()));
			}
			
			Integer starterQuantity;
			try {
				starterQuantity = Integer.valueOf(t.getValue()[0]);
			}
			catch (NumberFormatException e) {
				verificationResult.add(new InvalidValue("starter count", t.getValue()[0]));
				return;
			}
			
			playStarters.add(new PlayStarter(starter, playInfo, starterQuantity));
		});
		return playStarters;
	}
	
	/**
	 * Initialize the model
	 * 
	 * @param model model to initialize
	 * @param play Current play info
	 */
	private void fillModel(Model model, Play play) {
		List<String> outcomes =
				Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
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
