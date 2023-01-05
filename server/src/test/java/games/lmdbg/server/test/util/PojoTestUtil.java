package games.lmdbg.server.test.util;

import org.junit.jupiter.api.BeforeAll;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.SetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

/**
 * Utility to test pojos. 
 */
public class PojoTestUtil {

	/** Validator to check POJO getters */
	private static Validator readValidator;
	
	/** Validator to check POJO setters */
	private static Validator setValidator;

	/**
	 * Initialize the validator
	 */
	@BeforeAll
	static void setUp() {
		readValidator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new GetterTester()).build();
		setValidator = ValidatorBuilder.create().with(new SetterMustExistRule()).with(new SetterTester()).build();
	}

	/**
	 * Validate that a given getter only POJO has correct behavior
	 * @param clazz The class to test
	 */
	protected static void validateGetOnlyPojo(Class<?> clazz) {
		readValidator.validate(PojoClassFactory.getPojoClass(clazz));
	}
	
	/**
	 * Validate that a given get and set POJO has correct behavior
	 * @param clazz The class to test
	 */
	protected static void validateGetSetPojo(Class<?> clazz) {
		readValidator.validate(PojoClassFactory.getPojoClass(clazz));
		setValidator.validate(PojoClassFactory.getPojoClass(clazz));
	}

}