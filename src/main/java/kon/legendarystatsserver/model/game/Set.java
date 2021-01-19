package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A set of the game.
 */
@Entity
public class Set {
	/** Database ID */
	@Id
	private Integer id;

	/** The name of the set for Marvel comics sets */
	private String marvelName;
	/** The name of the set for Marvel movie stills sets */
	private String mcuName;
	/** The name of the set for Legendary DXP sets */
	private String dxpName;

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMarvelName() {
		return this.marvelName;
	}

	public void setMarvelName(String marvelName) {
		this.marvelName = marvelName;
	}

	public String getMcuName() {
		return this.mcuName;
	}

	public void setMcuName(String mcuName) {
		this.mcuName = mcuName;
	}

	public String getDxpName() {
		return this.dxpName;
	}

	public void setDxpName(String dxpName) {
		this.dxpName = dxpName;
	}
}
