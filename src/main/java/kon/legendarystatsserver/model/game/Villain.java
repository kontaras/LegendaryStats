package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kon.legendarystatsserver.model.Play;

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
		return plays;
	}

}
