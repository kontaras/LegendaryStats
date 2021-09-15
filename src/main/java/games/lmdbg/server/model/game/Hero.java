package games.lmdbg.server.model.game;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import games.lmdbg.server.model.Play;

/**
 * A hero card set
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Hero extends CardSet {
	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return plays;
	}
}
