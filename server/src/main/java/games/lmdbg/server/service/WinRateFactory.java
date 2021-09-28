package games.lmdbg.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.Repository;

import games.lmdbg.server.model.Play;
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
	/** {@link Repository} to fetch {@link Play}s by {@link Hero} */
	@Autowired
	private HeroesRepository heroes;

	/** {@link Repository} to fetch {@link Play}s by {@link Villain} */
	@Autowired
	private VillainsRepository villains;

	/** {@link Repository} to fetch {@link Play}s by {@link Mastermind} */
	@Autowired
	private MastermindsRepository masterminds;

	/** {@link Repository} to fetch {@link Play}s by {@link Henchman} */
	@Autowired
	private HenchmenRepository henchmen;

	/** {@link Repository} to fetch {@link Play}s by {@link Scheme} */
	@Autowired
	private SchemesRepository schemes;

	/** Cache for dereferencing {@link Hero}es */
	@Autowired
	private CardCache<Hero> heroCache;

	/** Cache for dereferencing {@link Villain}s */
	@Autowired
	private CardCache<Villain> villainCache;

	/** Cache for dereferencing {@link Mastermind}s */
	@Autowired
	private CardCache<Mastermind> mastermindCache;

	/** Cache for dereferencing {@link Henchman} */
	@Autowired
	private CardCache<Henchman> henchmanCache;

	/** Cache for dereferencing {@link Scheme}s */
	@Autowired
	private CardCache<Scheme> schemeCache;

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Hero}
	 */
	@Bean
	public WinRate<Hero> getHeroWinRates() {
		return new WinRate<>(heroes, heroCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Villain}
	 */
	@Bean
	public WinRate<Villain> getVillainWinRates() {
		return new WinRate<>(villains, villainCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Mastermind}
	 */
	@Bean
	public WinRate<Mastermind> getMastermindWinRates() {
		return new WinRate<>(masterminds, mastermindCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Henchman}
	 */
	@Bean
	public WinRate<Henchman> getHenchmanWinRates() {
		return new WinRate<>(henchmen, henchmanCache);
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Scheme}
	 */
	@Bean
	public WinRate<Scheme> getSchemeWinRates() {
		return new WinRate<>(schemes, schemeCache);
	}
}
