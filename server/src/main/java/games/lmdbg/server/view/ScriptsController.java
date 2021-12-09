package games.lmdbg.server.view;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@GetMapping("/autoscripts/cardLookup")
	public String getVillains(Model model) {
		model.addAttribute("villainsMap",  villains.getAllById());
		model.addAttribute("henchmenMap",  henchmen.getAllById());
		return "scripts/cardSet";
	}
}
