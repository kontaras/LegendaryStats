package games.lmdbg.server.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.service.CardCache;

/**  */
@Controller
public class ScriptsController {
	
	@Autowired
	private CardCache<Villain> villains;
	
	@Autowired
	private CardCache<Henchman> henchmen;

	/**
	 * Generate the JavaScript card set lookup script
	 */
	@GetMapping(value = "/autoscripts/cardLookup", produces = "text/javascript")
	public String cardDeref(Model model) {
		model.addAttribute("villainsMap",  villains.getAllById());
		model.addAttribute("henchmenMap",  henchmen.getAllById());
		return "scripts/cardSet";
	}
}
