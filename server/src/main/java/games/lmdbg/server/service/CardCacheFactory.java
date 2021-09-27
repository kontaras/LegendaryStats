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
public class CardCacheFactory {
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
	
	@Bean
	public  CardCache<Hero>  getHeroCache() {
		 return new CardCache<Hero>(heroes);
	}
	
	@Bean
	public  CardCache<Villain>  getVillainCache() {
		 return new CardCache<Villain>(villains);
	}

	@Bean
	public  CardCache<Mastermind>  getMastermindCache() {
		 return new CardCache<Mastermind>(masterminds);
	}
	
	@Bean
	public  CardCache<Henchman>  getHenchmanCache() {
		 return new CardCache<Henchman>(henchmen);
	}
	
	@Bean
	public  CardCache<Scheme>  getSchemeCache() {
		 return new CardCache<Scheme>(schemes);
	}
}
