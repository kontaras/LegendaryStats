package games.lmdbg.server.view;

import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.CardCache;

/**
 * Tests for {@link ScriptsController}
 */
@SpringBootTest
class ScriptsControllerTest {
	@MockBean
	private CardCache<Villain> mockVillains;
	
	@MockBean
	private CardCache<Henchman> mockHenchmen;

	@Autowired
	private ScriptsController controller;

	/**
	 * Test that {@link ScriptsController#cardDeref(Model)} properly initializes its
	 * models.
	 */
	@Test
	void testCardDeref() {
		Map<Integer, Villain> villains = Map.of();
		Mockito.when(mockVillains.getAllById()).thenReturn(villains);
		Map<Integer, Henchman> henchmen = Map.of();
		Mockito.when(mockHenchmen.getAllById()).thenReturn(henchmen);
		
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("scripts/cardSet", controller.cardDeref(mod));
		
		Mockito.verify(mod).addAttribute("villainsMap", villains);
		Mockito.verify(mod).addAttribute("henchmenMap", henchmen);
	}

}
