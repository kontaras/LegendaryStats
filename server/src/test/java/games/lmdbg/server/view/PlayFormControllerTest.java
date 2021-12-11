package games.lmdbg.server.view;

import java.util.List;

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
import games.lmdbg.server.model.game.Starter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.CardCache;

/**
 * Test {@link PlayFormController}
 */
@SpringBootTest
class PlayFormControllerTest {
	@MockBean
	private CardCache<Scheme> mockSchemes;
	
	@MockBean
	private CardCache<Mastermind> mockMasterminds;
	
	@MockBean
	private CardCache<Hero> mockHeroes;
	
	@MockBean
	private CardCache<Villain> mockVillains;
	
	@MockBean
	private CardCache<Henchman> mockHenchmen;
	
	@MockBean
	private CardCache<Starter> mockStarters;
	
	@MockBean
	private CardCache<Support> mockSupports;
	
	@Autowired
	private PlayFormController controller;

	@Test
	void testCreateForm() {
		List<Scheme> schemes = List.of();
		Mockito.when(mockSchemes.getCardsInOrder()).thenReturn(schemes);
		List<Mastermind> masterminds = List.of();
		Mockito.when(mockMasterminds.getCardsInOrder()).thenReturn(masterminds);
		List<Hero> heroes = List.of();
		Mockito.when(mockHeroes.getCardsInOrder()).thenReturn(heroes);
		List<Villain> villains = List.of();
		Mockito.when(mockVillains.getCardsInOrder()).thenReturn(villains);
		List<Henchman> henchmen = List.of();
		Mockito.when(mockHenchmen.getCardsInOrder()).thenReturn(henchmen);
		List<Starter> starters = List.of();
		Mockito.when(mockStarters.getCardsInOrder()).thenReturn(starters);
		List<Support> supports = List.of();
		Mockito.when(mockSupports.getCardsInOrder()).thenReturn(supports);
		
		Model mod = Mockito.mock(Model.class);
		Assertions.assertEquals("playForm", controller.createForm(mod));
		
		Mockito.verify(mod).addAttribute("schemes", schemes);
		Mockito.verify(mod).addAttribute("masterminds", masterminds);
		Mockito.verify(mod).addAttribute("heroes", heroes);
		Mockito.verify(mod).addAttribute("villains", villains);
		Mockito.verify(mod).addAttribute("henchmen", henchmen);
		Mockito.verify(mod).addAttribute("starters", starters);
		Mockito.verify(mod).addAttribute("supports", supports);
	}
}
