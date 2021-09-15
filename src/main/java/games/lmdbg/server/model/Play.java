package games.lmdbg.server.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;

/**
 * A single play of the game
 */
@Entity
public class Play {
	/** Database ID */
	@Id
	private Long id;

	/**
	 * User who played the game
	 */
	@ManyToOne
	@JsonIgnore
	private User player;

	/**
	 * Did the players win and how?
	 */
	private String outcome;

	/**
	 * Scheme played
	 */
	@ManyToOne
	@JsonIgnore
	private Scheme scheme;

	/**
	 * Mastermind/commander fought
	 */
	@ManyToOne
	@JsonIgnore
	private Mastermind mastermind;

	/**
	 * Heroes used in the game
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "hero_id") })
	private Set<Hero> heroes;

	/**
	 * Extra hero used outside of the hero deck
	 */
	@ManyToOne
	@JsonIgnore
	private Hero miscHero;

	/**
	 * Non-henchmen villains fought
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_villain", inverseJoinColumns = { @JoinColumn(name = "villain_id") })
	private Set<Villain> villains;

	/**
	 * Henchmen villains fought
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_henchman", inverseJoinColumns = { @JoinColumn(name = "henchman_id") })
	private Set<Henchman> henchmen;

	/**
	 * Any other notes the user may have
	 */
	private String notes;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the player
	 */
	public User getPlayer() {
		return player;
	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return outcome;
	}

	/**
	 * @return the scheme
	 */
	public Scheme getScheme() {
		return scheme;
	}

	/**
	 * @return the mastermind
	 */
	public Mastermind getMastermind() {
		return mastermind;
	}

	/**
	 * @return the heros
	 */
	public Set<Hero> getHeroes() {
		return heroes;
	}

	/**
	 * @return the miscHero
	 */
	public Hero getMiscHero() {
		return miscHero;
	}

	/**
	 * @return the villains
	 */
	public Set<Villain> getVillains() {
		return villains;
	}

	/**
	 * @return the henchmen
	 */
	public Set<Henchman> getHenchmen() {
		return henchmen;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return notes;
	}
}
