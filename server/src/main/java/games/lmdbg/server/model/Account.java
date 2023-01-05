package games.lmdbg.server.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

/**
 * Account info kept in the database
 */
@Entity
public class Account {
	/** Database ID */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "account_id_generator")
	@SequenceGenerator(name = "account_id_generator", allocationSize = 1, sequenceName = "account_ids")
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;
	
	/**
	 * Username
	 */
	private String userName;
	
	/**
	 * Password hash
	 */
	private String password;
	
	/**
	 * User email, if supplied
	 */
	private String email;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return this.id;
	}
	
	/**
	 * @return Get the {@link #userName}
	 */
	public String getUserName() {
		return this.userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
}
