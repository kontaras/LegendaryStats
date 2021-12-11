package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT) // Needed so that comparison is not order dependent
class CardCacheTest {
	@Mock
	private CardSetRepository<CardSet, Integer> mockRepo;

	@Test
	void testCaching() {
		List<CardSet> cards = new ArrayList<>(3);

		CardSet mockCardSet1 = Mockito.mock(CardSet.class);
		Mockito.when(mockCardSet1.getId()).thenReturn(Integer.valueOf(1));
		cards.add(mockCardSet1);

		CardSet mockCardSet2 = Mockito.mock(CardSet.class);
		Mockito.when(mockCardSet2.getId()).thenReturn(Integer.valueOf(2));
		cards.add(mockCardSet2);

		CardSet mockCardSet3 = Mockito.mock(CardSet.class);
		Mockito.when(mockCardSet3.getId()).thenReturn(Integer.valueOf(3));

		Mockito.when(mockCardSet1.compareTo(mockCardSet2)).thenReturn(-1);
		Mockito.when(mockCardSet1.compareTo(mockCardSet3)).thenReturn(-1);

		Mockito.when(mockCardSet2.compareTo(mockCardSet1)).thenReturn(1);
		Mockito.when(mockCardSet2.compareTo(mockCardSet3)).thenReturn(-1);

		Mockito.when(mockCardSet3.compareTo(mockCardSet1)).thenReturn(-1);
		Mockito.when(mockCardSet3.compareTo(mockCardSet2)).thenReturn(-1);

		Mockito.when(mockRepo.findAll()).thenReturn(cards);
		CardCache<CardSet> cache = new CardCache<>(mockRepo);

		Assertions.assertEquals(mockCardSet2, cache.getById(2));
		Assertions.assertEquals(mockCardSet1, cache.getById(1));
		Assertions.assertEquals(null, cache.getById(3));

		Assertions.assertEquals(List.of(mockCardSet1, mockCardSet2), cache.getCardsInOrder());
		Assertions.assertEquals(Map.of(1, mockCardSet1, 2, mockCardSet2), cache.getAllById());

		// Add a new set after the cache is built
		cards.add(mockCardSet3);
		
		Assertions.assertEquals(mockCardSet2, cache.getById(2));
		Assertions.assertEquals(mockCardSet1, cache.getById(1));
		Assertions.assertEquals(null, cache.getById(3));

		Assertions.assertEquals(List.of(mockCardSet1, mockCardSet2), cache.getCardsInOrder());
		Assertions.assertEquals(Map.of(1, mockCardSet1, 2, mockCardSet2), cache.getAllById());
	}

}
