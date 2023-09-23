package games.lmdbg.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.common.annotations.VisibleForTesting;

/**
 * The usage of a {@link Starter} in a {@link Play}
 */
@Entity
@IdClass(StarterPlay.Key.class)
public class StarterPlay {
	/** Starting deck used */
	@Id
	private Integer starter;
	
	/** The play that the deck was used in */
	@Id
	@ManyToOne
	@JoinColumn(name="play_id")
	private Play play;
	
	/** The number of times this deck was used */
	private Integer quantity;
	
	/**
	 * @param starter
	 * @param play
	 * @param quantity
	 */
	public StarterPlay(Integer starter, Play play, Integer quantity) {
		this.starter = starter;
		this.play = play;
		this.quantity = quantity;
	}

	/**
	 * @return the {@link #starter}
	 */
	public Integer getStarter() {
		return this.starter;
	}

	/**
	 * @return the {@link #play}
	 */
	public Play getPlay() {
		return this.play;
	}

	/**
	 * @return the {@link #quantity}
	 */
	public Integer getQuantity() {
		return this.quantity;
	}

	/**
	 * Composite key used in the starter_plays table
	 */
	@VisibleForTesting
	static class Key implements Serializable {
		/**
		 * @see Serializable
		 */
		private static final long serialVersionUID = 1L;

		/** id in the starter table */
		@JoinColumn(name="starter_id")
		private Integer starter;
		
		/** id in the play table */
		@JoinColumn(name="play_id")
		@ManyToOne
		private Play play;
		
		/**
		 * @return the {@link #starter}
		 */
		public Integer getStarter() {
			return this.starter;
		}

		/**
		 * @return the {@link #play}
		 */
		public Play getPlay() {
			return this.play;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (obj == null || obj.getClass() != this.getClass()) {
				return false;
			}
			Key other = (Key) obj;
			return this.play.getId() == other.play.getId() && this.starter == other.starter;
		}
		
		@Override
		public int hashCode() {
			final short PRIME = 47;
			return this.starter.intValue() + this.play.getId().intValue() * PRIME;
		}	
	}
}
