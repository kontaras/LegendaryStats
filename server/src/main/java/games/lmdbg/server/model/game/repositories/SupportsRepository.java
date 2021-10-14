package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Support;

/**
 * Repository to get {@link Support}
 */
@RepositoryRestResource
public interface SupportsRepository extends CardSetRepository<Support, Integer> {

}
