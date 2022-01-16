package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Henchman;

/**
 * Repository to get {@link Henchman}
 */
@RepositoryRestResource
public interface HenchmenRepository extends CardSetRepository<Henchman, Integer> {
	//No additional behavior beyond CardSetRepository
}
