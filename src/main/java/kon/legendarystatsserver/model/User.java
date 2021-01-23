package kon.legendarystatsserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
	/** Database ID */
	@Id
	private Long id;
	
	private String userName;
}
