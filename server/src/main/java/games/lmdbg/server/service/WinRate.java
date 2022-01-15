package games.lmdbg.server.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.tinylog.Logger;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.repositories.CardSetRepository;
import games.lmdbg.server.model.game.repositories.IWinRate;

/**
 * Calculate the win rate for a card type
 * 
 * @param <C> The {@link CardSet} for which to calculate the win rate
 */
public class WinRate<C extends CardSet> {
	/** The repository to pull win data from */
	CardSetRepository<C, Integer> repo;

	/** The cache to use to look up cards */
	CardCache<C> cache;

	/**
	 * @param repo  The repository to pull win data from
	 * @param cache The cache to use to look up cards
	 */
	public WinRate(CardSetRepository<C, Integer> repo, CardCache<C> cache) {
		this.repo = repo;
		this.cache = cache;
	}

	/**
	 * Calculate the win rates for a given card set type.
	 * 
	 * @return A map of {@link CardSet} to its {@link IWinRate}, ordered by the win
	 *         percentage.
	 */
	public Map<C, IWinRate> getWinRates() {
		Map<C, IWinRate> retVal = new LinkedHashMap<>();

		Logger.info("Starting to get win rates");
		long start = System.currentTimeMillis();
		List<IWinRate> winRates = this.repo.findWinRates();
		Logger.info("Finished getting win rates, took {}ms", () -> (Long.valueOf(System.currentTimeMillis() - start)));

		for (IWinRate winRate : winRates) {
			C hero = this.cache.getById(winRate.getId());
			retVal.put(hero, winRate);
		}

		return retVal;
	}

}
