package kon.legendarystatsserver.view;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import kon.legendarystatsserver.model.game.Henchman;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Mastermind;
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.IWinRate;
import kon.legendarystatsserver.service.WinRateService;

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
		
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("index", controller.mainPage(mod));
		
		Mockito.verify(mod).addAttribute("heroes", testHeroWinRateMap);
		Mockito.verify(mod).addAttribute("villains", testVillainWinRateMap);
		Mockito.verify(mod).addAttribute("masterminds", testMastermindWinRateMap);
		Mockito.verify(mod).addAttribute("henchmen", testHenchmanWinRateMap);
	}

}
