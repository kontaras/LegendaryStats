package games.lmdbg.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.HenchmenRepository;
import games.lmdbg.server.model.game.repositories.HeroesRepository;
import games.lmdbg.server.model.game.repositories.MastermindsRepository;
import games.lmdbg.server.model.game.repositories.SchemesRepository;
import games.lmdbg.server.model.game.repositories.VillainsRepository;

@Configuration
public class WinRateFactory {
	@Autowired
	private HeroesRepository heroes;
	
	@Autowired
	private VillainsRepository villains;
	
	@Autowired
	private MastermindsRepository masterminds;
	
	@Autowired
	private HenchmenRepository henchmen;
	
	@Autowired
	private SchemesRepository schemes;
	
	@Autowired
	CardCache<Hero>  heroCache;
	
	@Autowired
	CardCache<Villain>  villainCache;
	
	@Autowired
	CardCache<Mastermind>  mastermindCache;
	
	@Autowired
	CardCache<Henchman>  henchmanCache;
	
	@Autowired
	CardCache<Scheme>  schemeCache;
	
	@Bean
	public WinRate<Hero> getHeroWinRates() {
		return new WinRate<>(heroes, heroCache);
	}
	
	@Bean
	public WinRate<Villain> getVillainWinRates() {
		return new WinRate<>(villains, villainCache);
	}
	
	@Bean
	public WinRate<Mastermind> getMastermindWinRates() {
		return new WinRate<>(masterminds, mastermindCache);
	}
	
	@Bean
	public WinRate<Henchman> getHenchmanWinRates() {
		return new WinRate<>(henchmen, henchmanCache);
	}
	
	@Bean
	public WinRate<Scheme> getSchemeWinRates() {
		return new WinRate<>(schemes, schemeCache);
	}
}
