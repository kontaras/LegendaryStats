package kon.legendarystatsserver.service;

import org.junit.jupiter.api.Assertions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;

/**
 * Tests for {@link WinRateService}
 */
@SpringBootTest
class WinRateServiceTest {
	@MockBean
	private HeroesRepository repo;
	
	@MockBean
	private CardDirectoryService cd;
	
	@Autowired
	private WinRateService testMe;
	
	@Test
	void testEmptyRepo() {
		Assertions.assertTrue(testMe.getHeroWinRates().isEmpty());
	}
	
	@Test
	void testSingle() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(repo.findHeroWinRates()).thenReturn(List.of(win0));
		
		Hero hero0 = Mockito.mock(Hero.class);
		Mockito.when(cd.getHeroById(3)).thenReturn(hero0);
		
		Map<Hero, IWinRate> winRates = testMe.getHeroWinRates();
		Iterator<Entry<Hero, IWinRate>> iter = winRates.entrySet().iterator();
		
		Entry<Hero, IWinRate> entry = iter.next();
		Assertions.assertSame(hero0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}
	
	@Test
	void testMultiple() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		
		IWinRate win1 = Mockito.mock(IWinRate.class);
		Mockito.when(win1.getId()).thenReturn(7);
		
		IWinRate win2 = Mockito.mock(IWinRate.class);
		Mockito.when(win2.getId()).thenReturn(1000);
		Mockito.when(repo.findHeroWinRates()).thenReturn(List.of(win0, win1, win2));
		
		Hero hero0 = Mockito.mock(Hero.class);
		Mockito.when(cd.getHeroById(3)).thenReturn(hero0);
		
		Hero hero1 = Mockito.mock(Hero.class);
		Mockito.when(cd.getHeroById(7)).thenReturn(hero1);
		
		Hero hero2 = Mockito.mock(Hero.class);
		Mockito.when(cd.getHeroById(1000)).thenReturn(hero2);
		
		Map<Hero, IWinRate> winRates = testMe.getHeroWinRates();
		Iterator<Entry<Hero, IWinRate>> iter = winRates.entrySet().iterator();
		
		Entry<Hero, IWinRate> entry = iter.next();
		Assertions.assertSame(hero0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		
		entry = iter.next();
		Assertions.assertSame(hero1, entry.getKey());
		Assertions.assertSame(win1, entry.getValue());
		
		entry = iter.next();
		Assertions.assertSame(hero2, entry.getKey());
		Assertions.assertSame(win2, entry.getValue());
		
		
		Assertions.assertFalse(iter.hasNext());
	}
}
