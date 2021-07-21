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

import kon.legendarystatsserver.model.game.Hero;
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
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("index", controller.mainPage(mod));
		Mockito.verify(mod).addAttribute("heroes", testHeroWinRateMap);
	}

}
