package kon.legendarystatsserver.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.IWinRate;

@Controller
public class WinRateController {
	@Autowired
	HeroesRepository heroes;

	public Map<Hero, IWinRate> getHeroWinRates() {
		List<IWinRate> winRates = heroes.findHeroWinRates();
		Map<Hero, IWinRate> winRateMap = new LinkedHashMap<Hero, IWinRate>(winRates.stream()
				.collect(Collectors.toMap(a -> heroes.findById(a.getId()).get(), Function.identity())));
		return winRateMap;
	}
}
