package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Henchman;

@RepositoryRestResource
public interface HenchmenRepository extends CardSetRepository<Henchman, Integer> {

}
