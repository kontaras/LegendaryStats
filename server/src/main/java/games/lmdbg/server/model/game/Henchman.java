package games.lmdbg.server.model.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A henchman group
 */
@Entity
public class Henchman extends CardSet {
	/** Plays including this card set */
	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_henchman", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return this.plays;
	}

}
