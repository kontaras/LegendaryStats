package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.repository.CrudRepository;
import org.tinylog.Logger;

import games.lmdbg.server.model.game.CardSet;

/**
 * A cache of cards in a {@link CrudRepository}, since that list should never
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
	public CardCache(CrudRepository<C, Integer> repo) {
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
	public List<C> getCardsInOrder() {
		return Collections.unmodifiableList(cardList);
	}

	/**
	 * Get all of the cards in the set, mapped by ID.
	 * 
	 * @return A map of ID to card.
	 */
	public Map<Integer, C> getAllById() {
		return Collections.unmodifiableMap(cardsById);
	}
}
