package games.lmdbg.server.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import games.lmdbg.server.model.game.Namable;

/**
 * Tests for {@link ViewHelper}
 */
class ViewHelperTest {
	/**
	 * Object under test
	 */
	ViewHelper vh = new ViewHelper();

	/**
	 * Test for {@link ViewHelper#getDisplayName(games.lmdbg.server.model.game.Namable)}
	 * 
	 * Test the trivial case: A {@link Namable} that has no name or has only one name set
	 */
	@Test
	void testGetDisplayNameTrivial() {
		//Test no names
		Namable nameable = Mockito.mock(Namable.class);
		Assertions.assertEquals("", vh.getDisplayName(nameable));
		
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("");
		Mockito.when(nameable.getMcuName()).thenReturn("");
		Mockito.when(nameable.getDxpName()).thenReturn("");
		Assertions.assertEquals("", vh.getDisplayName(nameable));
		
		//Test only one name
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>",
				vh.getDisplayName(nameable));
		
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>",
				vh.getDisplayName(nameable));
		
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));
	}
	
	/**
	 * Test displaying multiple names.
	 */
	@Test
	void testGetDisplayNameMultiple() {
		Namable nameable;
		
		//Test combinations of two names
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
		
		//Test all three names set
		nameable = Mockito.mock(Namable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span><span class=\"mcu\">MCU NAME</span><span class=\"dxp\">DXP NAME</span>",
				vh.getDisplayName(nameable));
	}

}