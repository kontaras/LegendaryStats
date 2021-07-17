package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kon.legendarystatsserver.model.Play;

/**
 * A hero card set
 */
@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
public class Hero implements INamable {
	/** Database ID */
	@Id
	private Integer id;

	/** The name of the set for Marvel comics sets */
	private String marvelName;

	/** The name of the set for Marvel movie stills sets */
	private String mcuName;

	/** The name of the set for Legendary DXP sets */
	private String dxpName;

	/** Foreign key for the {@link GameSet} this card set belongs to. */
	private Integer setId;

	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_hero", inverseJoinColumns = { @JoinColumn(name = "play_id") })
	private java.util.Set<Play> plays;

	/**
	 * @return the {@link #id}
	 */
	public Integer getId() {
		return id;
	}

	@Override
	public String getMarvelName() {
		return marvelName;
	}

	@Override
	public String getMcuName() {
		return mcuName;
	}

	@Override
	public String getDxpName() {
		return dxpName;
	}

	/**
	 * @return the {@link #setId}
	 */
	public Integer getSetId() {
		return setId;
	}

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return plays;
	}
}
