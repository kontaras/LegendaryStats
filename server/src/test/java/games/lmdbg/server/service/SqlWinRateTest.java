
package games.lmdbg.server.service;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import games.lmdbg.server.model.IWinRate;
import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.set.core.Heroes;

/**
 * Tests for {@link SqlWinRate}
 */
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class SqlWinRateTest extends SqlTest {
	@Autowired
	private SqlWinRate testInstance;
	
	private Number account_id;
	
	public SqlWinRateTest(@Autowired JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}
	
	@Test
	void testHeroLookup() {
		Number winPlay = createPlay(account_id, "WIN", "SOLO");
		Number drawPlay = createPlay(account_id, "DRAW", "SOLO");
		Number lossPlay = createPlay(account_id, "LOSS", "SOLO");
		
		createPlayComponent(winPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(winPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getCAPTAIN_AMERICA().getId());
		
		createPlayComponent(drawPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(drawPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		
		createPlayComponent(lossPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		createPlayComponent(lossPlay, Schema.ComponentType.VILLAIN.getSqlValue(),
				Heroes.INSTANCE.getCYCLOPS().getId());
		
		List<IWinRate> winRates = this.testInstance.queryWinRates(Schema.ComponentType.HERO);
		Assertions.assertEquals(3, winRates.size());
		
		IWinRate rate = winRates.get(0);
		Assertions.assertEquals(Heroes.INSTANCE.getDEADPOOL().getId(), rate.getId());
		Assertions.assertEquals(2, rate.getPlayed());
		Assertions.assertEquals(1, rate.getWon());
		Assertions.assertEquals(0, rate.getLost());
		
		rate = winRates.get(1);
		Assertions.assertEquals(Heroes.INSTANCE.getCAPTAIN_AMERICA().getId(), rate.getId());
		Assertions.assertEquals(1, rate.getPlayed());
		Assertions.assertEquals(1, rate.getWon());
		Assertions.assertEquals(0, rate.getLost());
		
		rate = winRates.get(2);
		Assertions.assertEquals(Heroes.INSTANCE.getEMMA_FROST().getId(), rate.getId());
		Assertions.assertEquals(2, rate.getPlayed());
		Assertions.assertEquals(0, rate.getWon());
		Assertions.assertEquals(1, rate.getLost());
	}

	@BeforeAll
	protected void beforeAll() {
		account_id = createUser();
	}

	@Test
	void testGetSetWinRates() {
		Number winPlay = createPlay(account_id, "WIN", "SOLO");
		Number drawPlay = createPlay(account_id, "DRAW", "SOLO");
		Number lossPlay = createPlay(account_id, "LOSS", "SOLO");
		
		createPlayComponent(winPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(winPlay, Schema.ComponentType.HERO.getSqlValue(), -1);
		
		createPlayComponent(drawPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(drawPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		
		createPlayComponent(lossPlay, Schema.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		createPlayComponent(lossPlay, Schema.ComponentType.VILLAIN.getSqlValue(),
				Heroes.INSTANCE.getCYCLOPS().getId());
		
		Map<CardSet, IWinRate> winRates = this.testInstance.getSetWinRates(Schema.ComponentType.HERO);
		Assertions.assertEquals(2, winRates.size());
		
		IWinRate rate = winRates.get(Heroes.INSTANCE.getDEADPOOL());
		Assertions.assertEquals(Heroes.INSTANCE.getDEADPOOL().getId(), rate.getId());
		Assertions.assertEquals(2, rate.getPlayed());
		Assertions.assertEquals(1, rate.getWon());
		Assertions.assertEquals(0, rate.getLost());
		
		rate = winRates.get(Heroes.INSTANCE.getEMMA_FROST());
		Assertions.assertEquals(Heroes.INSTANCE.getEMMA_FROST().getId(), rate.getId());
		Assertions.assertEquals(2, rate.getPlayed());
		Assertions.assertEquals(0, rate.getWon());
		Assertions.assertEquals(1, rate.getLost());
	}
}
