package kon.legendarystatsserver.model.game;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * Repository to get Sets
 */
@RepositoryRestResource
public interface SetsRepository extends ReadOnlyRepository<Set, Integer> {
}
