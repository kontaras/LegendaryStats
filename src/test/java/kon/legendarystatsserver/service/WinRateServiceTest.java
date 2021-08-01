package kon.legendarystatsserver.service;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.IntFunction;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import kon.legendarystatsserver.model.game.CardSet;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.CardSetRepository;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;
import kon.legendarystatsserver.model.game.repositories.VillainsRepository;

/**
 * Tests for {@link WinRateService}
 */
@SpringBootTest
class WinRateServiceTest {
	@MockBean
	private CardDirectoryService cd;

	@Mock
	CardSetRepository<CardSet, Integer> mockRepo;

	@Mock
	IntFunction<CardSet> mockLooup;

	@Autowired
	private WinRateService testMe;

	@MockBean
	private HeroesRepository heroRepo;

	@MockBean
	private VillainsRepository villainRepo;

	@Test
	void testEmptyRepo() {
		Mockito.when(mockRepo.findAll()).thenReturn(Lists.emptyList());
		Assertions.assertTrue(testMe.getCardSetWinRates(mockRepo, (x) -> null).isEmpty());
	}

	@Test
	void testSingleCardSet() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(mockRepo.findWinRates()).thenReturn(List.of(win0));

		CardSet hero0 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(3)).thenReturn(hero0);

		Map<CardSet, IWinRate> winRates = testMe.getCardSetWinRates(mockRepo, mockLooup);
		Iterator<Entry<CardSet, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<CardSet, IWinRate> entry = iter.next();
		Assertions.assertSame(hero0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}

	@Test
	void testMultipleCardSets() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);

		IWinRate win1 = Mockito.mock(IWinRate.class);
		Mockito.when(win1.getId()).thenReturn(7);

		IWinRate win2 = Mockito.mock(IWinRate.class);
		Mockito.when(win2.getId()).thenReturn(1000);
		Mockito.when(mockRepo.findWinRates()).thenReturn(List.of(win0, win1, win2));

		CardSet hero0 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(3)).thenReturn(hero0);

		CardSet hero1 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(7)).thenReturn(hero1);

		CardSet hero2 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(1000)).thenReturn(hero2);

		Map<CardSet, IWinRate> winRates = testMe.getCardSetWinRates(mockRepo, mockLooup);
		Iterator<Entry<CardSet, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<CardSet, IWinRate> entry = iter.next();
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

	@Test
	void testSingleHero() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(heroRepo.findWinRates()).thenReturn(List.of(win0));

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
	void testSingleVillain() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(villainRepo.findWinRates()).thenReturn(List.of(win0));

		Villain villain0 = Mockito.mock(Villain.class);
		Mockito.when(cd.getVillainById(3)).thenReturn(villain0);

		Map<Villain, IWinRate> winRates = testMe.getVillainWinRates();
		Iterator<Entry<Villain, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<Villain, IWinRate> entry = iter.next();
		Assertions.assertSame(villain0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}
}
