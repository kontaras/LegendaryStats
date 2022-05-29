package games.lmdbg.server.service;

import java.util.List;
import java.util.stream.Collectors;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.rules.verifier.VerifierKt;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.PlayStarter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;

public class RulesUtils {

	public static games.lmdbg.rules.model.Play serverToRules(games.lmdbg.server.model.Play serverPlay) {
		games.lmdbg.rules.model.Play rulesPlay = new games.lmdbg.rules.model.Play(
				Outcome.valueOf(serverPlay.getOutcome()), 
				PlayerCount.valueOf(serverPlay.getPlayers()),
				serverPlay.getScheme().getId(),
				serverPlay.getMastermind().getId(),
				serverPlay.getHeroes().parallelStream().map(Hero::getId).collect(Collectors.toSet()),
				serverPlay.getVillains().parallelStream().map(Villain::getId).collect(Collectors.toSet()),
				serverPlay.getHenchmen().parallelStream().map(Henchman::getId).collect(Collectors.toSet()),
				serverPlay.getSupports().parallelStream().map(Support::getId).collect(Collectors.toSet()),
				serverPlay.getStarters().parallelStream()
						.collect(Collectors.toMap((t) -> t.getStarter().getId(), PlayStarter::getQuantity)),
				serverPlay.getBoard().getId(),
				null);
		return rulesPlay;
	}
	
	public static List<PrintableError> verify(games.lmdbg.server.model.Play serverPlay) {
		games.lmdbg.rules.model.Play rulesPlay = serverToRules(serverPlay);
		return VerifierKt.verify(rulesPlay);
	}
}
