/**
 * 
 */
package kon.legendarystatsserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.Play;
import kon.legendarystatsserver.model.PlaysRepository;
import kon.legendarystatsserver.model.game.Set;
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

	@GetMapping("/api/getSets")
	public Iterable<Set> getSets() {
		sets.deleteAll();
		return sets.findAll();
	}
	
	@GetMapping("/api/getPlays")
	public Iterable<Play> getPlays() {
		return plays.findAll();
	}

}
