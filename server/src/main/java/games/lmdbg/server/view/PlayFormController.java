package games.lmdbg.server.view;

import com.google.common.annotations.VisibleForTesting;
import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.set.CardLookupKt;
import games.lmdbg.rules.set.core.Boards;
import games.lmdbg.rules.set.core.Supports;
import games.lmdbg.rules.utils.DisplayKt;
import games.lmdbg.rules.verifier.InvalidValue;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.rules.verifier.VerifierKt;
import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;
import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.service.PlayStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Controller for creating the game entry form.
 */
@Controller
public class PlayFormController {
	public static final String PLAY_PATH = "/play";

	/** All of the accounts */
	@Autowired
	@VisibleForTesting
	AccountsRepository accounts;

	@Autowired
	@VisibleForTesting
	PlayStore serializer;

	/**
	 * Generate data for rendering a game entry form
	 *
	 * @param model Model to put data into
	 * @return The template to create the game entry form page
	 */
	@GetMapping(PLAY_PATH)
	public static String createForm(Model model) {
		ServerPlay emptyPlay = new ServerPlay();

		emptyPlay.setBoard(Boards.INSTANCE.getHQ().getId());
		emptyPlay.setSupports(Set.of(Supports.INSTANCE.getSHIELD_OFFICER().getId()));

		fillModel(model, emptyPlay);

		return "play";
	}

	/**
	 * Validate and persist a play
	 *
	 * @param request       http POST request
	 * @param model         Model to initialize
	 * @param playInfo      play to persist
	 * @param bindingResult Automated validation
	 * @return Next page template to display
	 */
	@PostMapping(PLAY_PATH)
	public String newPlay(HttpServletRequest request, Model model,
	        @Valid @ModelAttribute("playInfo") ServerPlay playInfo, final BindingResult bindingResult) {

		playInfo.setSupports(Set.of(Supports.INSTANCE.getSHIELD_OFFICER().getId()));

		Map<String, String[]> params = request.getParameterMap();

		final List<PrintableError> verificationResult = new ArrayList<>();

		checkFieldErrors(bindingResult, verificationResult);

		setStarters(params, playInfo, verificationResult);

		if (verificationResult.isEmpty() && !bindingResult.hasGlobalErrors()) {
			verifyPlay(playInfo, verificationResult);

			setUser(request, playInfo, bindingResult, verificationResult);
		}

		if (verificationResult.isEmpty() && !bindingResult.hasGlobalErrors()) {
			this.serializer.createPlay(playInfo);
			return "redirect:/";
		}

		fillModel(model, playInfo);
		model.addAttribute("verificationErrors", DisplayKt.errorsToString(verificationResult));
		return "play";
	}

	/**
	 * Check for {@link FieldError} and convert them to {@link PrintableError}.
	 * 
	 * @param bindingResult      Binding to check for errors
	 * @param verificationResult Output list to store errors into
	 */
	private void checkFieldErrors(final BindingResult bindingResult, final List<PrintableError> verificationResult) {
		if (bindingResult.hasErrors()) {
			for (FieldError error : bindingResult.getFieldErrors()) {
				verificationResult.add(new InvalidValue(error.getField(), String.valueOf(error.getRejectedValue())));
			}
		}
	}

	/**
	 * Find starters in request parameters and write them into the play.
	 * 
	 * @param params             Request parameters to find starters in
	 * @param playInfo           Output play to write the starters into
	 * @param verificationResult Output list to store errors into
	 */
	private void setStarters(Map<String, String[]> params, ServerPlay playInfo,
	        final List<PrintableError> verificationResult) {
		Map<Integer, Integer> playStarters = extractStaters(params, verificationResult);
		if (verificationResult.isEmpty()) {
			playInfo.setStarters(playStarters);
		}

	}

	/**
	 * Run the play verifier on the play.
	 * 
	 * This method follows a railroad error pattern. If there are already errors in
	 * the results, the method is a no-op.
	 * 
	 * @param playInfo           Play to check
	 * @param verificationResult Output list to store errors into and error list to
	 *                           check before doing any work.
	 */
	private void verifyPlay(ServerPlay playInfo, final List<PrintableError> verificationResult) {
		if (verificationResult.isEmpty()) {
			verificationResult.addAll(VerifierKt.verify(playInfo));
		}
	}

	/**
	 * Add user info into the play.
	 * 
	 * This method follows a railroad error pattern. If there are already errors in
	 * the results, the method is a no-op.
	 * 
	 * @param request            Request from which to extract user info
	 * @param playInfo           Output play to write the user info into
	 * @param bindingResult      Output result to store errors into
	 * @param verificationResult Error list to check before doing any work.
	 */
	private void setUser(HttpServletRequest request, ServerPlay playInfo, final BindingResult bindingResult,
	        final List<PrintableError> verificationResult) {
		if (verificationResult.isEmpty()) {
			String loginError = "Error with login. Please log out and log back in.";
			String name = request.getRemoteUser();
			if (name == null) {
				bindingResult.addError(new ObjectError("globalError", loginError));
			} else {
				Account user = this.accounts.findByUserName(name);
				if (user == null) {
					bindingResult.addError(new ObjectError("globalError", loginError));
				} else {
					playInfo.setUser(user.getId());
				}
			}
		}
	}

	/**
	 * Go through the form parameters and extract the starters used.
	 *
	 * @param params             parameters to extract plays from
	 * @param verificationResult <i>output variable</i> List of errors to report
	 *                           back to the user
	 * @return The starters for this play object
	 */
	@VisibleForTesting
	static Map<Integer, Integer> extractStaters(Map<String, String[]> params,
	        final List<PrintableError> verificationResult) {
		Map<Integer, Integer> playStarters = new HashMap<>();
		String starterPrefix = "starters_";
		params.entrySet().stream().filter(t -> t.getKey().startsWith(starterPrefix)).forEach(t -> {
			String starterIdStr = t.getKey().substring(starterPrefix.length());
			Integer starterId;
			try {
				starterId = Integer.valueOf(starterIdStr);
			} catch (NumberFormatException e) {
				verificationResult.add(new InvalidValue("starter", starterIdStr));
				return;
			}

			Integer starterQuantity;
			try {
				starterQuantity = Integer.valueOf(t.getValue()[0]);
			} catch (NumberFormatException e) {
				verificationResult.add(new InvalidValue("starter count", t.getValue()[0]));
				return;
			}

			playStarters.put(starterId, starterQuantity);
		});
		return playStarters;
	}

	/**
	 * Initialize the model
	 *
	 * @param model model to initialize
	 * @param play  Current play info
	 */
	private static void fillModel(Model model, ServerPlay play) {
		List<String> outcomes = Arrays.stream(Outcome.values()).map(Outcome::toString).collect(Collectors.toList());
		Collections.sort(outcomes);

		List<String> players = Arrays.stream(PlayerCount.values()).map(PlayerCount::toString).toList();

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
		model.addAttribute("playInfo", play);
	}

	/**
	 * Rules to sort a card list
	 * 
	 * @param <T>     {@link CardSet} type
	 * @param cardSet Cards to sort
	 * @return The sorted list of cards.
	 */
	private static <T extends CardSet> List<T> sortCards(Collection<T> cardSet) {
		List<T> cards = new ArrayList<>(cardSet);
		cards.sort(Comparator.comparing(CardSet::getId));
		return cards;
	}
}
