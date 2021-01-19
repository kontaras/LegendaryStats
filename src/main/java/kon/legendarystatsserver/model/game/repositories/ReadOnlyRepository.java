package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends CrudRepository<T, ID> {

	<S extends T> S save(S entity);

	<S extends T> Iterable<S> saveAll(Iterable<S> entities);

	void deleteById(Integer id);

	void delete(T entity);

	void deleteAll(Iterable<? extends T> entities);

	void deleteAll();

}