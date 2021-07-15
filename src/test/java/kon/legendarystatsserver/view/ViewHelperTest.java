package kon.legendarystatsserver.view;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import kon.legendarystatsserver.model.game.INamable;

/**
 * Tests for {@link ViewHelper}
 */
class ViewHelperTest {

	/**
	 * Test for {@link ViewHelper#getDisplayName(kon.legendarystatsserver.model.game.INamable)}
	 * 
	 * Test the trivial case: A {@link INamable} that has no name or has only one name set
	 */
	@Test
	void testGetDisplayNameTrivial() {
		//Test no names
		INamable nameable = Mockito.mock(INamable.class);
		Assertions.assertEquals("", ViewHelper.getDisplayName(nameable));
		
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("");
		Mockito.when(nameable.getMcuName()).thenReturn("");
		Mockito.when(nameable.getDxpName()).thenReturn("");
		Assertions.assertEquals("", ViewHelper.getDisplayName(nameable));
		
		//Test only one name
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span>",
				ViewHelper.getDisplayName(nameable));
		
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span>",
				ViewHelper.getDisplayName(nameable));
		
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"dxp\">DXP NAME</span>",
				ViewHelper.getDisplayName(nameable));
	}
	
	/**
	 * Test displaying multiple names.
	 */
	@Test
	void testGetDisplayNameMultiple() {
		INamable nameable;
		
		//Test combinations of two names
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span> / <span class=\"mcu\">MCU NAME</span>",
				ViewHelper.getDisplayName(nameable));
		
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span> / <span class=\"dxp\">DXP NAME</span>",
				ViewHelper.getDisplayName(nameable));
		
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"mcu\">MCU NAME</span> / <span class=\"dxp\">DXP NAME</span>",
				ViewHelper.getDisplayName(nameable));
		
		//Test all three names set
		nameable = Mockito.mock(INamable.class);
		Mockito.when(nameable.getMarvelName()).thenReturn("MARVEL NAME");
		Mockito.when(nameable.getMcuName()).thenReturn("MCU NAME");
		Mockito.when(nameable.getDxpName()).thenReturn("DXP NAME");
		Assertions.assertEquals("<span class=\"marvel\">MARVEL NAME</span> / <span class=\"mcu\">MCU NAME</span> / <span class=\"dxp\">DXP NAME</span>",
				ViewHelper.getDisplayName(nameable));
	}

}