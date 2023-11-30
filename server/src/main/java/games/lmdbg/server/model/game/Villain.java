package games.lmdbg.server.model.game;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A villain group in the game
 */
@Entity
public class Villain extends CardSet {
	/**
	 * Plays of the game which include this villain group.
	 */
	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_villain", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}

}
