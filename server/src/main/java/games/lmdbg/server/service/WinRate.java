package games.lmdbg.server.service;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.tinylog.Logger;

import games.lmdbg.rules.model.CardSet;
import games.lmdbg.server.model.game.repositories.IWinRate;

/**
 * Calculate the win rate for a card type
 * 
 * @param <C> The {@link CardSet} for which to calculate the win rate
 */
public class WinRate<C extends CardSet> {
	/**  A factory to pull win data from */
	private Supplier<List<IWinRate>> repo;
	
	/**The cache to use to look up cards */
	private Map<Integer, C> cache;

	/**
	 * @param winSource  A factory to pull win data from
	 * @param cache The cache to use to look up cards
	 */
	public WinRate(Supplier<List<IWinRate>> winSource, Map<Integer, C> cache) {
		this.repo = winSource;
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
		List<IWinRate> winRates = this.repo.get();
		
		winRates.sort(Comparator
				.comparingDouble(
						(IWinRate rate) -> rate.getWon().doubleValue() / rate.getPlayed().intValue())
				.reversed());
		
		Logger.info("Finished getting win rates, took {}ms", () -> (Long.valueOf(System.currentTimeMillis() - start)));

		for (IWinRate winRate : winRates) {
			C hero = this.cache.get(winRate.getId());
			retVal.put(hero, winRate);
		}

		return retVal;
	}

}
