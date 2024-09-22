package games.lmdbg.server.view;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import games.lmdbg.rules.model.Board;
import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Starter;
import games.lmdbg.rules.model.Support;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.rules.set.CardLookupKt;
import games.lmdbg.rules.set.core.Boards;
import games.lmdbg.rules.set.core.Henchmen;
import games.lmdbg.rules.set.core.Heroes;
import games.lmdbg.rules.set.core.Masterminds;
import games.lmdbg.rules.set.core.Schemes;
import games.lmdbg.rules.set.core.Starters;
import games.lmdbg.rules.set.core.Supports;
import games.lmdbg.rules.set.core.Villains;
import games.lmdbg.rules.verifier.InvalidValue;
import games.lmdbg.rules.verifier.MissingField;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;
import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.service.PlayStore;
import games.lmdbg.server.test.util.ReplacedLookupTable;
import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * Test {@link PlayFormController}
 */
@SuppressWarnings({ "static-method", "unchecked" })
class PlayFormControllerTest {

	/**
	 * Test {@link PlayFormController#createForm(Model)}
	 */
	@Test
	void testCreateForm() {
		List<Scheme> schemes = List.of(new Scheme(0, null, null, null), new Scheme(1, null, null, null));
		List<Mastermind> masterminds =
		        List.of(new Mastermind(0, null, null, null), new Mastermind(1, null, null, null));
		List<Hero> heroes = List.of(new Hero(0, null, null, null, List.of()), new Hero(1, null, null, null, List.of()));
		List<Villain> villains = List.of(new Villain(0, null, null, null), new Villain(1, null, null, null));
		List<Henchman> henchmen = List.of(new Henchman(0, null, null, null), new Henchman(1, null, null, null));
		List<Starter> starters = List.of(new Starter(0, null, null, null), new Starter(1, null, null, null));
		List<Support> supports = List.of(new Support(0, null, null, null), new Support(1, null, null, null));
		List<Board> boards = List.of(new Board(0, null, null, null), new Board(1, null, null, null));

		Model mod = mock(Model.class);
		try (ReplacedLookupTable<Scheme> _shemes = new ReplacedLookupTable<>(CardLookupKt.getSchemesById(), schemes);
		        ReplacedLookupTable<Mastermind> _masterminds =
		                new ReplacedLookupTable<>(CardLookupKt.getMastermindsById(), masterminds);
		        ReplacedLookupTable<Hero> _heroes = new ReplacedLookupTable<>(CardLookupKt.getHeroesById(), heroes);
		        ReplacedLookupTable<Villain> _villains =
		                new ReplacedLookupTable<>(CardLookupKt.getVillainsById(), villains);
		        ReplacedLookupTable<Henchman> _henchmen =
		                new ReplacedLookupTable<>(CardLookupKt.getHenchmanById(), henchmen);
		        ReplacedLookupTable<Starter> _starters =
		                new ReplacedLookupTable<>(CardLookupKt.getStartersById(), starters);
		        ReplacedLookupTable<Support> _supports =
		                new ReplacedLookupTable<>(CardLookupKt.getSupportsById(), supports);
		        ReplacedLookupTable<Board> _boards = new ReplacedLookupTable<>(CardLookupKt.getBoardsById(), boards);) {
			Assertions.assertEquals("play", PlayFormController.createForm(mod));
		}

		verify(mod).addAttribute("schemes", schemes);
		verify(mod).addAttribute("masterminds", masterminds);
		verify(mod).addAttribute("heroes", heroes);
		verify(mod).addAttribute("villains", villains);
		verify(mod).addAttribute("henchmen", henchmen);
		verify(mod).addAttribute("starters", starters);
		verify(mod).addAttribute("supports", supports);
		verify(mod).addAttribute("boards", boards);

		ServerPlay expected = new ServerPlay();
		expected.setBoard(Boards.INSTANCE.getHQ().getId());
		expected.setSupports(Set.of(Supports.INSTANCE.getSHIELD_OFFICER().getId()));
		verify(mod).addAttribute("playInfo", expected);
	}

