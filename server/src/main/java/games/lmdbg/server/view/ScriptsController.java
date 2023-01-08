package games.lmdbg.server.view;

import javax.servlet.http.HttpServletResponse;

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
	@GetMapping(value = "/autoscripts/cardLookup",
			produces = "text/javascript")
	public String cardDeref(Model model, HttpServletResponse response) {
		model.addAttribute("villainsMap",  villains.getAllById());
		model.addAttribute("henchmenMap",  henchmen.getAllById());
		
		response.setContentType("text/javascript"); //TODO: This does not actually seem to work. Fix the content-type
		return "scripts/cardSet";
	}
}
