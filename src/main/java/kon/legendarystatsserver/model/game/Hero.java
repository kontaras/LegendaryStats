package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Hero {
	/** Database ID */
	@Id
	private Integer id;

	/** The name of the set for Marvel comics sets */
	private String marvelName;
	
	/** The name of the set for Marvel movie stills sets */
	private String mcuName;
	
	/** The name of the set for Legendary DXP sets */
	private String dxpName;

	@ManyToOne(fetch = FetchType.LAZY)
	private Set set;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the marvelName
	 */
	public String getMarvelName() {
		return marvelName;
	}

	/**
	 * @return the mcuName
	 */
	public String getMcuName() {
		return mcuName;
	}

	/**
	 * @return the dxpName
	 */
	public String getDxpName() {
		return dxpName;
	}

	/**
	 * @return the set
	 */
	public Set getSet() {
		return set;
	}

}
