package games.lmdbg.server.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.server.service.WinRate;
import games.lmdbg.server.model.game.repositories.IWinRate;

/**
 * Test {@link FrontPageController}
 */
@SpringBootTest
class FrontPageControllerTest {
	@MockBean
	private WinRate<Hero> heroWinRates;
	
	@MockBean
	private WinRate<Villain> villainWinRates;

	@MockBean
	private WinRate<Mastermind> mastermindWinRates;
	
	@MockBean
	private WinRate<Henchman> henchmanWinRates;
	
	@MockBean
	private WinRate<Scheme> schemeWinRates;
	
	@Autowired
	private FrontPageController controller;

	@Test
	void testMainPage() {
		Map<Hero, IWinRate> testHeroWinRateMap = new HashMap<>(0);
		Mockito.when(heroWinRates.getWinRates()).thenReturn(testHeroWinRateMap);
		Map<Villain, IWinRate> testVillainWinRateMap = new HashMap<>(0);
		Mockito.when(villainWinRates.getWinRates()).thenReturn(testVillainWinRateMap);
		Map<Mastermind, IWinRate> testMastermindWinRateMap = new HashMap<>(0);
		Mockito.when(mastermindWinRates.getWinRates()).thenReturn(testMastermindWinRateMap);
		Map<Henchman, IWinRate> testHenchmanWinRateMap = new HashMap<>(0);
		Mockito.when(henchmanWinRates.getWinRates()).thenReturn(testHenchmanWinRateMap);
		Map<Scheme, IWinRate> testSchemeWinRateMap = new HashMap<>(0);
		Mockito.when(schemeWinRates.getWinRates()).thenReturn(testSchemeWinRateMap);
		
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("index", controller.mainPage(mod));
		
		Mockito.verify(mod).addAttribute("heroes", testHeroWinRateMap);
		Mockito.verify(mod).addAttribute("villains", testVillainWinRateMap);
		Mockito.verify(mod).addAttribute("masterminds", testMastermindWinRateMap);
		Mockito.verify(mod).addAttribute("henchmen", testHenchmanWinRateMap);
		Mockito.verify(mod).addAttribute("schemes", testSchemeWinRateMap);
	}

}
