package games.lmdbg.server.model.game;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.google.common.base.Strings;

/**
 * A common superclass for any card/card set that has a different name in
 * releases.
 */
@MappedSuperclass
public class Namable implements Comparable<Namable> {
	/** Database ID */
	@Id
	private Integer id;

	/** The name of the set for Marvel comics sets */
	private String marvelName;

	/** The name of the set for Marvel movie stills sets */
	private String mcuName;

	/** The name of the set for Legendary DXP sets */
	private String dxpName;

	/**
	 * @return the {@link #id}
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @return the name as released in mainline Legendary Marvel releases.
	 */
	public String getMarvelName() {
		return marvelName;
	}

	/**
	 * @return the name as released in Marvel Cinematic Universe releases.
	 */
	public String getMcuName() {
		return mcuName;
	}

	/**
	 * @return the name as released in the Legendary DXP app.
	 */
	public String getDxpName() {
		return dxpName;
	}

	@Override
	public int compareTo(Namable other) {
		return this.toString().compareTo(other.toString());
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (!Strings.isNullOrEmpty(marvelName)) {
			builder.append(marvelName);
			builder.append(' ');
		}

		if (!Strings.isNullOrEmpty(mcuName)) {
			builder.append(mcuName);
			builder.append(' ');
		}

		if (!Strings.isNullOrEmpty(mcuName)) {
			builder.append(getDxpName());
			builder.append(' ');
		}

		builder.append(id);

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return obj != null && this.getClass().equals(obj.getClass()) && this.id.equals(((Namable) obj).id);
	}
}