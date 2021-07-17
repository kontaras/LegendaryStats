package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.GameRelease;

/**
 * Repository to get Sets
 */
@RepositoryRestResource
public interface SetsRepository extends ReadOnlyRepository<GameRelease, Integer> {
}
