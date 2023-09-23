package games.lmdbg.server.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

/**
 * A single play of the game
 */
@Entity
public class Play {
	/** Database ID */
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "play_id_generator")
	@SequenceGenerator(name = "play_id_generator", allocationSize = 1, sequenceName = "play_ids")
	@Column(name = "id", updatable = false, nullable = false)
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
	 * How many players played?
	 */
	@NotNull
	private String players;

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
	 * @return the {@link #players}
	 */
	public String getPlayers() {
		return players;
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

	/**
	 * @param id the {@link #id} to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param player the {@link #player} to set
	 */
	public void setPlayer(Account player) {
		this.player = player;
	}

	/**
	 * @param playDate the {@link #playDate} to set
	 */
	public void setPlayDate(Date playDate) {
		this.playDate = playDate;
	}

	/**
	 * @param outcome the {@link #outcome} to set
	 */
	public void setOutcome(String outcome) {
		this.outcome = outcome;
	}

	/**
	 * @param players the {@link #players} to set
	 */
	public void setPlayers(String players) {
		this.players = players;
	}

	/**
	 * @param scheme the {@link #scheme} to set
	 */
	public void setScheme(Integer scheme) {
		this.scheme = scheme;
	}

	/**
	 * @param mastermind the {@link #mastermind} to set
	 */
	public void setMastermind(Integer mastermind) {
		this.mastermind = mastermind;
	}

	/**
	 * @param board the {@link #board} to set
	 */
	public void setBoard(Integer board) {
		this.board = board;
	}

	/**
	 * @param heroes the {@link #heroes} to set
	 */
	public void setHeroes(Set<Integer> heroes) {
		this.heroes = heroes;
	}

	/**
	 * @param villains the {@link #villains} to set
	 */
	public void setVillains(Set<Integer> villains) {
		this.villains = villains;
	}

	/**
	 * @param henchmen the {@link #henchmen} to set
	 */
	public void setHenchmen(Set<Integer> henchmen) {
		this.henchmen = henchmen;
	}

	/**
	 * @param supports the {@link #supports} to set
	 */
	public void setSupports(Set<Integer> supports) {
		this.supports = supports;
	}

	/**
	 * @param starters the {@link #starters} to set
	 */
	public void setStarters(Set<StarterPlay> starters) {
		this.starters = starters;
	}

	/**
	 * @param notes the {@link #notes} to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
