package games.lmdbg.server.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Account info in the Database
 */
public interface AccountsRepository extends CrudRepository<Account, Integer> {
	/**
	 * Check if a user already exists
	 * @param userName Name to check
	 * @return if the user exists
	 */
	boolean existsByUserName(String userName);
}
