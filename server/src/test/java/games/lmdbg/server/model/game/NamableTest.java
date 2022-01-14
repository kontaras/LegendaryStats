package games.lmdbg.server.model.game;

import java.lang.reflect.Field;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;


/**
 * Tests for {@link Namable} logic beyond {@link GameModelPojoTests#testNamable()}
 */
@SuppressWarnings("static-method")
class NamableTest
{
	
	/**
	 * Test {@link Namable#hashCode()}
	 * 
	 * @throws NoSuchFieldException	Logic error
	 * @throws IllegalAccessException	If there are problems changing private values
	 */
	@Test
	final void testHashCode() throws NoSuchFieldException, IllegalAccessException
	{
		Namable testInstance1 = new Namable();
		Namable testInstance2 = new Namable();
		
		setPrivateFieldValue(testInstance1, "id", Integer.valueOf(123));
		Assertions.assertTrue(testInstance1.hashCode() == testInstance1.hashCode());
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(123));
		Assertions.assertTrue(testInstance1.hashCode() == testInstance2.hashCode());
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(122));
		Assertions.assertFalse(testInstance1.hashCode() == testInstance2.hashCode());
		
		setPrivateFieldValue(testInstance2, "id", null);
		Assertions.assertFalse(testInstance1.hashCode() == testInstance2.hashCode());
		
		setPrivateFieldValue(testInstance1, "id", null);
		Assertions.assertTrue(testInstance1.hashCode() == testInstance2.hashCode());
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(123));
		Assertions.assertFalse(testInstance1.hashCode() == testInstance2.hashCode());
	}
	
	/**
	 * Test {@link Namable#equals(Object)}
	 * 
	 * @throws NoSuchFieldException	Logic error
	 * @throws IllegalAccessException	If there are problems changing private values
	 */
	@Test
	final void testEquals() throws NoSuchFieldException, IllegalAccessException
	{
		Namable testInstance1 = new Namable();
		Namable testInstance2 = new Namable();
		Hero testHero = new Hero();
		
		setPrivateFieldValue(testInstance1, "id", Integer.valueOf(123));
		Assertions.assertTrue(testInstance1.equals(testInstance1));
		
		Assertions.assertFalse(testInstance1.equals(null));
		
		setPrivateFieldValue(testHero, "id", Integer.valueOf(123));
		Assertions.assertFalse(testInstance1.equals(testHero));
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(123));
		Assertions.assertTrue(testInstance1.equals(testInstance2));
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(122));
		Assertions.assertFalse(testInstance1.equals(testInstance2));
		
		setPrivateFieldValue(testInstance2, "id", null);
		Assertions.assertFalse(testInstance1.equals(testInstance2));
		
		setPrivateFieldValue(testInstance1, "id", null);
		Assertions.assertTrue(testInstance1.equals(testInstance2));
		
		setPrivateFieldValue(testInstance2, "id", Integer.valueOf(123));
		Assertions.assertFalse(testInstance1.equals(testInstance2));
	}
	
	/**
	 * Test {@link Namable#toString()}
	 * 
	 * @throws NoSuchFieldException	Logic error
	 * @throws IllegalAccessException	If there are problems changing private values
	 */
	@Test
	final void testToString() throws NoSuchFieldException, IllegalAccessException
	{
		Namable testInstance = new Namable();
		setPrivateFieldValue(testInstance, "id", Integer.valueOf(123));
		
		Assertions.assertEquals("123", testInstance.toString());
		
		setPrivateFieldValue(testInstance, "marvelName", "MRVL_NAME");
		Assertions.assertEquals("MRVL_NAME 123", testInstance.toString());
		setPrivateFieldValue(testInstance, "marvelName", "");
		
		setPrivateFieldValue(testInstance, "mcuName", "MCU_NAME");
		Assertions.assertEquals("MCU_NAME 123", testInstance.toString());
		setPrivateFieldValue(testInstance, "mcuName", "");
		
		setPrivateFieldValue(testInstance, "dxpName", "DXP_NAME");
		Assertions.assertEquals("DXP_NAME 123", testInstance.toString());
		setPrivateFieldValue(testInstance, "dxpName", "");
		
		setPrivateFieldValue(testInstance, "marvelName", "MRVL_NAME");
		setPrivateFieldValue(testInstance, "mcuName", "MCU_NAME");
		setPrivateFieldValue(testInstance, "dxpName", "DXP_NAME");
		Assertions.assertEquals("MRVL_NAME MCU_NAME DXP_NAME 123", testInstance.toString());
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
	public static void setPrivateFieldValue(Namable object, String fieldName, Object valueTobeSet) throws NoSuchFieldException, IllegalAccessException {
		Field field = Namable.class.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(object, valueTobeSet);
	}

}
