package games.lmdbg.server.model.game;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * The game board that was used for a game
 */
@Entity
public class Board extends CardSet {
	/**
	 * Plays that include this board
	 */
	@OneToMany(mappedBy = "board")
	@JsonIgnore 
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}
}
