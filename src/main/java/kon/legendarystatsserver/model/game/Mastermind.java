package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kon.legendarystatsserver.model.Play;

/**
 * A mastermind in the game
 */
@Entity
public class Mastermind extends CardSet {
	@OneToMany(mappedBy = "mastermind")
	@JsonIgnore 
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return plays;
	}

}
