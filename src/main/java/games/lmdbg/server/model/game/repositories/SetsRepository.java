package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.GameRelease;

/**
 * Repository to get Sets
 */
@RepositoryRestResource
public interface SetsRepository extends ReadOnlyRepository<GameRelease, Integer> {
}
