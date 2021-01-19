package kon.legendarystatsserver.model.game;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface VillainsRepository extends ReadOnlyRepository<Villain, Integer> {
	Iterable<Villain> findAllBySet(Set set);
}
