package games.lmdbg.server.model.game;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 * A starting deck for the game
 */
@Entity
public class Starter extends CardSet {
	/**
	 * Plays of the game which include this starting deck.
	 */
	@OneToMany
	Set<StarterPlay> plays;
	
	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<StarterPlay> getPlays() {
		return this.plays;
	}
}
