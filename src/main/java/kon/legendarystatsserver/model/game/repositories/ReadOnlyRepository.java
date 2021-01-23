package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends CrudRepository<T, ID> {

	default <S extends T> S save(S entity){
		throw new UnsupportedOperationException();
	}

	default <S extends T> Iterable<S> saveAll(Iterable<S> entities){
		throw new UnsupportedOperationException();
	}

	default void deleteById(Integer id){
		throw new UnsupportedOperationException();
	}

	default void delete(T entity){
		throw new UnsupportedOperationException();
	}

	default void deleteAll(Iterable<? extends T> entities){
		throw new UnsupportedOperationException();
	}

	default void deleteAll() {
		throw new UnsupportedOperationException();
	}

}