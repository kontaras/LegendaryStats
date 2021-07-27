package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Hero;

@RepositoryRestResource
public interface HeroesRepository extends CardSetRepository<Hero, Integer> {

}