	/**
	 * Test newPlay finding field binding errors
	 */
	@Test
	void testNewPlayFieldErrors() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		when(bindingResult.hasErrors()).thenReturn(true);
		when(bindingResult.getFieldErrors())
		        .thenReturn(List.of(new FieldError("obj", "field1", null, false, null, null, null),
		                new FieldError("obj", "field2", "bad value", false, null, null, null)));

		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(new InvalidValue("field1", "null").getMessage(),
		        new InvalidValue("field2", "bad value").getMessage()), errorList);
		verify(bindingResult, never()).addError(any());
	}

	/**
	 * Test newPlay finding global binding errors
	 */
	@Test
	void testNewPlayGlobalErrors() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		when(bindingResult.hasGlobalErrors()).thenReturn(true);

		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(), errorList);
		verify(bindingResult, never()).addError(any());
	}

	/**
	 * Test newPlay with bad starters
	 */
	@Test
	void testNewPlayBadStarter() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		when(request.getParameterMap()).thenReturn(Map.of("starters_cat", new String[] { "dog" }));

		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(new InvalidValue("starter", "cat").getMessage()), errorList);
		verify(bindingResult, never()).addError(any());
	}

	/**
	 * Test newPlay with verify errors
	 */
	@Test
	void testNewPlayBadVerify() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		play.setBoard(1);
		play.setMastermind(2);
		play.setScheme(3);
		play.setPlayers(PlayerCount.ADVANCED_SOLO);
		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(new MissingField("game outcome").getMessage()), errorList);
		verify(bindingResult, never()).addError(any());
	}

	/**
	 * Test newPlay with a null user
	 */
	@Test
	void testNewPlayNullUser() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		play.setBoard(Boards.INSTANCE.getHQ().getId());
		play.setMastermind(Masterminds.INSTANCE.getEPIC_NAX_LORD_OF_CRIMSON_BOG().getId());
		play.setScheme(Schemes.INSTANCE.getPORTALS_TO_THE_DARK_DIMENSION().getId());
		play.setPlayers(PlayerCount.ADVANCED_SOLO);
		play.setOutcome(Outcome.LOSS_SCHEME);
		play.setHeroes(Set.of(Heroes.INSTANCE.getBLACK_WIDOW().getId(), Heroes.INSTANCE.getCAPTAIN_AMERICA().getId(),
		        Heroes.INSTANCE.getEMMA_FROST().getId()));
		play.setVillains(Set.of(Villains.INSTANCE.getHYDRA().getId()));
		play.setHenchmen(Set.of(Henchmen.INSTANCE.getSAVAGE_LAND_MUTATES().getId()));

		when(request.getParameterMap())
		        .thenReturn(Map.of("starters_" + Starters.INSTANCE.getSHIELD().getId(), new String[] { "1" }));

		when(request.getRemoteUser()).thenReturn(null);

		when(bindingResult.hasGlobalErrors()).thenReturn(false, true);

		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(), errorList);
		verify(bindingResult)
		        .addError(new ObjectError("globalError", "Error with login. Please log out and log back in."));
	}

	/**
	 * Test newPlay with an invalid user
	 */
	@Test
	void testNewPlayBadUser() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		play.setBoard(Boards.INSTANCE.getHQ().getId());
		play.setMastermind(Masterminds.INSTANCE.getEPIC_NAX_LORD_OF_CRIMSON_BOG().getId());
		play.setScheme(Schemes.INSTANCE.getPORTALS_TO_THE_DARK_DIMENSION().getId());
		play.setPlayers(PlayerCount.ADVANCED_SOLO);
		play.setOutcome(Outcome.LOSS_SCHEME);
		play.setHeroes(Set.of(Heroes.INSTANCE.getBLACK_WIDOW().getId(), Heroes.INSTANCE.getCAPTAIN_AMERICA().getId(),
		        Heroes.INSTANCE.getEMMA_FROST().getId()));
		play.setVillains(Set.of(Villains.INSTANCE.getHYDRA().getId()));
		play.setHenchmen(Set.of(Henchmen.INSTANCE.getSAVAGE_LAND_MUTATES().getId()));

		when(request.getParameterMap())
		        .thenReturn(Map.of("starters_" + Starters.INSTANCE.getSHIELD().getId(), new String[] { "1" }));

		when(bindingResult.hasGlobalErrors()).thenReturn(false, true);

		PlayFormController underTest = new PlayFormController();

		AccountsRepository accounts = mock(AccountsRepository.class);
		when(accounts.findByUserName("invalid")).thenReturn(null);

		underTest.accounts = accounts;

		when(request.getRemoteUser()).thenReturn("invalid");

		ArgumentCaptor<List<String>> errors = ArgumentCaptor.forClass(List.class);

		Assertions.assertEquals("play", underTest.newPlay(request, mod, play, bindingResult));
		verify(mod).addAttribute(ArgumentMatchers.eq("verificationErrors"), errors.capture());
		List<String> errorList = errors.getValue();
		Assertions.assertIterableEquals(List.of(), errorList);
		verify(bindingResult)
		        .addError(new ObjectError("globalError", "Error with login. Please log out and log back in."));
	}

	/**
	 * Test newPlay saving a valid play
	 */
	@Test
	void testNewPlayStorePlay() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		play.setBoard(Boards.INSTANCE.getHQ().getId());
		play.setMastermind(Masterminds.INSTANCE.getEPIC_NAX_LORD_OF_CRIMSON_BOG().getId());
		play.setScheme(Schemes.INSTANCE.getPORTALS_TO_THE_DARK_DIMENSION().getId());
		play.setPlayers(PlayerCount.ADVANCED_SOLO);
		play.setOutcome(Outcome.LOSS_SCHEME);
		play.setHeroes(Set.of(Heroes.INSTANCE.getBLACK_WIDOW().getId(), Heroes.INSTANCE.getCAPTAIN_AMERICA().getId(),
		        Heroes.INSTANCE.getEMMA_FROST().getId()));
		play.setVillains(Set.of(Villains.INSTANCE.getHYDRA().getId()));
		play.setHenchmen(Set.of(Henchmen.INSTANCE.getSAVAGE_LAND_MUTATES().getId()));

		when(request.getParameterMap())
		        .thenReturn(Map.of("starters_" + Starters.INSTANCE.getSHIELD().getId(), new String[] { "1" }));

		PlayFormController underTest = new PlayFormController();

		Account account = new Account();
		account.setId(3);
		AccountsRepository accounts = mock(AccountsRepository.class);
		when(accounts.findByUserName("user")).thenReturn(account);
		underTest.accounts = accounts;
		when(request.getRemoteUser()).thenReturn("user");

		PlayStore store = mock(PlayStore.class);
		underTest.serializer = store;

		Assertions.assertEquals("redirect:/", underTest.newPlay(request, mod, play, bindingResult));
		verify(store).createPlay(play);
		verify(mod, never()).addAttribute(ArgumentMatchers.eq("verificationErrors"), any());
		verify(bindingResult, never()).addError(any());

		// Verify that the play was filled in
		Assertions.assertEquals(3, play.getUser());
		Assertions.assertEquals(Set.of(Supports.INSTANCE.getSHIELD_OFFICER().getId()), play.getSupports());
	}

	/**
	 * Test {@link PlayFormController#extractStaters(java.util.Map, List)}
	 */
	@Test
	void testExtractStaters() {
		Map<String, String[]> params = new HashMap<>();
		List<PrintableError> errors = new ArrayList<>();

		// Assert that if there are no parameters then there are no starters extracted
		// and no errors
		Assertions.assertEquals(Map.of(), PlayFormController.extractStaters(params, errors));
		Assertions.assertEquals(List.of(), errors);

		// Assert that if the params are unrelated then there are no starters extracted
		// and no errors
		params.put("unrealted_key", new String[] { "5" });
		params.put("another_key", new String[] {});
		params.put("a_third_key", new String[] { "cat", "42" });
		Assertions.assertEquals(Map.of(), PlayFormController.extractStaters(params, errors));
		Assertions.assertEquals(List.of(), errors);

		// Add a started and make sure it is extracted
		params.put("starters_3", new String[] { "5" });
		Assertions.assertEquals(Map.of(3, 5), PlayFormController.extractStaters(params, errors));
		Assertions.assertEquals(List.of(), errors);

		// Add another and make sure all are extracted
		params.put("starters_17", new String[] { "-1" });
		Assertions.assertEquals(Map.of(3, 5, 17, -1), PlayFormController.extractStaters(params, errors));
		Assertions.assertEquals(List.of(), errors);

		// Add some bad params and see that they are caught
		params.put("starters_one", new String[] { "4" });
		params.put("starters_8", new String[] { "two" });
		Assertions.assertEquals(Map.of(3, 5, 17, -1), PlayFormController.extractStaters(params, errors));
		Assertions.assertEquals(2, errors.size());
		Assertions.assertTrue(errors.contains(new InvalidValue("starter", "one")));
		Assertions.assertTrue(errors.contains(new InvalidValue("starter count", "two")));
	}
}
