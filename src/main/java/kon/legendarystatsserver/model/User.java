package kon.legendarystatsserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * User info kept in the database
 */
@Entity
public class User {
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
		return id;
	}
	
	/**
	 * @return Get the {@link #userName}
	 */
	public String getUserName() {
		return userName;
	}
}
