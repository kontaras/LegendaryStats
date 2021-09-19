package games.lmdbg.server.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import games.lmdbg.server.model.game.Hero;

@RepositoryRestResource
public interface HeroesRepository extends CardSetRepository<Hero, Integer> {

}
