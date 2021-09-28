package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.tinylog.Logger;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;

/**
 * A cache of cards in a {@link CardSetRepository}, since that list should never
 * change for a running system and fetching the data is a round trip call to the
 * database.
 * 
 * @param <C> The {@link CardSet} type being cached.
 */
public class CardCache<C extends CardSet> {
	/** Lookup table to find cards given an id */
	private final Map<Integer, C> cardsById;

	/** All of the cards, in name order */
	private final List<C> cardList;

	/**
	 * @param repo The repository the data of which is being cached.
	 */
	public CardCache(CardSetRepository<C, Integer> repo) {
		cardsById = new HashMap<>();
		cardList = new ArrayList<>();

		Logger.info("Starting to preload " + repo.getClass());
		for (C hero : repo.findAll()) {
			cardsById.put(hero.getId(), hero);
			cardList.add(hero);
		}
		Logger.info("Finished preloading " + repo.getClass());

		cardList.sort(null);
	}

	/**
	 * Find a card given its id
	 * 
	 * @param id the id to look for
	 * @return A {@link CardSet} with the given id
	 */
	public C getById(Integer id) {
		return cardsById.get(id);
	}

	/**
	 * Get the cards, in sorted order.
	 * 
	 * @return An iterator over all of the {@link CardSet}s
	 */
	public Iterator<C> getCardsInOrder() {
		return cardList.iterator();
	}
}
