package games.lmdbg.server.model.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A mastermind in the game
 */
@Entity
public class Mastermind extends CardSet {
	/** Plays including this card set */
	@OneToMany
	@JoinColumn(name = "mastermind_id")
	@JsonIgnore 
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}
}
