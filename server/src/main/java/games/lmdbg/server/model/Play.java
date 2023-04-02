package games.lmdbg.server.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * A single play of the game
 */
@Entity
public class Play {
	/** Database ID */
	@Id
	private Long id;

	/**
	 * Account who played the game
	 */
	@ManyToOne
	private Account player;
	
	/**
	 * Date on which the game was played
	 */
	private Date playDate;

	/**
	 * Did the players win and how?
	 */
	private String outcome;

	/**
	 * Scheme played
	 */
	@Column(name = "scheme_id")
	private Integer scheme;

	/**
	 * Mastermind/commander fought
	 */
	@Column(name = "mastermind_id")
	private Integer mastermind;
	
	/**
	 * Board the game was played on
	 */
	@Column(name = "board_id")
	private Integer board;

	/**
	 * Heroes used in the game
	 */
	@ElementCollection
	@JoinTable(name = "play_hero", joinColumns = { @JoinColumn(name = "play_id") })
	private Set<Integer> heroes;

	/**
	 * Non-henchmen villains fought
	 */
	@ElementCollection
	@JoinTable(name = "play_villain", joinColumns = { @JoinColumn(name = "play_id") })
	private Set<Integer> villains;

	/**
	 * Henchmen villains fought
	 */
	@ElementCollection
	@JoinTable(name = "play_henchman", joinColumns = { @JoinColumn(name = "play_id") })
	private Set<Integer> henchmen;
	
	/**
	 * Henchmen villains fought
	 */
	@ElementCollection
	@JoinTable(name = "play_support", joinColumns = { @JoinColumn(name = "play_id") })
	private Set<Integer> supports;
	
	/**
	 * Starting decks used by players
	 */
	@OneToMany
	private Set<StarterPlay> starters;

	/**
	 * Any other notes the user may have
	 */
	private String notes;

	/**
	 * @return the id
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @return the player
	 */
	public Account getPlayer() {
		return this.player;
	}

	/**
	 * @return the {@link #playDate}
	 */
	public Date getPlayDate() {
		return this.playDate;
	}

	/**
	 * @return the outcome
	 */
	public String getOutcome() {
		return this.outcome;
	}

	/**
	 * @return the scheme
	 */
	public Integer getScheme() {
		return this.scheme;
	}

	/**
	 * @return the mastermind
	 */
	public Integer getMastermind() {
		return this.mastermind;
	}

	/**
	 * Getter for board
	 *
	 * @return the board
	 */
	public Integer getBoard()
	{
		return this.board;
	}

	/**
	 * @return the heros
	 */
	public Set<Integer> getHeroes() {
		return this.heroes;
	}

	/**
	 * @return the villains
	 */
	public Set<Integer> getVillains() {
		return this.villains;
	}

	/**
	 * @return the henchmen
	 */
	public Set<Integer> getHenchmen() {
		return this.henchmen;
	}

	/**
	 * @return the notes
	 */
	public String getNotes() {
		return this.notes;
	}

	/**
	 * @return the {@link #supports}
	 */
	public Set<Integer> getSupports() {
		return this.supports;
	}

	/**
	 * @return the {@link #starters}
	 */
	public Set<StarterPlay> getStarters() {
		return this.starters;
	}
}
