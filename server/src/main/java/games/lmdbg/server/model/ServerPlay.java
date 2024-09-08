package games.lmdbg.server.model;

import games.lmdbg.rules.model.Play;
import java.util.Objects;

public class ServerPlay extends Play {
	/** Database ID of the play */
	private long id;

	/** The user id of the play owner */
	private int user;

	/** User notes for the play */
	private String notes;

	/**
	 * Blank play
	 */
	public ServerPlay() {
		super();
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

	/**
	 * Get the user id
	 * 
	 * @return the user id
	 */
	public int getUser() {
		return user;
	}

	/**
	 * Set the user id
	 * 
	 * @param user id of the user
	 */
	public void setUser(int user) {
		this.user = user;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(this.id, this.notes, this.user);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		ServerPlay other = (ServerPlay) obj;
		return this.id == other.id && Objects.equals(this.notes, other.notes) && this.user == other.user;
	}
}
