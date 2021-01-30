package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Scheme;

@RepositoryRestResource
public interface SchemesRepository extends ReadOnlyRepository<Scheme, Integer> {

}
