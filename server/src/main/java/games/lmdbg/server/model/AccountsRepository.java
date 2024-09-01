package games.lmdbg.server.model;

import org.springframework.data.repository.CrudRepository;

/**
 * Account info in the Database
 */
public interface AccountsRepository extends CrudRepository<Account, Integer> {
	/**
	 * Check if a user already exists
	 * 
	 * @param userName Name to check
	 * @return if the user exists
	 */
	boolean existsByUserName(String userName);

	/**
	 * Find the account with a given user name
	 * 
	 * @param userName name to look up
	 * @return The account object or null if not found
	 */
	Account findByUserName(String userName);
}
