
package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import games.lmdbg.server.model.IWinRate;
import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.set.core.Heroes;

/**
 *
 */
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class SqlWinRateTest {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Autowired
	private SqlWinRate testInstance;
	
	private final SimpleJdbcInsert playComponentInsert;
	
	private final SimpleJdbcInsert playInsert;
	
	private final SimpleJdbcInsert accountInsert;

	private Number account_id;
	
	private List<Number> playsToClean;
	
	/**
	 * 
	 */
	public SqlWinRateTest(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.playComponentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("play_component");
		this.playInsert =
				new SimpleJdbcInsert(jdbcTemplate).withTableName("new_play").usingGeneratedKeyColumns("id");
		this.accountInsert = new SimpleJdbcInsert(this.jdbcTemplate).withTableName("account")
				.usingGeneratedKeyColumns("id");
	}
	
	@Test
	void testHeroLookup() {
		Number winPlay = createPlay(account_id, "WIN", "SOLO");
		Number drawPlay = createPlay(account_id, "DRAW", "SOLO");
		Number lossPlay = createPlay(account_id, "LOSS", "SOLO");
		
		createPlayComponent(winPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(winPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getCAPTAIN_AMERICA().getId());
		
		createPlayComponent(drawPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(drawPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		
		createPlayComponent(lossPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		createPlayComponent(lossPlay, PlaySerializer.ComponentType.VILLAIN.getSqlValue(),
				Heroes.INSTANCE.getCYCLOPS().getId());
		
		List<IWinRate> winRates = this.testInstance.queryWinRates(PlaySerializer.ComponentType.HERO);
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
		account_id = createUser("test", "foober");
	}

	@BeforeEach
	protected void beforeEach() {
		playsToClean = new ArrayList<>();
	}
	
	@AfterEach
	protected void afterEach() {
		logPlays();
		logComponents();
		
		cleanupPlays();
	}
	
	private void cleanupPlays() {
		String query = "DELETE FROM play_component "
				+ "WHERE play_id IN " + playsToClean.stream().map(Number::toString).collect(Collectors.joining(", ", "(", ")"));
		int deleted = this.jdbcTemplate.update(query);
		System.out.println(deleted);
	}
	
	@Test
	void testGetSetWinRates() {
		Number winPlay = createPlay(account_id, "WIN", "SOLO");
		Number drawPlay = createPlay(account_id, "DRAW", "SOLO");
		Number lossPlay = createPlay(account_id, "LOSS", "SOLO");
		
		createPlayComponent(winPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(winPlay, PlaySerializer.ComponentType.HERO.getSqlValue(), -1);
		
		createPlayComponent(drawPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getDEADPOOL().getId());
		createPlayComponent(drawPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		
		createPlayComponent(lossPlay, PlaySerializer.ComponentType.HERO.getSqlValue(),
				Heroes.INSTANCE.getEMMA_FROST().getId());
		createPlayComponent(lossPlay, PlaySerializer.ComponentType.VILLAIN.getSqlValue(),
				Heroes.INSTANCE.getCYCLOPS().getId());
		
		Map<CardSet, IWinRate> winRates = this.testInstance.getSetWinRates(PlaySerializer.ComponentType.HERO);
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
	
	/**
	 * 
	 */
	protected void logComponents() {
		SqlRowSet playComponents = this.jdbcTemplate
				.queryForRowSet("SELECT play_id, c_type, component_id FROM play_component;");
		playComponents.beforeFirst();
		while (playComponents.next()) {
			System.out.println(String.format("play_id: %d, c_type: %s, component_id: %d",
					playComponents.getInt("play_id"), playComponents.getString("c_type"),
					playComponents.getInt("component_id")));
		}
	}
	
	/**
	 * 
	 */
	protected void logPlays() {
		SqlRowSet plays =
				this.jdbcTemplate.queryForRowSet("SELECT id, player_id, outcome, players FROM new_play;");
		plays.beforeFirst();
		while (plays.next()) {
			System.out
					.println(String.format("id: %d, player: %d, outcome: %s, plyars: %s", plays.getInt("id"),
							plays.getInt("player_id"), plays.getString("outcome"), plays.getString("players")));
		}
	}
	
	/**
	 * @param userName
	 * @param password
	 * @return
	 */
	public Number createUser(String userName, String password) {
		return this.accountInsert
				.executeAndReturnKey(Map.of("user_name", userName, "password", password));
	}
	
	public Number createPlay(Number playerId, String outcome, String players) {
		Number id = this.playInsert
				.executeAndReturnKey(Map.of("player_id", playerId, "outcome", outcome, "players", players));
		playsToClean.add(id);
		return id;
	}
	
	public void createPlayComponent(Number playId, String cType, Integer componentId) {
		this.playComponentInsert
				.execute(Map.of("play_id", playId, "c_type", cType, "component_id", componentId));
	}
}
