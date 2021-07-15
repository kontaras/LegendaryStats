package kon.legendarystatsserver.model.game;

/**
 * A common interface for any card/card set that has a different name in releases.
 */
public interface INamable {

	/**
	 * @return the name as released in mainline Legendary Marvel releases.
	 */
	String getMarvelName();

	/**
	 * @return the name as released in Marvel Cinematic Universe releases.
	 */
	String getMcuName();

	/**
	 * @return the name as released in the Legendary DXP app.
	 */
	String getDxpName();

}