package kon.legendarystatsserver.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A set of the game.
 */
@Entity
public class GameSet {
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
	private Set<Hero> heroes;
	
	@OneToMany(mappedBy = "set_id")
	private Set<Scheme> schemes;
	
	@OneToMany(mappedBy = "set_id")
	private Set<Mastermind> masterminds;
	
	@OneToMany(mappedBy = "set_id")
	private Set<Villain> villains;
	
	@OneToMany(mappedBy = "set_id")
	private Set<Henchman> henchmen;

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
	public Set<Hero> getHeroes() {
		return heroes;
	}
	
	/**
	 * @return the villains
	 */
	public Set<Villain> getVillains() {
		return villains;
	}

	/**
	 * @return the masterminds
	 */
	public Set<Mastermind> getMasterminds() {
		return masterminds;
	}

	/**
	 * @return the henchmen
	 */
	public Set<Henchman> getHenchmen() {
		return henchmen;
	}

	/**
	 * @return the schemes
	 */
	public Set<Scheme> getSchemes() {
		return schemes;
	}

}
