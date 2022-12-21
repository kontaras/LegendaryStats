package games.lmdbg.server.model;

import org.springframework.data.repository.CrudRepository;

public interface AccountsRepository extends CrudRepository<Account, Integer> {
	
	boolean existsByUserName(String userName);

}
