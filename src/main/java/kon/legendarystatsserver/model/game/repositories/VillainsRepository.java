package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Set;
import kon.legendarystatsserver.model.game.Villain;

@RepositoryRestResource
public interface VillainsRepository extends ReadOnlyRepository<Villain, Integer> {
	Iterable<Villain> findAllBySet(Set set);
}
