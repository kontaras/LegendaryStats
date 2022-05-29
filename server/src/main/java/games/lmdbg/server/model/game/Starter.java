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
	Set<PlayStarter> plays;
	
	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<PlayStarter> getPlays() {
		return this.plays;
	}
}
