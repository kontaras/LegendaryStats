/**
 * 
 */
package kon.legendarystatsserver.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.SetsRepository;
import kon.legendarystatsserver.model.game.repositories.VillainsRepository;

/**
 * @author knaryshkin
 *
 */
@RestController
public class DataController {
	@Autowired
	VillainsRepository heroes;
	
	@Autowired
	SetsRepository sets;

	@GetMapping("/api/getSets")
	public Iterable<Villain> getSets() {
		return heroes.findAllBySet(sets.findById(Integer.valueOf(1)).get());
	}

}
