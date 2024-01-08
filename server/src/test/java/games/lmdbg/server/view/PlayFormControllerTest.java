package games.lmdbg.server.view;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.ui.Model;

import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Starter;
import games.lmdbg.rules.model.Support;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.rules.set.CardLookupKt;
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
		
		Model mod = Mockito.mock(Model.class);
		try (
				ReplacedLookupTable<Scheme> _shemes = new ReplacedLookupTable<>(CardLookupKt.getSchemesById(), schemes);
				ReplacedLookupTable<Mastermind> _masterminds = new ReplacedLookupTable<>(CardLookupKt.getMastermindsById(), masterminds);
				ReplacedLookupTable<Hero> _heroes = new ReplacedLookupTable<>(CardLookupKt.getHeroesById(), heroes);
				ReplacedLookupTable<Villain> _villains = new ReplacedLookupTable<>(CardLookupKt.getVillainsById(), villains);
				ReplacedLookupTable<Henchman> _henchmen = new ReplacedLookupTable<>(CardLookupKt.getHenchmanById(), henchmen);
				ReplacedLookupTable<Starter> _starters = new ReplacedLookupTable<>(CardLookupKt.getStartersById(), starters);
				ReplacedLookupTable<Support> _supports = new ReplacedLookupTable<>(CardLookupKt.getSupportsById(), supports);
				) {
			Assertions.assertEquals("play", PlayFormController.createForm(mod));
		}
		
		Mockito.verify(mod).addAttribute("schemes", schemes);
		Mockito.verify(mod).addAttribute("masterminds", masterminds);
		Mockito.verify(mod).addAttribute("heroes", heroes);
		Mockito.verify(mod).addAttribute("villains", villains);
		Mockito.verify(mod).addAttribute("henchmen", henchmen);
		Mockito.verify(mod).addAttribute("starters", starters);
		Mockito.verify(mod).addAttribute("supports", supports);
		//TODO: TEST playInfo
	}
}
