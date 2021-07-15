package kon.legendarystatsserver.model.game;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import kon.legendarystatsserver.model.Play;

/**
 * A villain group in the game
 */
@Entity
public class Villain implements INamable {
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
	private Integer set_id;
	
	/**
	 * Plays of the game which include this villain group.
	 */
	@ManyToMany
	@JsonIgnore 
	@JoinTable(name = "play_villain", inverseJoinColumns = { @JoinColumn(name = "play_id") })
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
	 * @return the {@link #set_id}
	 */
	public Integer getSet_id() {
		return set_id;
	}

	/**
	 * @return the {@link #plays}
	 */
	public java.util.Set<Play> getPlays() {
		return plays;
	}

}
