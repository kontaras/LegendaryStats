package games.lmdbg.server.model.game;

import jakarta.persistence.MappedSuperclass;

/**
 * A card set representing a character or a character group.
 */
@MappedSuperclass
public class CardSet  extends Namable {
	/** Foreign key for the {@link GameRelease} this card set belongs to. */
	private Integer releaseId;

	/**
	 * @return the {@link #releaseId}
	 */
	public Integer getReleaseId() {
		return this.releaseId;
	}

}