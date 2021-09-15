package games.lmdbg.server.test.util;

import org.junit.jupiter.api.BeforeAll;

import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.test.impl.GetterTester;

public class PojoTestUtil {

	static Validator valid;

	@BeforeAll
	static void setUp() {
		valid = ValidatorBuilder.create().with(new GetterMustExistRule()).with(new GetterTester()).build();
	}

	protected void validate(Class<?> clazz) {
		valid.validate(PojoClassFactory.getPojoClass(clazz));
	}

}