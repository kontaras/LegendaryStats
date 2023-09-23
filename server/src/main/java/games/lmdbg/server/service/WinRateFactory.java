package games.lmdbg.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import games.lmdbg.rules.model.Henchman;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Mastermind;
import games.lmdbg.rules.model.Scheme;
import games.lmdbg.rules.model.Villain;
import games.lmdbg.rules.set.CardLookupKt;
import games.lmdbg.server.model.PlaysRepository;

/** {@link Configuration}to set up {@link WinRate}s */
@Configuration
public class WinRateFactory {
	@Autowired
	PlaysRepository plays;
	
	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Hero}
	 */
	@Bean
	public WinRate<Hero> getHeroWinRates() {
		return new WinRate<>(this.plays::findHeroWinRates, CardLookupKt.getHeroesById());
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Villain}
	 */
	@Bean
	public WinRate<Villain> getVillainWinRates() {
		return new WinRate<>(this.plays::findVillainWinRates, CardLookupKt.getVillainsById());
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Mastermind}
	 */
	@Bean
	public WinRate<Mastermind> getMastermindWinRates() {
		return new WinRate<>(this.plays::findMastermindWinRates, CardLookupKt.getMastermindsById());
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Henchman}
	 */
	@Bean
	public WinRate<Henchman> getHenchmanWinRates() {
		return new WinRate<>(this.plays::findHenchmenWinRates, CardLookupKt.getHenchmanById());
	}

	/**
	 * <em>DON'T MANUALLY CREATE INSTANCES, HAVE SPRING INJECT THE INSTANCE!</em>
	 * 
	 * @return Get the {@link WinRate} instance for {@link Scheme}
	 */
	@Bean
	public WinRate<Scheme> getSchemeWinRates() {
		return new WinRate<>(this.plays::findSchemeWinRates, CardLookupKt.getSchemesById());
	}
}
