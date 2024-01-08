package games.lmdbg.server.model;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.Play;
import games.lmdbg.rules.model.PlayerCount;

public class ServerPlay extends Play {
	/** Database ID of the play */
	long id;
	
	/** User notes for the play */
	String notes;

	/**
	 * Blank play
	 */
	public ServerPlay() {
		super(Outcome.DRAW_DECK, PlayerCount.SOLO, -1, -1, Set.of(), Set.of(), Set.of(), Set.of(),
				Map.of(), -1);
	}

	/**
	 * Getter for id
	 *
	 * @return the id
	 */
	public long getId() {
		return this.id;
	}

	/**
	 * Getter for notes
	 *
	 * @return the notes
	 */
	public String getNotes() {
		return this.notes;
	}

	/**
	 * Set the value of id
	 *
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Set the value of notes
	 *
	 * @param notes the notes to set
	 */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id, notes);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if ( !super.equals(obj))
			return false;
		ServerPlay other = (ServerPlay) obj;
		return id == other.id && Objects.equals(notes, other.notes);
	}
}