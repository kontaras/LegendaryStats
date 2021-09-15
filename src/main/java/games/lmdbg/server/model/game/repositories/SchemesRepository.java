package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Scheme;

@RepositoryRestResource
public interface SchemesRepository extends CardSetRepository<Scheme, Integer> {

}
