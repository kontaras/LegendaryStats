package games.lmdbg.server.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tinylog.Logger;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;
import games.lmdbg.server.model.game.repositories.IWinRate;

public class WinRate<C extends CardSet> {
	CardSetRepository<C, Integer> repo;

	CardCache<C> cache;

	/**
	 * @param repo
	 * @param cache
	 */
	public WinRate(CardSetRepository<C, Integer> repo, CardCache<C> cache) {
		this.repo = repo;
		this.cache = cache;
	}

	public Map<C, IWinRate> getWinRates() {
		Map<C, IWinRate> retVal = new LinkedHashMap<>();

		Logger.info("Starting to get win rates");
		long start = System.currentTimeMillis();
		List<IWinRate> winRates = repo.findWinRates();
		Logger.info("Finished getting win rates, took {}ms", () -> (System.currentTimeMillis() - start));

		for (IWinRate winRate : winRates) {
			C hero = cache.getById(winRate.getId());
			retVal.put(hero, winRate);
		}

		return retVal;
	}

}
