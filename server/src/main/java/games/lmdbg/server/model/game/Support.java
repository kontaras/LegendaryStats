package games.lmdbg.server.model.game;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A support deck that could be included as always available for a play
 */
@Entity
public class Support extends CardSet {
	/**
	 * Plays of the game which include this support deck.
	 */
	@ManyToMany
	@JsonIgnore
	@JoinTable(name = "play_support", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}

}
