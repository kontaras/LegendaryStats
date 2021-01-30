package kon.legendarystatsserver.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kon.legendarystatsserver.model.game.Henchman;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Mastermind;
import kon.legendarystatsserver.model.game.Scheme;
import kon.legendarystatsserver.model.game.Villain;

@Entity
public class Play {
	/** Database ID */
	@Id
	private Long id;

	@ManyToOne
	@JsonIgnore 
	private User player;

	private String outcome;

	@ManyToOne
	@JsonIgnore 
	private Scheme scheme;

	@ManyToOne
	@JsonIgnore 
	private Mastermind mastermind;

	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "hero_id") })
	private Set<Hero> heroes;

	@ManyToOne
	@JsonIgnore 
	private Hero miscHero;

	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_villain", inverseJoinColumns = { @JoinColumn(name = "villain_id") })
	private Set<Villain> villains;

	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_henchman", inverseJoinColumns = { @JoinColumn(name = "henchman_id") })
	private Set<Henchman> henchmen;

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
}
