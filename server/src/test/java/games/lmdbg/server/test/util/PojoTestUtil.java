package games.lmdbg.server.test.util;

import org.junit.jupiter.api.BeforeAll;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;

/**
 * Utility to test pojos. 
 */
public class PojoTestUtil {

	/** Validator to use for POJO testing. */
	private static Validator readValidator;

	/**
	 * Initialize the validator
	 */
	@BeforeAll
	static void setUp() {
		readValidator = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new GetterTester()).build();
	}

	/**
	 * Validate that a given setter only POJO has correct behavior
	 * @param clazz The class to test
	 */
	protected static void validateSetOnlyPojo(Class<?> clazz) {
		readValidator.validate(PojoClassFactory.getPojoClass(clazz));
	}

}