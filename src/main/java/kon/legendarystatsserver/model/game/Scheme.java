package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A single scheme in the game
 */
@Entity
public class Scheme implements INamable {
	/** Database ID */
	@Id
	private Integer id;
	
	/** The name of the set for Marvel comics sets */
	private String marvelName;
	
	/** The name of the set for Marvel movie stills sets */
	private String mcuName;
	
	/** The name of the set for Legendary DXP sets */
	private String dxpName;
	
	/** Foreign key for the {@link GameSet} this card set belongs to. */
	private Integer set_id;

	/**
	 * @return the {@link #id}
	 */
	public Integer getId() {
		return id;
	}

	@Override
	public String getMarvelName() {
		return marvelName;
	}

	@Override
	public String getMcuName() {
		return mcuName;
	}

	@Override
	public String getDxpName() {
		return dxpName;
	}

	/**
	 * @return the {@link #set_id}
	 */
	public Integer getSet_id() {
		return set_id;
	}
}
