package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Mastermind;

/**
 * Repository to get {@link Mastermind}
 */
@RepositoryRestResource
public interface MastermindsRepository extends CardSetRepository<Mastermind, Integer> {
	//No additional behavior beyond CardSetRepository
}
