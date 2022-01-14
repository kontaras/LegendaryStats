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
		return this.id;
	}

	/**
	 * @return the name as released in mainline Legendary Marvel releases.
	 */
	public String getMarvelName() {
		return this.marvelName;
	}

	/**
	 * @return the name as released in Marvel Cinematic Universe releases.
	 */
	public String getMcuName() {
		return this.mcuName;
	}

	/**
	 * @return the name as released in the Legendary DXP app.
	 */
	public String getDxpName() {
		return this.dxpName;
	}

	@Override
	public int compareTo(Namable other) {
		return this.hashCode() - other.hashCode();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		if (!Strings.isNullOrEmpty(this.marvelName)) {
			builder.append(this.marvelName);
			builder.append(' ');
		}

		if (!Strings.isNullOrEmpty(this.mcuName)) {
			builder.append(this.mcuName);
			builder.append(' ');
		}

		if (!Strings.isNullOrEmpty(this.dxpName)) {
			builder.append(getDxpName());
			builder.append(' ');
		}

		builder.append(this.id);

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !this.getClass().equals(obj.getClass())) {
			return false;
		}
		
		if (this.id == null) {
			return ((Namable) obj).id == null;
		}
		return this.id.equals(((Namable) obj).id);
	}

	@Override
	public int hashCode() {
		if (this.id == null) {
			return this.getClass().hashCode();
		}
		return this.getClass().hashCode() + this.id.intValue();
	}
}