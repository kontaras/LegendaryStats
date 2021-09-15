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

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.IWinRate;
import games.lmdbg.server.service.WinRateService;

/**
 * Test {@link FrontPageController}
 */
@SpringBootTest
class FrontPageControllerTest {
	@MockBean
	private WinRateService winRates;
	
	@Autowired
	private FrontPageController controller;

	@Test
	void testMainPage() {
		Map<Hero, IWinRate> testHeroWinRateMap = new HashMap<>(0);
		Mockito.when(winRates.getHeroWinRates()).thenReturn(testHeroWinRateMap);
		Map<Villain, IWinRate> testVillainWinRateMap = new HashMap<>(0);
		Mockito.when(winRates.getVillainWinRates()).thenReturn(testVillainWinRateMap);
		Map<Mastermind, IWinRate> testMastermindWinRateMap = new HashMap<>(0);
		Mockito.when(winRates.getMastermindWinRates()).thenReturn(testMastermindWinRateMap);
		Map<Henchman, IWinRate> testHenchmanWinRateMap = new HashMap<>(0);
		Mockito.when(winRates.getHenchmanWinRates()).thenReturn(testHenchmanWinRateMap);
		Map<Scheme, IWinRate> testSchemeWinRateMap = new HashMap<>(0);
		Mockito.when(winRates.getSchemeWinRates()).thenReturn(testSchemeWinRateMap);
		
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("index", controller.mainPage(mod));
		
		Mockito.verify(mod).addAttribute("heroes", testHeroWinRateMap);
		Mockito.verify(mod).addAttribute("villains", testVillainWinRateMap);
		Mockito.verify(mod).addAttribute("masterminds", testMastermindWinRateMap);
		Mockito.verify(mod).addAttribute("henchmen", testHenchmanWinRateMap);
		Mockito.verify(mod).addAttribute("schemes", testSchemeWinRateMap);
	}

}
