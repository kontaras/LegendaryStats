/**
 * 
 */
package kon.legendarystatsserver.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.Play;
import kon.legendarystatsserver.model.PlaysRepository;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.GameSet;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;
import kon.legendarystatsserver.model.game.repositories.SetsRepository;

/**
 * @author knaryshkin
 *
 */
@RestController
public class DataController {
	@Autowired
	SetsRepository sets;
	
	@Autowired
	PlaysRepository plays;
	
	@Autowired
	HeroesRepository heroes;

	@GetMapping("/api/set")
	public Iterable<GameSet> getSets() {
		return sets.findAll();
	}
	
	@GetMapping("/api/play")
	public Iterable<Play> getPlays() {
		return plays.findAll();
	}
	
	@GetMapping("/api/hero")
	public Iterable<Hero> getHeroes() {
		return heroes.findAll();
	}
	
	@GetMapping("/api/hero/rate")
	public List<IWinRate> getHeroWinRate() {
		return heroes.findHeroWinRates();
	}

}
