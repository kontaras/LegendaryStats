package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Set;

@RepositoryRestResource
public interface HeroesRepository extends ReadOnlyRepository<Hero, Integer> {
	Iterable<Hero> findAllBySet(Set set);
}
