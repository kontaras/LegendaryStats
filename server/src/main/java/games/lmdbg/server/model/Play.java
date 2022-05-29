package games.lmdbg.server.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.game.Board;
import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.PlayStarter;
import games.lmdbg.server.model.game.Support;
import games.lmdbg.server.model.game.Villain;

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
	@JsonIgnore
	private Account player;
	
	/**
	 * Date on which the game was played
	 */
	private Date playDate;

	/**
	 * Did the players win and how?
	 */
	@NotNull
	private String outcome;
	
	/**
	 * How many players played?
	 */
	@NotNull
	private String players;

	/**
	 * Scheme played
	 */
	@ManyToOne
	@JsonIgnore
	@NotNull
	private Scheme scheme;

	/**
	 * Mastermind/commander fought
	 */
	@ManyToOne
	@JsonIgnore
	@NotNull
	private Mastermind mastermind;
	
	/**
	 * Board the game was played on
	 */
	@ManyToOne
	@JsonIgnore
	@NotNull
	private Board board;

	/**
	 * Heroes used in the game
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "hero_id") })
	@NotNull
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
	@NotNull
	private Set<Villain> villains;

	/**
	 * Henchmen villains fought
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_henchman", inverseJoinColumns = { @JoinColumn(name = "henchman_id") })
	@NotNull
	private Set<Henchman> henchmen;
	
	/**
	 * Henchmen villains fought
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_support", inverseJoinColumns = { @JoinColumn(name = "support_id") })
	@NotNull
	private Set<Support> supports;
	
	/**
	 * Starting decks used by players
	 */
	@OneToMany(mappedBy = "play", cascade = CascadeType.ALL)
	@JsonIgnore
	//@JoinTable(name = "play_starter", mappededBy)
	private Set<PlayStarter> starters;

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
	public Scheme getScheme() {
		return this.scheme;
	}

	/**
	 * @return the mastermind
	 */
	public Mastermind getMastermind() {
		return this.mastermind;
	}

	/**
	 * Getter for board
	 *
	 * @return the board
	 */
	public Board getBoard()
	{
		return this.board;
	}

	/**
	 * @return the heros
	 */
	public Set<Hero> getHeroes() {
		return this.heroes;
	}

	/**
	 * @return the miscHero
	 */
	public Hero getMiscHero() {
		return this.miscHero;
	}

	/**
	 * @return the villains
	 */
	public Set<Villain> getVillains() {
		return this.villains;
	}

	/**
	 * @return the henchmen
	 */
	public Set<Henchman> getHenchmen() {
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
	public Set<Support> getSupports() {
		return this.supports;
	}

	/**
	 * @return the {@link #starters}
	 */
	public Set<PlayStarter> getStarters() {
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
	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	/**
	 * @param mastermind the {@link #mastermind} to set
	 */
	public void setMastermind(Mastermind mastermind) {
		this.mastermind = mastermind;
	}

	/**
	 * @param board the {@link #board} to set
	 */
	public void setBoard(Board board) {
		this.board = board;
	}

	/**
	 * @param heroes the {@link #heroes} to set
	 */
	public void setHeroes(Set<Hero> heroes) {
		this.heroes = heroes;
	}

	/**
	 * @param miscHero the {@link #miscHero} to set
	 */
	public void setMiscHero(Hero miscHero) {
		this.miscHero = miscHero;
	}

	/**
	 * @param villains the {@link #villains} to set
	 */
	public void setVillains(Set<Villain> villains) {
		this.villains = villains;
	}

	/**
	 * @param henchmen the {@link #henchmen} to set
	 */
	public void setHenchmen(Set<Henchman> henchmen) {
		this.henchmen = henchmen;
	}

	/**
	 * @param supports the {@link #supports} to set
	 */
	public void setSupports(Set<Support> supports) {
		this.supports = supports;
	}

	/**
	 * @param starters the {@link #starters} to set
	 */
	public void setStarters(Set<PlayStarter> starters) {
		this.starters = starters;
	}

	/**
	 * @param notes the {@link #notes} to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}
}
