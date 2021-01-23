package kon.legendarystatsserver.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import kon.legendarystatsserver.model.game.Henchman;
import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.Mastermind;
import kon.legendarystatsserver.model.game.Villain;

@Entity
public class Play {
	/** Database ID */
	@Id
	private Long id;
	
	@ManyToOne
	private User player;
	
	private String outcome;
	
	@ManyToOne
	private Mastermind mastermind;
	
	@ManyToOne
	private Hero hero1;
	
	@ManyToOne
	private Hero hero2;
	
	@ManyToOne
	private Hero hero3;
	
	@ManyToOne
	private Hero hero4;
	
	@ManyToOne
	private Hero hero5;
	
	@ManyToOne
	private Hero hero6;
	
	@ManyToOne
	private Hero miscHero;
	
	@ManyToOne
	private Villain villain1;
	
	@ManyToOne
	private Villain villain2;
	
	@ManyToOne
	private Villain villain3;
	
	@ManyToOne
	private Villain villain4;
	
	@ManyToOne
	private Henchman henchman1;
	
	@ManyToOne
	private Henchman henchman2;
	
	@ManyToOne
	private Henchman henchman3;
}
