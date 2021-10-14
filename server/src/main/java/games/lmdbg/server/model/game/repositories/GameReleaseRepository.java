package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.GameRelease;

/**
 * Repository to get GameReleases
 */
@RepositoryRestResource
public interface GameReleaseRepository extends ReadOnlyRepository<GameRelease, Integer> {
}
