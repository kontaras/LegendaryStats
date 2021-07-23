package kon.legendarystatsserver.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import kon.legendarystatsserver.model.game.Hero;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;

/**
 * Test {@link CardDirectoryService} caching
 */
@TestInstance(Lifecycle.PER_CLASS)
class CardDirectoryServiceTest {

	@InjectMocks
	private CardDirectoryService directory;
	
	@Mock
	HeroesRepository heroes;
	
	@Mock
	Hero mockHero0, mockHero1, mockHero2;
	
	/**
	 * Create a {@link CardDirectoryService} with mock values injected prior to initialization.
	 */
	@BeforeAll
	public void setUp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);
		
		Mockito.when(mockHero0.getId()).thenReturn(0);
		Mockito.when(mockHero1.getId()).thenReturn(1);
		Mockito.when(mockHero2.getId()).thenReturn(2);
		
		List<Hero> heroList = new ArrayList<>(3);
		heroList.add(mockHero0);
		heroList.add(mockHero1);
		
		Mockito.when(heroes.findAll()).thenReturn(heroList);
		
		Method initMethod = CardDirectoryService.class.getDeclaredMethod("init");
		initMethod.setAccessible(true);
		initMethod.invoke(directory);
		
		//Should not be in the dir, if the dir is caching
		heroList.add(mockHero2);
	}
	
	@Test
	public void testHeroCache() {
		Assertions.assertEquals(mockHero0, directory.getHeroById(0));
		Assertions.assertEquals(mockHero1, directory.getHeroById(1));
		Assertions.assertEquals(null, directory.getHeroById(2));
	}
}
