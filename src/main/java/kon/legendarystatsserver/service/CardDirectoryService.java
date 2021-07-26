package kon.legendarystatsserver.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.VillainsRepository;

/**
 * A static source for looking up cards. Since any change in cards will only
 * occur as part of a deploy, we can cache the game contents to speed up the
 * program and reduce DB load.
 */
@Service
public class CardDirectoryService {
	private final Map<Integer, Hero> heroesById;
	
	private final Map<Integer, Villain> villainsById;
	
	@Autowired
	private HeroesRepository heroes;
	
	@Autowired
	private VillainsRepository villains;

	@Autowired
	private CardDirectoryService() {
		heroesById = new HashMap<>();
		villainsById = new HashMap<>();
	}
	
	@PostConstruct
	private void init() {
		Logger.info("Starting to preload heroes");
		
		for (Hero hero : heroes.findAll()) {
			heroesById.put(hero.getId(), hero);
		}
		Logger.info("Finished preloading heroes");
		
		Logger.info("Starting to preload villains");
		
		for (Villain hero : villains.findAll()) {
			villainsById.put(hero.getId(), hero);
		}
		Logger.info("Finished preloading villains");
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
	
	/**
	 * Get a villain by id. Similar to {@link VillainsRepository#findById(Integer)},
	 * though it does not access the database for every call.
	 * 
	 * @param id The id of the villain to find.
	 * @return The cached Villain, or null if the id is not present
	 */
	public Villain getVillainById(Integer id) {
		return villainsById.get(id);
	}

}
