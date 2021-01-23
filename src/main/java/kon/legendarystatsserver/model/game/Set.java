package kon.legendarystatsserver.model.game;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
	
	@OneToMany(mappedBy = "set_id")
	private List<Hero> heroes;
	
	@OneToMany(mappedBy = "set_id")
	private List<Mastermind> masterminds;
	
	@OneToMany(mappedBy = "set_id")
	private List<Villain> villains;
	
	@OneToMany(mappedBy = "set_id")
	private List<Henchman> henchmen;

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
	 * @return the heroes
	 */
	public List<Hero> getHeroes() {
		return heroes;
	}
	
	/**
	 * @return the villains
	 */
	public List<Villain> getVillains() {
		return villains;
	}

	/**
	 * @return the masterminds
	 */
	public List<Mastermind> getMasterminds() {
		return masterminds;
	}

	/**
	 * @return the henchmen
	 */
	public List<Henchman> getHenchmen() {
		return henchmen;
	}

}
