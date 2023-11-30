package games.lmdbg.server.model.game;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A single scheme in the game
 */
@Entity
public class Scheme extends CardSet {
	/** Plays including this card set */
	@OneToMany(mappedBy = "scheme")
	@JsonIgnore 
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}
}
