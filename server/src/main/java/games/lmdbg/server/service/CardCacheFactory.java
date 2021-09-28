package games.lmdbg.server.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import games.lmdbg.server.model.game.CardSet;
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

/**
 * Create {@link CardCache} for all of the {@link CardSet} types.
 * 
 * <em>DON'T MANUALLY CALL METHODS FROM THIS CLASS, USE SPRING TO AUTOWIRE THEM
 * FOR YOU</em>
 */
@Configuration
public class CardCacheFactory {
	/**
	 * Cache {@link HeroesRepository}
	 * 
	 * @return A cache of hero sets
	 */
	@Bean
	public CardCache<Hero> getHeroCache(HeroesRepository heroes) {
		return new CardCache<>(heroes);
	}

	/**
	 * Cache {@link VillainsRepository}
	 * 
	 * @return A cache of villain sets
	 */
	@Bean
	public CardCache<Villain> getVillainCache(VillainsRepository villains) {
		return new CardCache<>(villains);
	}

	/**
	 * Cache {@link MastermindsRepository}
	 * 
	 * @return A cache of mastermind sets
	 */
	@Bean
	public CardCache<Mastermind> getMastermindCache(MastermindsRepository masterminds) {
		return new CardCache<>(masterminds);
	}

	/**
	 * Cache {@link HenchmenRepository}
	 * 
	 * @return A cache of henchman sets
	 */
	@Bean
	public CardCache<Henchman> getHenchmanCache(HenchmenRepository henchmen) {
		return new CardCache<>(henchmen);
	}

	/**
	 * Cache {@link SchemesRepository}
	 * 
	 * @return A cache of scheme sets
	 */
	@Bean
	public CardCache<Scheme> getSchemeCache(SchemesRepository schemes) {
		return new CardCache<>(schemes);
	}
}
