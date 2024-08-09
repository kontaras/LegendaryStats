package games.lmdbg.server.view;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import games.lmdbg.rules.model.Board;
import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Starter;
import games.lmdbg.rules.model.Support;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.rules.set.CardLookupKt;
import games.lmdbg.rules.set.core.Boards;
import games.lmdbg.rules.set.core.Supports;
import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.test.util.ReplacedLookupTable;
/**
 * Test {@link PlayFormController}
 */
class PlayFormControllerTest {

	/**
	 * Test {@link PlayFormController#createForm(Model)}
	 */
	@Test
	void testCreateForm() {
		List<Scheme> schemes = List.of(new Scheme(0, null, null, null), 
				new Scheme(1, null, null, null));
		List<Mastermind> masterminds = List.of(new Mastermind(0, null, null, null), 
				new Mastermind(1, null, null, null));
		List<Hero> heroes = List.of(new Hero(0, null, null, null, List.of()), 
				new Hero(1, null, null, null, List.of()));
		List<Villain> villains = List.of(new Villain(0, null, null, null), 
				new Villain(1, null, null, null));
		List<Henchman> henchmen = List.of(new Henchman(0, null, null, null), 
				new Henchman(1, null, null, null));
		List<Starter> starters = List.of(new Starter(0, null, null, null), 
				new Starter(1, null, null, null));
		List<Support> supports = List.of(new Support(0, null, null, null), 
				new Support(1, null, null, null));
		List<Board> boards = List.of(new Board(0, null, null, null), 
				new Board(1, null, null, null));
		
		Model mod = mock(Model.class);
		try (
				ReplacedLookupTable<Scheme> _shemes = new ReplacedLookupTable<>(CardLookupKt.getSchemesById(), schemes);
				ReplacedLookupTable<Mastermind> _masterminds = new ReplacedLookupTable<>(CardLookupKt.getMastermindsById(), masterminds);
				ReplacedLookupTable<Hero> _heroes = new ReplacedLookupTable<>(CardLookupKt.getHeroesById(), heroes);
				ReplacedLookupTable<Villain> _villains = new ReplacedLookupTable<>(CardLookupKt.getVillainsById(), villains);
				ReplacedLookupTable<Henchman> _henchmen = new ReplacedLookupTable<>(CardLookupKt.getHenchmanById(), henchmen);
				ReplacedLookupTable<Starter> _starters = new ReplacedLookupTable<>(CardLookupKt.getStartersById(), starters);
				ReplacedLookupTable<Support> _supports = new ReplacedLookupTable<>(CardLookupKt.getSupportsById(), supports);
				ReplacedLookupTable<Board> _boards = new ReplacedLookupTable<>(CardLookupKt.getBoardsById(), boards);
				) {
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
	
	@Test
	void testNewPlay() {
		Model mod = mock(Model.class);
		HttpServletRequest request = mock(HttpServletRequest.class);
		BindingResult bindingResult = mock(BindingResult.class);
		ServerPlay play = new ServerPlay();
		Assertions.assertEquals("play", new PlayFormController().newPlay(request, mod, play, bindingResult));
	}
}
