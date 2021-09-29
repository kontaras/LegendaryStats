package games.lmdbg.server.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;
import games.lmdbg.server.model.game.repositories.IWinRate;

@ExtendWith(MockitoExtension.class)
class WinRateTest {
	@Mock
	CardSetRepository<CardSet, Integer> repo;

	@Mock
	CardCache<CardSet> cache;
	
	@Test
	void testWinRate() {
		WinRate<CardSet> testMe = new WinRate<>(repo, cache);
		
		IWinRate mockRate0 = Mockito.mock(IWinRate.class);
		Mockito.when(mockRate0.getId()).thenReturn(0);
		
		IWinRate mockRate1 = Mockito.mock(IWinRate.class);
		Mockito.when(mockRate1.getId()).thenReturn(1);
		
		IWinRate mockRate2 = Mockito.mock(IWinRate.class);
		Mockito.when(mockRate2.getId()).thenReturn(2);
		
		Mockito.when(repo.findWinRates()).thenReturn(List.of(mockRate0, mockRate2, mockRate1));
		
		CardSet set0 = Mockito.mock(CardSet.class);
		Mockito.when(cache.getById(0)).thenReturn(set0);
		
		CardSet set1 = Mockito.mock(CardSet.class);
		Mockito.when(cache.getById(1)).thenReturn(set1);
		
		CardSet set2 = Mockito.mock(CardSet.class);
		Mockito.when(cache.getById(2)).thenReturn(set2);
		
		Map<CardSet, IWinRate> winRates = testMe.getWinRates();
		Assertions.assertNotNull(winRates);
		Iterator<Entry<CardSet, IWinRate>> winRateIter = winRates.entrySet().iterator();
		
		Entry<CardSet, IWinRate> entry = winRateIter.next();
		Assertions.assertSame(set0, entry.getKey());
		Assertions.assertSame(mockRate0, entry.getValue());
		
		entry = winRateIter.next();
		Assertions.assertSame(set2, entry.getKey());
		Assertions.assertSame(mockRate2, entry.getValue());
		
		entry = winRateIter.next();
		Assertions.assertSame(set1, entry.getKey());
		Assertions.assertSame(mockRate1, entry.getValue());
		
		Assertions.assertFalse(winRateIter.hasNext());
	}
}
