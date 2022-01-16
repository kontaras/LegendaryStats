package games.lmdbg.server.service;

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

/** {@link Configuration}to set up {@link WinRate}s */
@Configuration
public class WinRateFactory {
	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Hero}
	 */
	@Bean
	public static WinRate<Hero> getHeroWinRates(HeroesRepository heroes, CardCache<Hero> heroCache) {
		return new WinRate<>(heroes, heroCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Villain}
	 */
	@Bean
	public static WinRate<Villain> getVillainWinRates(VillainsRepository villains, CardCache<Villain> villainCache) {
		return new WinRate<>(villains, villainCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Mastermind}
	 */
	@Bean
	public static WinRate<Mastermind> getMastermindWinRates(MastermindsRepository masterminds, CardCache<Mastermind> mastermindCache) {
		return new WinRate<>(masterminds, mastermindCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Henchman}
	 */
	@Bean
	public static WinRate<Henchman> getHenchmanWinRates(HenchmenRepository henchmen, CardCache<Henchman> henchmanCache) {
		return new WinRate<>(henchmen, henchmanCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Scheme}
	 */
	@Bean
	public static WinRate<Scheme> getSchemeWinRates(SchemesRepository schemes, CardCache<Scheme> schemeCache) {
		return new WinRate<>(schemes, schemeCache);
	}
}
