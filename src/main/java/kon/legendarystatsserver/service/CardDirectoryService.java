package kon.legendarystatsserver.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tinylog.Logger;

import kon.legendarystatsserver.model.game.Henchman;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Mastermind;
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.HenchmenRepository;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.MastermindsRepository;
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
	
	private final Map<Integer, Mastermind> mastermindsById;
	
	private final Map<Integer, Henchman> henchmenById;
	
	@Autowired
	private HeroesRepository heroes;
	
	@Autowired
	private VillainsRepository villains;
	
	@Autowired
	private MastermindsRepository masterminds;
	
	@Autowired
	private HenchmenRepository henchmen;

	private CardDirectoryService() {
		heroesById = new HashMap<>();
		villainsById = new HashMap<>();
		mastermindsById = new HashMap<>();
		henchmenById = new HashMap<>();
	}
	
	@PostConstruct
	private void init() {
		Logger.info("Starting to preload heroes");
		for (Hero hero : heroes.findAll()) {
			heroesById.put(hero.getId(), hero);
		}
		Logger.info("Finished preloading heroes");
		
		Logger.info("Starting to preload villains");
		for (Villain villain : villains.findAll()) {
			villainsById.put(villain.getId(), villain);
		}
		Logger.info("Finished preloading villains");
		
		Logger.info("Starting to preload masterminds");
		for (Mastermind mastermind : masterminds.findAll()) {
			mastermindsById.put(mastermind.getId(), mastermind);
		}
		Logger.info("Finished preloading masterminds");
		
		Logger.info("Starting to preload henchmen");
		for (Henchman henchman : henchmen.findAll()) {
			henchmenById.put(henchman.getId(), henchman);
		}
		Logger.info("Finished preloading henchmen");
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
	
	/**
	 * Get a mastermind by id. Similar to {@link MastermindsRepository#findById(Integer)},
	 * though it does not access the database for every call.
	 * 
	 * @param id The id of the mastermind to find.
	 * @return The cached Mastermind, or null if the id is not present
	 */
	public Mastermind getMastermindById(Integer id) {
		return mastermindsById.get(id);
	}
	
	/**
	 * Get a henchman by id. Similar to {@link HenchmenRepository#findById(Integer)},
	 * though it does not access the database for every call.
	 * 
	 * @param id The id of the henchman to find.
	 * @return The cached Henchman, or null if the id is not present
	 */
	public Henchman getHenchmanById(Integer id) {
		return henchmenById.get(id);
	}

}
