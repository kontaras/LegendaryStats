package games.lmdbg.server.model.game.repositories;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests to insure that all Create, Update, and Delete methods are disabled for {@link ReadOnlyRepository}
 */
@ExtendWith(MockitoExtension.class)
class ReadOnlyRepositoryTests {
	@Mock(answer = Answers.CALLS_REAL_METHODS)
	private ReadOnlyRepository<Object, Integer> testRepo;
	
	/**
	 * Test {@link ReadOnlyRepository#save(Object)}
	 */
	@Test
	void testSave() {
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.save(null));
	}
	
	/**
	 * Test {@link ReadOnlyRepository#saveAll(Iterable)}
	 */
	@Test
	void testSaveAll() {
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.saveAll(null));
	}

	/**
	 * Test {@link ReadOnlyRepository#deleteById(Object)}
	 */
	@Test
	void testDeleteById() {
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.deleteById(0));
	}
	
	/**
	 * Test {@link ReadOnlyRepository#delete(Object)}
	 */
	@Test
	void testDelete() {
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.delete(null));
	}

	/**
	 * Test {@link ReadOnlyRepository#deleteAll()} and {@link ReadOnlyRepository#deleteAll(Iterable)}
	 */
	@Test
	void testDeleteAll() {
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.deleteAll());
		Assertions.assertThrows(UnsupportedOperationException.class,() -> testRepo.deleteAll(null));
	}
}
