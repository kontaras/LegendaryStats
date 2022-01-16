package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Scheme;

/**
 * Repository to get {@link Scheme}
 */
@RepositoryRestResource
public interface SchemesRepository extends CardSetRepository<Scheme, Integer> {
	//No additional behavior beyond CardSetRepository
}
