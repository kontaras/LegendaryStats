package kon.legendarystatsserver.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;

/**
 * A static source for looking up cards. Since any change in cards will only
 * occur as part of a deploy, we can cache the game contents to speed up the
 * program and reduce DB load.
 */
@Service
public class CardService {
	private final Map<Integer, Hero> heroesById;

	@Autowired
	private CardService(HeroesRepository heroes) {
		Logger.info("Starting to preload heroes");
		heroesById = new HashMap<>();
		for (Hero hero : heroes.findAll()) {
			heroesById.put(hero.getId(), hero);
		}
		Logger.info("Finished preloadin heroes");
	}

	/**
	 * Get a hero by id. Similar to {@link HeroesRepository#findById(Integer)},
	 * though it does not access the database for every call.
	 * 
	 * @param id The id of the hero to find.
	 * @return The cached Hero, or null if the id is not present
	 */
	public Hero getHeroById(Integer id) {
		return heroesById.get(id);
	}

}
