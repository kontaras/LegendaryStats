package games.lmdbg.server.model.game;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.google.common.annotations.VisibleForTesting;

import games.lmdbg.server.model.Play;

/**
 * The usage of a {@link Starter} in a {@link Play}
 */
@Entity
@IdClass(PlayStarter.Key.class)
public class PlayStarter {
	/** Starting deck used */
	@Id
	@ManyToOne
	@JoinColumn(name="starter_id")
	private Starter starter;
	
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
	public PlayStarter(Starter starter, Play play, Integer quantity) {
		this.starter = starter;
		this.play = play;
		this.quantity = quantity;
	}

	/**
	 * @return the {@link #starter}
	 */
	public Starter getStarter() {
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
		@ManyToOne
		private Starter starter;
		
		/** id in the play table */
		@JoinColumn(name="play_id")
		@ManyToOne
		private Play play;
		
		/**
		 * @return the {@link #starter}
		 */
		public Starter getStarter() {
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
			return this.play.getId() == other.play.getId() && this.starter.getId() == other.starter.getId();
		}
		
		@Override
		public int hashCode() {
			final short PRIME = 47;
			return this.starter.getId().intValue() + this.play.getId().intValue() * PRIME;
		}	
	}
}
