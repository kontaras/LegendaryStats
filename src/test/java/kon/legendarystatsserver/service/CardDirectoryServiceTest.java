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
import kon.legendarystatsserver.model.game.Villain;
import kon.legendarystatsserver.model.game.repositories.HeroesRepository;
import kon.legendarystatsserver.model.game.repositories.VillainsRepository;

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
	VillainsRepository villains;
	
	@Mock
	Hero mockHero0, mockHero1, mockHero2;
	
	@Mock
	Villain mockVillain0, mockVillain1, mockVillain2;
	
	/**
	 * Create a {@link CardDirectoryService} with mock values injected prior to initialization.
	 */
	@BeforeAll
	void setUp() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		MockitoAnnotations.openMocks(this);
		
		Mockito.when(mockHero0.getId()).thenReturn(0);
		Mockito.when(mockHero1.getId()).thenReturn(1);
		Mockito.when(mockHero2.getId()).thenReturn(2);
		
		List<Hero> heroList = new ArrayList<>(3);
		heroList.add(mockHero0);
		heroList.add(mockHero1);
		
		Mockito.when(heroes.findAll()).thenReturn(heroList);
		
		Mockito.when(mockVillain0.getId()).thenReturn(0);
		Mockito.when(mockVillain1.getId()).thenReturn(1);
		Mockito.when(mockVillain2.getId()).thenReturn(2);
		
		List<Villain> villainList = new ArrayList<>(3);
		villainList.add(mockVillain0);
		villainList.add(mockVillain1);
		
		Mockito.when(villains.findAll()).thenReturn(villainList);
		
		
		Method initMethod = CardDirectoryService.class.getDeclaredMethod("init");
		initMethod.setAccessible(true);
		initMethod.invoke(directory);
		
		//Should not be in the dir, if the dir is caching
		heroList.add(mockHero2);
		villainList.add(mockVillain2);
	}
	
	@Test
	void testHeroCache() {
		Assertions.assertEquals(mockHero0, directory.getHeroById(0));
		Assertions.assertEquals(mockHero1, directory.getHeroById(1));
		Assertions.assertEquals(null, directory.getHeroById(2));
	}
	
	@Test
	void testVillainCache() {
		Assertions.assertEquals(mockVillain0, directory.getVillainById(0));
		Assertions.assertEquals(mockVillain1, directory.getVillainById(1));
		Assertions.assertEquals(null, directory.getVillainById(2));
	}
}
