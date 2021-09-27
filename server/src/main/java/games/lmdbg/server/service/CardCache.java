package games.lmdbg.server.service;

import java.util.HashMap;
import java.util.Map;

import org.tinylog.Logger;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;


public class CardCache <C extends CardSet> {
	private final Map<Integer, C> cardsById;
	
	public CardCache(CardSetRepository<C, Integer> repo) {
		cardsById = new HashMap<>();
		Logger.info("Starting to preload " + repo.getClass());
		for (C hero : repo.findAll()) {
			cardsById.put(hero.getId(), hero);
		}
		Logger.info("Finished preloading "  + repo.getClass());
	}
	
	public C getById(Integer id) {
		return cardsById.get(id);
	}
}
