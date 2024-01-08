package games.lmdbg.server.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import games.lmdbg.rules.model.Hero;
import games.lmdbg.rules.model.Nameable;
import games.lmdbg.rules.model.Team;

/**
 * Tests for {@link ViewHelper}
 */
class ViewHelperTest {
	/**
	 * Object under test
	 */
	ViewHelper vh = new ViewHelper();

	/**
	 * Test for
	 * {@link ViewHelper#getDisplayName(Nameable)}
	 * 
	 * Test the trivial case: A {@link Nameable} that has no name or has only one
	 * name set
	 */
	@Test
	void testGetDisplayNameTrivial() {
		// Test no names
		Nameable nameable = new Nameable(null, null, null);
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		nameable = new Nameable("", "", "");
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		// Test only one name
		nameable = new Nameable("MARVEL NAME", null, null);
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>", vh.getDisplayName(nameable));

		nameable = new Nameable(null, null, "MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>", vh.getDisplayName(nameable));

		nameable = new Nameable(null, "DXP NAME", null);
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>", vh.getDisplayName(nameable));
	}

	/**
	 * Test displaying multiple names.
	 */
	@Test
	void testGetDisplayNameMultiple() {
		Nameable nameable;

		// Test combinations of two names
		nameable = new Nameable("MARVEL NAME", "", "MCU NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span><span class=\"mcu\">MCU NAME</span>",
				vh.getDisplayName(nameable));

		nameable = new Nameable("MARVEL NAME", "DXP NAME", "");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));

		nameable = new Nameable("", "DXP NAME", "MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));

		// Test all three names set
		nameable = new Nameable("MARVEL NAME", "DXP NAME", "MCU NAME");
		Assertions.assertEquals(
				"<span class=\"marvel\">MARVEL NAME</span><span class=\"mcu\">MCU NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));
	}

	/**
	 * Test for {@link ViewHelper#getHeroDisplayName(Hero)}
	 * 
	 * Test the trivial case: A {@link Hero} that has no name or has only one name
	 * set
	 */
	@Test
	void testGetDisplayNameHeroTrivial() {
		// Test no names
		Team team = new Team(null, null, null);
		Hero nameable = new Hero(0, null, null, null, List.of(team));
		Assertions.assertEquals("", vh.getHeroDisplayName(nameable));

		team = new Team("", "", "");
		nameable = new Hero(0, "", "", "", List.of(team));
		Assertions.assertEquals("", vh.getHeroDisplayName(nameable));

		// Test only one name, with and without teams
		team = new Team("", "", "");
		nameable = new Hero(0, "MARVEL NAME", "", "", List.of(team));
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>", vh.getHeroDisplayName(nameable));

		team = new Team("MARVEL TEAM", "", "");
		nameable = new Hero(0, "MARVEL NAME", "", "", List.of(team));
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME (MARVEL TEAM)</span>", vh.getHeroDisplayName(nameable));

		team = new Team("", "", "");
		nameable = new Hero(0, "", "", "MCU NAME", List.of(team));
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>", vh.getHeroDisplayName(nameable));

		team = new Team("", "", "MCU TEAM");
		nameable = new Hero(0, "", "", "MCU NAME", List.of(team));
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME (MCU TEAM)</span>", vh.getHeroDisplayName(nameable));

		team = new Team("", "", "");
		nameable = new Hero(0, "", "DXP NAME", "", List.of(team));
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>", vh.getHeroDisplayName(nameable));

		team = new Team("", "DXP TEAM", "");
		nameable = new Hero(0, "", "DXP NAME", "", List.of(team));
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME (DXP TEAM)</span>", vh.getHeroDisplayName(nameable));
	}

	/**
	 * Check that {@link ViewHelper#getDisplayName(Nameable)} will delegate to
	 * {@link ViewHelper#getHeroDisplayName(Hero)}
	 */
	@Test
	void testDelegation() {
		Team team = new Team(null, null, "MCU TEAM");
		Nameable nameable = new Hero(0, null, null, "MCU NAME", List.of(team));

		Assertions.assertEquals("<span class=\"mcu\">MCU NAME (MCU TEAM)</span>", vh.getDisplayName(nameable));
	}
	
	/**
	 * Test {@link ViewHelper#asCollection(Object)}
	 */
	@Test
	void testAsCollection() {
		Assertions.assertTrue(vh.asCollection(null).isEmpty());
		
		Collection<Object> someCollection = new ArrayList<>(); 
		Assertions.assertEquals(someCollection, vh.asCollection(someCollection));
		
		Object something = new Object();
		Collection<?> somethingAsCollection = vh.asCollection(something);
		Assertions.assertEquals(1, somethingAsCollection.size());
		Assertions.assertTrue(somethingAsCollection.contains(something));
	}

}