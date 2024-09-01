package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 *
 */
public class SqlTest {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	private Schema schema;
	protected final SimpleJdbcInsert accountInsert;
	private List<Number> playsToClean;

	/**
	 * 
	 */
	public SqlTest(@Autowired JdbcTemplate jdbcTemplate) {
		this.accountInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("account").usingGeneratedKeyColumns("id");
	}

	@BeforeEach
	protected void beforeEach() {
		playsToClean = new ArrayList<>();
	}

	@AfterEach
	protected void afterEach() {
		logPlays();
		logComponents();
		logStarters();

		cleanupPlays();
	}

	private void cleanupPlays() {
		String query = "DELETE FROM " + Schema.COMPONENT_TABLE + " WHERE play_id IN "
		        + playsToClean.stream().map(Number::toString).collect(Collectors.joining(", ", "(", ")"));
		int deleted = this.jdbcTemplate.update(query);

		System.out.println(String.format("Deleted %d components", deleted));

		query = "DELETE FROM " + Schema.STARTER_TABLE + " WHERE play_id IN "
		        + playsToClean.stream().map(Number::toString).collect(Collectors.joining(", ", "(", ")"));
		deleted = this.jdbcTemplate.update(query);

		System.out.println(String.format("Deleted %d starters", deleted));

		query = "DELETE FROM " + Schema.PLAY_TABLE + " WHERE id IN "
		        + playsToClean.stream().map(Number::toString).collect(Collectors.joining(", ", "(", ")"));
		deleted = this.jdbcTemplate.update(query);
		Assertions.assertEquals(playsToClean.size(), deleted, "Could not clean up plays after test.");
		playsToClean.clear();
	}

	protected void logComponents() {
		SqlRowSet playComponents = this.jdbcTemplate
		        .queryForRowSet(String.format("SELECT play_id, c_type, component_id FROM %s;", Schema.COMPONENT_TABLE));
		playComponents.beforeFirst();
		while (playComponents.next()) {
			System.out.println(
			        String.format("play_id: %d, c_type: %s, component_id: %d", playComponents.getInt("play_id"),
			                playComponents.getString("c_type"), playComponents.getInt("component_id")));
		}
	}

	protected void logStarters() {
		SqlRowSet playComponents = this.jdbcTemplate
		        .queryForRowSet(String.format("SELECT play_id, starter_id, quantity FROM %s;", Schema.STARTER_TABLE));
		playComponents.beforeFirst();
		while (playComponents.next()) {
			System.out.println(
			        String.format("play_id: %d, starter_id: %s, quantity: %d", playComponents.getInt("play_id"),
			                playComponents.getString("starter_id"), playComponents.getInt("quantity")));
		}
	}

	protected void logPlays() {
		SqlRowSet plays = this.jdbcTemplate
		        .queryForRowSet(String.format("SELECT id, player_id, outcome, players FROM %s;", Schema.PLAY_TABLE));
		plays.beforeFirst();
		while (plays.next()) {
			System.out.println(String.format("id: %d, player: %d, outcome: %s, plyars: %s", plays.getInt("id"),
			        plays.getInt("player_id"), plays.getString("outcome"), plays.getString("players")));
		}
	}

	public Number createUser() {
		Random rng = new Random();
		long userName = rng.nextLong();
		long password = rng.nextLong();
		return this.accountInsert.executeAndReturnKey(Map.of("user_name", userName, "password", password));
	}

	public Number createPlay(Number playerId, String outcome, String players) {
		Number id = schema.getPlayInsert()
		        .executeAndReturnKey(Map.of("player_id", playerId, "outcome", outcome, "players", players));
		playToClean(id);
		return id;
	}

	protected void playToClean(Number id) {
		playsToClean.add(id);
	}

	public void createPlayComponent(Number playId, String cType, Integer componentId) {
		schema.getPlayComponentInsert()
		        .execute(Map.of("play_id", playId, "c_type", cType, "component_id", componentId));
	}

}
