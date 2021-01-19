/**
 * 
 */
package kon.legendarystatsserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.HeroesRepository;
import kon.legendarystatsserver.model.game.Set;
import kon.legendarystatsserver.model.game.SetsRepository;

/**
 * @author knaryshkin
 *
 */
@RestController
public class DataController {
	@Autowired
	HeroesRepository heroes;
	
	@Autowired
	SetsRepository sets;

	@GetMapping("/api/getSets")
	public Iterable<Hero> getSets() {
		return heroes.findAllBySet(sets.findById(Integer.valueOf(1)).get());
	}

}
