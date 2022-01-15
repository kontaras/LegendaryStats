package games.lmdbg.server.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A release of the game, uniting several item sets together.
 */
@Entity
public class GameRelease extends Namable {
	/**
	 * The heroes in the release
	 */
	@OneToMany(mappedBy = "releaseId")
	private Set<Hero> heroes;
	
	/**
	 * The schemes in the release
	 */
	@OneToMany(mappedBy = "releaseId")
	private Set<Scheme> schemes;
	
	/**
	 * The masterminds in the release
	 */
	@OneToMany(mappedBy = "releaseId")
	private Set<Mastermind> masterminds;
	
	/**
	 * The villains in the release
	 */
	@OneToMany(mappedBy = "releaseId")
	private Set<Villain> villains;
	
	/**
	 * The henchmen villains in the release
	 */
	@OneToMany(mappedBy = "releaseId")
	private Set<Henchman> henchmen;

	/**
	 * @return the {@link #heroes}
	 */
	public Set<Hero> getHeroes() {
		return this.heroes;
	}
	
	/**
	 * @return the {@link #villains}
	 */
	public Set<Villain> getVillains() {
		return this.villains;
	}

	/**
	 * @return the {@link #masterminds}
	 */
	public Set<Mastermind> getMasterminds() {
		return this.masterminds;
	}

	/**
	 * @return the {@link #henchmen}
	 */
	public Set<Henchman> getHenchmen() {
		return this.henchmen;
	}

	/**
	 * @return the {@link #schemes}
	 */
	public Set<Scheme> getSchemes() {
		return this.schemes;
	}

}
