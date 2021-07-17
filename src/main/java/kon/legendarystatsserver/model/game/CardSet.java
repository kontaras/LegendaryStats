package kon.legendarystatsserver.model.game;

import javax.persistence.MappedSuperclass;

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
		return releaseId;
	}

}