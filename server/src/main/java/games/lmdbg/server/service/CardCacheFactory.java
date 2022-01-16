package games.lmdbg.server.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import games.lmdbg.server.model.game.Board;
import games.lmdbg.server.model.game.CardSet;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Starter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.BoardRepository;
import games.lmdbg.server.model.game.repositories.HenchmenRepository;
import games.lmdbg.server.model.game.repositories.HeroesRepository;
import games.lmdbg.server.model.game.repositories.MastermindsRepository;
import games.lmdbg.server.model.game.repositories.SchemesRepository;
import games.lmdbg.server.model.game.repositories.StartersRepository;
import games.lmdbg.server.model.game.repositories.SupportsRepository;
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
	 * @param heroes Repository to cache
	 * @return A cache of hero sets
	 */
	@Bean
	public static CardCache<Hero> getHeroCache(HeroesRepository heroes) {
		return new CardCache<>(heroes);
	}

	/**
	 * Cache {@link VillainsRepository}
	 * 
	 * @param villains Repository to cache
	 * @return A cache of villain sets
	 */
	@Bean
	public static CardCache<Villain> getVillainCache(VillainsRepository villains) {
		return new CardCache<>(villains);
	}

	/**
	 * Cache {@link MastermindsRepository}
	 * 
	 * @param masterminds Repository to cache
	 * @return A cache of mastermind sets
	 */
	@Bean
	public static CardCache<Mastermind> getMastermindCache(MastermindsRepository masterminds) {
		return new CardCache<>(masterminds);
	}

	/**
	 * Cache {@link HenchmenRepository}
	 * 
	 * @param henchmen Repository to cache
	 * @return A cache of henchman sets
	 */
	@Bean
	public static CardCache<Henchman> getHenchmanCache(HenchmenRepository henchmen) {
		return new CardCache<>(henchmen);
	}

	/**
	 * Cache {@link SchemesRepository}
	 * 
	 * @param schemes Repository to cache
	 * @return A cache of scheme sets
	 */
	@Bean
	public static CardCache<Scheme> getSchemeCache(SchemesRepository schemes) {
		return new CardCache<>(schemes);
	}
	
	/**
	 * Cache {@link Support}
	 * 
	 * @param supports Repository to cache
	 * @return A cache of scheme sets
	 */
	@Bean
	public static CardCache<Support> getSupportCache(SupportsRepository supports) {
		return new CardCache<>(supports);
	}
	
	/**
	 * Cache {@link Starter}
	 * 
	 * @param starters Repository to cache
	 * @return A cache of scheme sets
	 */
	@Bean
	public static CardCache<Starter> getStarterCache(StartersRepository starters) {
		return new CardCache<>(starters);
	}
	
	/**
	 * Cache {@link Board}
	 * 
	 * @param starters Repository to cache
	 * @return A cache of scheme sets
	 */
	@Bean
	public static CardCache<Board> getBoardCache(BoardRepository board) {
		return new CardCache<>(board);
	}
}
