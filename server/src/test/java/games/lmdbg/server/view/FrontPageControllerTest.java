package games.lmdbg.server.view;

import games.lmdbg.rules.model.CardSet;
import games.lmdbg.server.model.IWinRate;
import games.lmdbg.server.service.Schema;
import games.lmdbg.server.service.SqlWinRate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

/**
 * Test {@link FrontPageController}
 */
@SpringBootTest
class FrontPageControllerTest {
	@MockBean
	private SqlWinRate winRates;

	@Autowired
	private FrontPageController controller;

	@Test
	void testMainPage() {
		Map<CardSet, IWinRate> testHeroWinRateMap = new HashMap<>(0);
		Mockito.when(this.winRates.getSetWinRates(Schema.ComponentType.HERO)).thenReturn(testHeroWinRateMap);
		Map<CardSet, IWinRate> testVillainWinRateMap = new HashMap<>(0);
		Mockito.when(this.winRates.getSetWinRates(Schema.ComponentType.HERO)).thenReturn(testVillainWinRateMap);
		Map<CardSet, IWinRate> testMastermindWinRateMap = new HashMap<>(0);
		Mockito.when(this.winRates.getSetWinRates(Schema.ComponentType.HERO)).thenReturn(testMastermindWinRateMap);
		Map<CardSet, IWinRate> testHenchmanWinRateMap = new HashMap<>(0);
		Mockito.when(this.winRates.getSetWinRates(Schema.ComponentType.HERO)).thenReturn(testHenchmanWinRateMap);
		Map<CardSet, IWinRate> testSchemeWinRateMap = new HashMap<>(0);
		Mockito.when(this.winRates.getSetWinRates(Schema.ComponentType.HERO)).thenReturn(testSchemeWinRateMap);

		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("index", controller.mainPage(mod));

		Mockito.verify(mod).addAttribute("heroes", testHeroWinRateMap);
		Mockito.verify(mod).addAttribute("villains", testVillainWinRateMap);
		Mockito.verify(mod).addAttribute("masterminds", testMastermindWinRateMap);
		Mockito.verify(mod).addAttribute("henchmen", testHenchmanWinRateMap);
		Mockito.verify(mod).addAttribute("schemes", testSchemeWinRateMap);
	}

}
