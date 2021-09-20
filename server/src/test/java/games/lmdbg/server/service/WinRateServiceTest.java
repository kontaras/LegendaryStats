package games.lmdbg.server.service;

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

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.CardSetRepository;
import games.lmdbg.server.model.game.repositories.HenchmenRepository;
import games.lmdbg.server.model.game.repositories.HeroesRepository;
import games.lmdbg.server.model.game.repositories.IWinRate;
import games.lmdbg.server.model.game.repositories.MastermindsRepository;
import games.lmdbg.server.model.game.repositories.SchemesRepository;
import games.lmdbg.server.model.game.repositories.VillainsRepository;

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
	
	@MockBean
	private MastermindsRepository mastermindRepo;
	
	@MockBean
	private HenchmenRepository henchmanRepo;
	
	@MockBean
	private SchemesRepository schemeRepo;

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

		CardSet card0 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(3)).thenReturn(card0);

		Map<CardSet, IWinRate> winRates = testMe.getCardSetWinRates(mockRepo, mockLooup);
		Iterator<Entry<CardSet, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<CardSet, IWinRate> entry = iter.next();
		Assertions.assertSame(card0, entry.getKey());
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

		CardSet card0 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(3)).thenReturn(card0);

		CardSet card1 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(7)).thenReturn(card1);

		CardSet card2 = Mockito.mock(CardSet.class);
		Mockito.when(mockLooup.apply(1000)).thenReturn(card2);

		Map<CardSet, IWinRate> winRates = testMe.getCardSetWinRates(mockRepo, mockLooup);
		Iterator<Entry<CardSet, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<CardSet, IWinRate> entry = iter.next();
		Assertions.assertSame(card0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());

		entry = iter.next();
		Assertions.assertSame(card1, entry.getKey());
		Assertions.assertSame(win1, entry.getValue());

		entry = iter.next();
		Assertions.assertSame(card2, entry.getKey());
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
	
	@Test
	void testSingleMastermind() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(mastermindRepo.findWinRates()).thenReturn(List.of(win0));

		Mastermind mastermind0 = Mockito.mock(Mastermind.class);
		Mockito.when(cd.getMastermindById(3)).thenReturn(mastermind0);

		Map<Mastermind, IWinRate> winRates = testMe.getMastermindWinRates();
		Iterator<Entry<Mastermind, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<Mastermind, IWinRate> entry = iter.next();
		Assertions.assertSame(mastermind0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}
	
	@Test
	void testSingleHenchman() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(henchmanRepo.findWinRates()).thenReturn(List.of(win0));

		Henchman henchman0 = Mockito.mock(Henchman.class);
		Mockito.when(cd.getHenchmanById(3)).thenReturn(henchman0);

		Map<Henchman, IWinRate> winRates = testMe.getHenchmanWinRates();
		Iterator<Entry<Henchman, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<Henchman, IWinRate> entry = iter.next();
		Assertions.assertSame(henchman0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}
	
	@Test
	void testSingleScheme() {
		IWinRate win0 = Mockito.mock(IWinRate.class);
		Mockito.when(win0.getId()).thenReturn(3);
		Mockito.when(schemeRepo.findWinRates()).thenReturn(List.of(win0));

		Scheme scheme0 = Mockito.mock(Scheme.class);
		Mockito.when(cd.getSchemeById(3)).thenReturn(scheme0);

		Map<Scheme, IWinRate> winRates = testMe.getSchemeWinRates();
		Iterator<Entry<Scheme, IWinRate>> iter = winRates.entrySet().iterator();

		Entry<Scheme, IWinRate> entry = iter.next();
		Assertions.assertSame(scheme0, entry.getKey());
		Assertions.assertSame(win0, entry.getValue());
		Assertions.assertFalse(iter.hasNext());
	}
}