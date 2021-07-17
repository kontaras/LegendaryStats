package kon.legendarystatsserver.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * A set of the game.
 */
@Entity
public class GameSet implements INamable {
	/** Database ID */
	@Id
	private Integer id;

	/** The name of the set for Marvel comics sets */
	private String marvelName;

	/** The name of the set for Marvel movie stills sets */
	private String mcuName;

	/** The name of the set for Legendary DXP sets */
	private String dxpName;
	
	/**
	 * The heroes in the set
	 */
	@OneToMany(mappedBy = "setId")
	private Set<Hero> heroes;
	
	/**
	 * The schemes in the set
	 */
	@OneToMany(mappedBy = "setId")
	private Set<Scheme> schemes;
	
	/**
	 * The masterminds in the set
	 */
	@OneToMany(mappedBy = "setId")
	private Set<Mastermind> masterminds;
	
	/**
	 * The villains in the set
	 */
	@OneToMany(mappedBy = "setId")
	private Set<Villain> villains;
	
	/**
	 * The henchmen villains in the set
	 */
	@OneToMany(mappedBy = "setId")
	private Set<Henchman> henchmen;

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
	 * @return the {@link #heroes}
	 */
	public Set<Hero> getHeroes() {
		return heroes;
	}
	
	/**
	 * @return the {@link #villains}
	 */
	public Set<Villain> getVillains() {
		return villains;
	}

	/**
	 * @return the {@link #masterminds}
	 */
	public Set<Mastermind> getMasterminds() {
		return masterminds;
	}

	/**
	 * @return the {@link #henchmen}
	 */
	public Set<Henchman> getHenchmen() {
		return henchmen;
	}

	/**
	 * @return the {@link #schemes}
	 */
	public Set<Scheme> getSchemes() {
		return schemes;
	}

}
