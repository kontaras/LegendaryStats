package kon.legendarystatsserver.model.game.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

/**
 * A version of {@link CrudRepository} with all modification methods disabled
 * 
 * @param <T> See {@link Repository}
 * @param <ID> See {@link Repository}
 */
@NoRepositoryBean
public interface ReadOnlyRepository<T, ID> extends CrudRepository<T, ID> {

	@Override
	default <S extends T> S save(S entity){
		throw new UnsupportedOperationException();
	}

	@Override
	default <S extends T> Iterable<S> saveAll(Iterable<S> entities){
		throw new UnsupportedOperationException();
	}

	@Override
	default void deleteById(ID id){
		throw new UnsupportedOperationException();
	}

	@Override
	default void delete(T entity){
		throw new UnsupportedOperationException();
	}

	@Override
	default void deleteAll(Iterable<? extends T> entities){
		throw new UnsupportedOperationException();
	}

	@Override
	default void deleteAll() {
		throw new UnsupportedOperationException();
	}

}