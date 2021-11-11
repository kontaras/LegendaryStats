package games.lmdbg.server.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Namable;
import games.lmdbg.server.model.game.Team;

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
	 * {@link ViewHelper#getDisplayName(games.lmdbg.server.model.game.Namable)}
	 * 
	 * Test the trivial case: A {@link Namable} that has no name or has only one
	 * name set
	 */
	@Test
	void testGetDisplayNameTrivial() {
		// Test no names
		Namable nameable = Mockito.mock(Namable.class);
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("");
		Mockito.when(nameable.getMcuName()).thenReturn("");
		Mockito.when(nameable.getDxpName()).thenReturn("");
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		// Test only one name
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>", vh.getDisplayName(nameable));
	}

	/**
	 * Test displaying multiple names.
	 */
	@Test
	void testGetDisplayNameMultiple() {
		Namable nameable;

		// Test combinations of two names
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span><span class=\"mcu\">MCU NAME</span>",
				vh.getDisplayName(nameable));

		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));

		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));

		// Test all three names set
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals(
				"<span class=\"marvel\">MARVEL NAME</span><span class=\"mcu\">MCU NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));
	}

	/**
	 * Test for {@link ViewHelper#getDisplayName(Hero)}
	 * 
	 * Test the trivial case: A {@link Hero} that has no name or has only one name
	 * set
	 */
	@Test
	void testGetDisplayNameHeroTrivial() {
		// Test no names
		Hero nameable = Mockito.mock(Hero.class);
		Team team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("");
		Mockito.when(nameable.getMcuName()).thenReturn("");
		Mockito.when(nameable.getDxpName()).thenReturn("");
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Assertions.assertEquals("", vh.getDisplayName(nameable));

		// Test only one name, with and without teams
		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(team.getMarvelName()).thenReturn("MARVEL TEAM");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME (MARVEL TEAM)</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(team.getMcuName()).thenReturn("MCU TEAM");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME (MCU TEAM)</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>", vh.getDisplayName(nameable));

		nameable = Mockito.mock(Hero.class);
		team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Mockito.when(team.getDxpName()).thenReturn("DXP TEAM");
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME (DXP TEAM)</span>", vh.getDisplayName(nameable));
	}

	/**
	 * Check that {@link ViewHelper#getDisplayName(Namable)} will delegate to
	 * {@link ViewHelper#getDisplayName(Hero)}
	 */
	@Test
	void testDelegation() {
		Hero nameable = Mockito.mock(Hero.class);
		Team team = Mockito.mock(Team.class);
		Mockito.when(nameable.getTeam()).thenReturn(team);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>", vh.getDisplayName((Namable) nameable));
	}

}