package games.lmdbg.server.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A hero card set
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Hero extends CardSet {
	/**
	 * Plays of the game which include this hero.
	 */
	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private Set<Play> plays;
	
	/** What team the hero belongs to. */
	@ManyToOne
	private Team team;

	/**
	 * @return the {@link #plays}
	 */
	public Set<Play> getPlays() {
		return plays;
	}

	/**
	 * @return the {@link #team}
	 */
	public Team getTeam() {
		return team;
	}
}
