package games.lmdbg.server.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Account info kept in the database
 */
@Entity
public class Account {
	/** Database ID */
	@Id
	private Long id;
	
	/**
	 * Username
	 */
	private String userName;

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * @return Get the {@link #userName}
	 */
	public String getUserName() {
		return this.userName;
	}
}
