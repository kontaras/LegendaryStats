package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Starter;

/**
 * Repository for {@link Starter}
 */
@RepositoryRestResource
public interface StartersRepository extends ReadOnlyRepository<Starter, Integer> {
	//No additional behavior beyond ReadOnlyRepository
}
