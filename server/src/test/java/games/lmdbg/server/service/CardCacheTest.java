package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;

class CardCacheTest {
	@Mock
	private CardSetRepository<CardSet, Integer> mockRepo;

	@Test
	private void testCaching() {
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
		CardCache<CardSet> testMe = new CardCache<>(mockRepo);

		Assertions.assertEquals(mockCardSet2, testMe.getById(2));
		Assertions.assertEquals(mockCardSet1, testMe.getById(1));
		Assertions.assertEquals(null, testMe.getById(3));

		Assertions.assertEquals(List.of(mockCardSet1, mockCardSet2), testMe.getCardsInOrder());

		// Add a new set after the cache is built
		cards.add(mockCardSet3);
	}

}
