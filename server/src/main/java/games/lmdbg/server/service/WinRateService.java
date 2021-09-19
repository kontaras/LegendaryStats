package games.lmdbg.server.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import com.google.common.annotations.VisibleForTesting;

import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.CardSetRepository;
import games.lmdbg.server.model.game.repositories.HenchmenRepository;
import games.lmdbg.server.model.game.repositories.HeroesRepository;
import games.lmdbg.server.model.game.repositories.IWinRate;
import games.lmdbg.server.model.game.repositories.MastermindsRepository;
import games.lmdbg.server.model.game.repositories.SchemesRepository;
import games.lmdbg.server.model.game.repositories.VillainsRepository;

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
	 * Repository that can get us mastermind win rates
	 */
	@Autowired
	private MastermindsRepository masterminds;
	
	/**
	 * Repository that can get us henchman win rates
	 */
	@Autowired
	private HenchmenRepository henchmen;
	
	/**
	 * Repository that can get us scheme win rates
	 */
	@Autowired
	private SchemesRepository schemes;

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
		Logger.info("Starting to get {} win rates");
		long start = System.currentTimeMillis();
		Map<Hero, IWinRate> winRates = getCardSetWinRates(heroes, directory::getHeroById);
		Logger.info("Finished getting hero win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}

	/**
	 * Get all qualifying villains with their {@link IWinRate}.
	 * 
	 * @return The list of villains, ordered by the win percentage of each villain.
	 */
	public Map<Villain, IWinRate> getVillainWinRates() {
		Logger.info("Starting to get villain win rates");
		long start = System.currentTimeMillis();
		Map<Villain, IWinRate> winRates = getCardSetWinRates(villains, directory::getVillainById);
		Logger.info("Finished getting villain win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}
	
	/**
	 * Get all qualifying masterminds with their {@link IWinRate}.
	 * 
	 * @return The list of masterminds, ordered by the win percentage of each mastermind.
	 */
	public Map<Mastermind, IWinRate> getMastermindWinRates() {
		Logger.info("Starting to get mastermind win rates");
		long start = System.currentTimeMillis();
		Map<Mastermind, IWinRate> winRates = getCardSetWinRates(masterminds, directory::getMastermindById);
		Logger.info("Finished getting mastermind win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}
	
	/**
	 * Get all qualifying henchmen with their {@link IWinRate}.
	 * 
	 * @return The list of henchmen, ordered by the win percentage of each henchman.
	 */
	public Map<Henchman, IWinRate> getHenchmanWinRates() {
		Logger.info("Starting to get henchman win rates");
		long start = System.currentTimeMillis();
		Map<Henchman, IWinRate> winRates = getCardSetWinRates(henchmen, directory::getHenchmanById);
		Logger.info("Finished getting henchman win rates, took {}ms", () -> (System.currentTimeMillis() - start));
		return winRates;
	}
	
	/**
	 * Get all qualifying schemes with their {@link IWinRate}.
	 * 
	 * @return The list of schemes, ordered by the win percentage of each scheme.
	 */
	public Map<Scheme, IWinRate> getSchemeWinRates() {
		Logger.info("Starting to get scheme win rates");
		long start = System.currentTimeMillis();
		Map<Scheme, IWinRate> winRates = getCardSetWinRates(schemes, directory::getSchemeById);
		Logger.info("Finished getting scheme win rates, took {}ms", () -> (System.currentTimeMillis() - start));
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

}
