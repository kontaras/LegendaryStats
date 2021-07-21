package kon.legendarystatsserver.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.tinylog.Logger;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;

/**
 * Controller to get the win rates of each card set.
 */
@Controller
public class WinRateService {
	
	/**
	 * Repository that can get us hero win rates
	 */
	@Autowired
	private HeroesRepository heroes;
	
	/**
	 * Directory that we use to dereference IDs to actual objects
	 */
	@Autowired
	private CardService directory;

	/**
	 * Get all qualifying heroes with their {@link IWinRate}.
	 * 
	 * @return The list of heroes, ordered by the win percentage of each hero.
	 */
	public Map<Hero, IWinRate> getHeroWinRates() {
		Logger.info("Starting to get hero win rates");
		long start = System.currentTimeMillis();
		List<IWinRate> winRates = heroes.findHeroWinRates();
		Logger.info("Finished getting win rates, took {}ms", ()-> (System.currentTimeMillis() - start));
		Map<Hero, IWinRate> winRateMap = new LinkedHashMap<>();
		for (IWinRate winRate : winRates) {
			Hero hero = directory.getHeroById(winRate.getId());
			winRateMap.put(hero, winRate);
		}
		return winRateMap;
	}
}
