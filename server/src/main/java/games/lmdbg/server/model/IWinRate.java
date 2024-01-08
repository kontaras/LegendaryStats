package games.lmdbg.server.model;

/**
 * A data structure to hold the total win/lose results containing some card set.
 */
public interface IWinRate {
	/**
	 * @return The id of the entity in its respective table.
	 */
	Integer getId();

	/**
	 * @return The total number of games played that include this entity.
	 */
	Integer getPlayed();

	/**
	 * @return The total number of games that resulted in a win and include this entity.
	 */
	Integer getWon();
	
	/**
	 * @return The total number of games that resulted in a loss and include this entity.
	 */
	Integer getLost();
}