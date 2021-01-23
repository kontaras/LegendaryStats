/**
 * 
 */
package kon.legendarystatsserver.api;

import java.util.Iterator;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.game.Henchman;
import kon.legendarystatsserver.model.game.Set;
import kon.legendarystatsserver.model.game.repositories.HenchmenRepository;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.SetsRepository;

/**
 * @author knaryshkin
 *
 */
@RestController
public class DataController {
	@Autowired
	HenchmenRepository henches;
	
	@Autowired
	HeroesRepository heroes;
	
	@Autowired
	SetsRepository sets;

	@GetMapping("/api/getSets")
	public Iterable<Set> getSets() {
		return sets.findAll();
	}

}
