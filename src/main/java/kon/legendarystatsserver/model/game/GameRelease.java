package kon.legendarystatsserver.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A release of the game, uniting several item sets together.
 */
@Entity
public class GameRelease extends Namable {
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
