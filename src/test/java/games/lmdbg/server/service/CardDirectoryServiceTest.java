package games.lmdbg.server.service;

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

import games.lmdbg.server.model.game.Henchman;
import games.lmdbg.server.model.game.Hero;
import games.lmdbg.server.model.game.Mastermind;
import games.lmdbg.server.model.game.Scheme;
import games.lmdbg.server.model.game.Villain;
import games.lmdbg.server.model.game.repositories.HenchmenRepository;
import games.lmdbg.server.model.game.repositories.HeroesRepository;
import games.lmdbg.server.model.game.repositories.MastermindsRepository;
import games.lmdbg.server.model.game.repositories.SchemesRepository;
import games.lmdbg.server.model.game.repositories.VillainsRepository;

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
	MastermindsRepository masterminds;
	
	@Mock
	HenchmenRepository henchmen;
	
	@Mock
	SchemesRepository schemes;
	
	@Mock
	Hero mockHero0, mockHero1, mockHero2;
	
	@Mock
	Villain mockVillain0, mockVillain1, mockVillain2;
	
	@Mock
	Mastermind mockMastermind0, mockMastermind1, mockMastermind2;
	
	@Mock
	Henchman mockHenchman0, mockHenchman1, mockHenchman2;
	
	@Mock
	Scheme mockScheme0, mockScheme1, mockScheme2;
	
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
		
		
		Mockito.when(mockMastermind0.getId()).thenReturn(0);
		Mockito.when(mockMastermind1.getId()).thenReturn(1);
		Mockito.when(mockMastermind2.getId()).thenReturn(2);
		
		List<Mastermind> mastermindList = new ArrayList<>(3);
		mastermindList.add(mockMastermind0);
		mastermindList.add(mockMastermind1);
		
		Mockito.when(masterminds.findAll()).thenReturn(mastermindList);
		
		
		Mockito.when(mockHenchman0.getId()).thenReturn(0);
		Mockito.when(mockHenchman1.getId()).thenReturn(1);
		Mockito.when(mockHenchman2.getId()).thenReturn(2);
		
		List<Henchman> henchmanList = new ArrayList<>(3);
		henchmanList.add(mockHenchman0);
		henchmanList.add(mockHenchman1);
		
		Mockito.when(henchmen.findAll()).thenReturn(henchmanList);
		
		
		Mockito.when(mockScheme0.getId()).thenReturn(0);
		Mockito.when(mockScheme1.getId()).thenReturn(1);
		Mockito.when(mockScheme2.getId()).thenReturn(2);
		
		List<Scheme> schemeList = new ArrayList<>(3);
		schemeList.add(mockScheme0);
		schemeList.add(mockScheme1);
		
		Mockito.when(schemes.findAll()).thenReturn(schemeList);
		
		
		Method initMethod = CardDirectoryService.class.getDeclaredMethod("init");
		initMethod.setAccessible(true);
		initMethod.invoke(directory);
		
		//Should not be in the dir, if the dir is caching
		heroList.add(mockHero2);
		villainList.add(mockVillain2);
		mastermindList.add(mockMastermind2);
		henchmanList.add(mockHenchman2);
		schemeList.add(mockScheme2);
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
	
	@Test
	void testMastermindCache() {
		Assertions.assertEquals(mockMastermind0, directory.getMastermindById(0));
		Assertions.assertEquals(mockMastermind1, directory.getMastermindById(1));
		Assertions.assertEquals(null, directory.getMastermindById(2));
	}
	
	@Test
	void testHenchmanCache() {
		Assertions.assertEquals(mockHenchman0, directory.getHenchmanById(0));
		Assertions.assertEquals(mockHenchman1, directory.getHenchmanById(1));
		Assertions.assertEquals(null, directory.getHenchmanById(2));
	}
	
	@Test
	void testSchemeCache() {
		Assertions.assertEquals(mockScheme0, directory.getSchemeById(0));
		Assertions.assertEquals(mockScheme1, directory.getSchemeById(1));
		Assertions.assertEquals(null, directory.getSchemeById(2));
	}
}
