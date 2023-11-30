package games.lmdbg.server.model.game;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

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
		return this.plays;
	}

	/**
	 * @return the {@link #team}
	 */
	public Team getTeam() {
		return this.team;
	}
}
