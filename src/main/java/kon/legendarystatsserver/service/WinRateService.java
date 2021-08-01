package kon.legendarystatsserver.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import com.google.common.annotations.VisibleForTesting;

import kon.legendarystatsserver.model.game.CardSet;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.CardSetRepository;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;
import kon.legendarystatsserver.model.game.repositories.VillainsRepository;

/**
 * Controller to get the win rates of each card set.
 */
@Service
public class WinRateService {

	/**
	 * Repository that can get us hero win rates
	 */
	@Autowired
	private HeroesRepository heroes;

	/**
	 * Repository that can get us villain win rates
	 */
	@Autowired
	private VillainsRepository villains;
	
	/**
	 * Directory that we use to dereference IDs to actual objects
	 */
	@Autowired
	private CardDirectoryService directory;

	/**
	 * Get all qualifying heroes with their {@link IWinRate}.
	 * 
	 * @return The list of heroes, ordered by the win percentage of each hero.
	 */
	public Map<Hero, IWinRate> getHeroWinRates() {
		Logger.info("Starting to get hero win rates");
		long start = System.currentTimeMillis();
		Map<Hero, IWinRate> winRates = getCardSetWinRates(heroes, directory::getHeroById);
		Logger.info("Finished getting win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}

	@VisibleForTesting
	<C extends CardSet> Map<C, IWinRate> getCardSetWinRates(CardSetRepository<C, Integer> repo,
			IntFunction<C> lookupCache) {
		Map<C, IWinRate> retVal = new LinkedHashMap<>();

		List<IWinRate> winRates = repo.findWinRates();
		for (IWinRate winRate : winRates) {
			C hero = lookupCache.apply(winRate.getId());
			retVal.put(hero, winRate);
		}
		return retVal;
	}

	/**
	 * Get all qualifying villains with their {@link IWinRate}.
	 * 
	 * @return The list of villains, ordered by the win percentage of each hero.
	 */
	public Map<Villain, IWinRate> getVillainWinRates() {
		Logger.info("Starting to get villain win rates");
		long start = System.currentTimeMillis();
		Map<Villain, IWinRate> winRates = getCardSetWinRates(villains, directory::getVillainById);
		Logger.info("Finished getting win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}
}
