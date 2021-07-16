package kon.legendarystatsserver.model;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlaysRepository extends CrudRepository<Play, Long> {

}