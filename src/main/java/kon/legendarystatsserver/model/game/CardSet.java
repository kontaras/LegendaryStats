package kon.legendarystatsserver.model.game;

import javax.persistence.MappedSuperclass;

/**
 * A card set representing a character or a character group.
 */
@MappedSuperclass
public class CardSet  extends Namable {
	/** Foreign key for the {@link GameRelease} this card set belongs to. */
	public Integer setId;

	/**
	 * @return the {@link #setId}
	 */

	public Integer getSetId() {
		return setId;
	}

}