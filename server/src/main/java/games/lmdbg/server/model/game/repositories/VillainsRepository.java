package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Villain;

/**
 * Repository to get {@link Villain}
 */
@RepositoryRestResource
public interface VillainsRepository extends CardSetRepository<Villain, Integer> {
	//No additional behavior beyond CardSetRepository
}
