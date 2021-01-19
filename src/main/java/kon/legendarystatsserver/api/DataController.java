/**
 * 
 */
package kon.legendarystatsserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.game.Set;
import kon.legendarystatsserver.model.game.SetsRepository;

/**
 * @author knaryshkin
 *
 */
@RestController
public class DataController {
	@Autowired
	SetsRepository sets;

	@GetMapping("/api/getSets")
	public Iterable<Set> getSets() {
		return sets.findAll();
	}

}
