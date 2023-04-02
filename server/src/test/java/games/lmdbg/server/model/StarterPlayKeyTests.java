package games.lmdbg.server.model;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Tests for {@link StarterPlay.Key} logic beyond {@link GameModelPojoTests#testStarterPlay()}
 */
@SuppressWarnings("static-method")
class StarterPlayKeyTests
{
	
	/**
	 * Test {@link StarterPlay.Key#hashCode()}
	 * 
	 * @throws NoSuchFieldException	Logic error
	 * @throws IllegalAccessException	If there are problems changing private values
	 */
	@Test
	final void testHashCode() throws NoSuchFieldException, IllegalAccessException
	{
		StarterPlay.Key instance = new StarterPlay.Key();
		
		setPrivateFieldValue(instance, "starter", Integer.valueOf(7));
		
		
		Play play = Mockito.mock(Play.class);
		Mockito.when(play.getId()).thenReturn((Long.valueOf(10)));
		setPrivateFieldValue(instance, "play", play);
		
		Assertions.assertEquals(477, instance.hashCode());
	}
	
	/**
	 * Test {@link StarterPlay.Key#equals(Object)}
	 * 
	 * @throws NoSuchFieldException	Logic error
	 * @throws IllegalAccessException	If there are problems changing private values
	 */
	@Test
	final void testEquals() throws NoSuchFieldException, IllegalAccessException
	{
		StarterPlay.Key instance1 = new StarterPlay.Key();
		
		Assertions.assertFalse(instance1.equals(null));
		
		Assertions.assertFalse(instance1.equals(new Object()));
		
		setPrivateFieldValue(instance1, "starter", Integer.valueOf(7));
		
		
		Play play1 = Mockito.mock(Play.class);
		Mockito.when(play1.getId()).thenReturn((Long.valueOf(10)));
		setPrivateFieldValue(instance1, "play", play1);
		
		Assertions.assertTrue(instance1.equals(instance1));
		
		StarterPlay.Key instance2 = new StarterPlay.Key();
		setPrivateFieldValue(instance2, "starter", Integer.valueOf(7));
		
		
		Play play2 = Mockito.mock(Play.class);
		Mockito.when(play2.getId()).thenReturn((Long.valueOf(10)));
		setPrivateFieldValue(instance2, "play", play2);
		
		Assertions.assertTrue(instance1.equals(instance2));
		
		setPrivateFieldValue(instance2, "starter", Integer.valueOf(8));
		Assertions.assertFalse(instance1.equals(instance2));
		setPrivateFieldValue(instance2, "starter", Integer.valueOf(7));
		
		Mockito.when(play2.getId()).thenReturn((Long.valueOf(9)));
		Assertions.assertFalse(instance1.equals(instance2));
	}
	
	
	/**
	 * Change the value of a private field
	 * 
	 * @param object Object that needs its field changed
	 * @param fieldName Field to set
	 * @param valueTobeSet New value to set it to
	 * @throws NoSuchFieldException If it is an invalid field
	 * @throws IllegalAccessException If there is an error setting the value
	 */
	public static void setPrivateFieldValue(StarterPlay.Key object, String fieldName, Object valueTobeSet) throws NoSuchFieldException, IllegalAccessException {
		Field field = StarterPlay.Key.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, valueTobeSet);
	}
}
