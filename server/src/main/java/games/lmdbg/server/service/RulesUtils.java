package games.lmdbg.server.service;

import java.util.List;
import java.util.stream.Collectors;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.rules.verifier.VerifierKt;
import games.lmdbg.server.model.StarterPlay;

public class RulesUtils {
	
	private RulesUtils() {
		//Never instantiated
	}

	public static games.lmdbg.rules.model.Play serverToRules(games.lmdbg.server.model.Play serverPlay) {
		return new games.lmdbg.rules.model.Play(
				Outcome.valueOf(serverPlay.getOutcome()), 
				PlayerCount.valueOf(serverPlay.getPlayers()),
				serverPlay.getScheme(),
				serverPlay.getMastermind(),
				serverPlay.getHeroes(),
				serverPlay.getVillains(),
				serverPlay.getHenchmen(),
				serverPlay.getSupports(),
				serverPlay.getStarters().parallelStream()
						.collect(Collectors.toMap(t -> t.getStarter(), StarterPlay::getQuantity)),
				serverPlay.getBoard());
	}
	
	public static List<PrintableError> verify(games.lmdbg.server.model.Play serverPlay) {
		games.lmdbg.rules.model.Play rulesPlay = serverToRules(serverPlay);
		return VerifierKt.verify(rulesPlay);
	}
}
