package kon.legendarystatsserver.model.game;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

/**
 * Repository to get Sets
 */
@RepositoryRestResource
public interface SetsRepository extends CrudRepository<Set,Integer>
{

  @Override
  @RestResource(exported=false)
  <S extends Set> S save(S entity);
	
	@Override
  @RestResource(exported=false)
  <S extends Set> Iterable<S> saveAll(Iterable<S> entities);

  @Override
  @RestResource(exported=false)
  void deleteById(Integer id);

  @Override
  @RestResource(exported=false)
  void delete(Set entity);

  @Override
  @RestResource(exported=false)
  void deleteAll(Iterable<? extends Set> entities);

  @Override
  @RestResource(exported=false)
  void deleteAll();
}
