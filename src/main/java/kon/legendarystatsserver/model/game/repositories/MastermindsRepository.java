package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Mastermind;

@RepositoryRestResource
public interface MastermindsRepository extends CardSetRepository<Mastermind, Integer> {

}
