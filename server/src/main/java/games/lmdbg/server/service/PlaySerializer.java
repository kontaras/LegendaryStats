package games.lmdbg.server.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.ServerPlay;

@Component
public class PlaySerializer {

	public static final String PLAY_TABLE = "new_play";
	public static final String COMPONENT_TABLE = "play_component";
	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert playInsert;
	
	public PlaySerializer(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.playInsert =
				new SimpleJdbcInsert(this.jdbcTemplate).withTableName(PLAY_TABLE).usingGeneratedKeyColumns("id");
	}
	
	//@Transactional
	public Number createPlay(ServerPlay play, Account user) {
		System.out.println(play);
		
		Map<String, Object> playValues = new HashMap<>();
		playValues.put("player_id", user.getId());
		playValues.put("notes", play.getNotes());
		playValues.put("outcome", play.getOutcome());
		playValues.put("players", play.getPlayers());
		
		Number playId = this.playInsert.executeAndReturnKey(playValues);
		
		List<Object[]> components = new ArrayList<>();
		
		populateComponents(components, playId, ComponentType.HERO, play.getHeroes());
		
		components.add(new Object[] {playId, ComponentType.SCHEME.getSqlValue(), play.getScheme()});
		
		components.add(new Object[] {playId, ComponentType.MASTERMIND.getSqlValue(), play.getMastermind()});
		
		populateComponents(components, playId, ComponentType.VILLAIN, play.getVillains());
		
		populateComponents(components, playId, ComponentType.HENCHMAN, play.getHenchmen());
		
		populateComponents(components, playId, ComponentType.SUPPORT, play.getSupports());
		
		components.add(new Object[] {playId, ComponentType.BOARD.getSqlValue(), play.getBoard()});
		
		String components_query = "INSERT INTO " + COMPONENT_TABLE
				+ " (play_id, c_type, component_id) "
				+ " VALUES (?, ?, ?)";
		int[] result = jdbcTemplate.batchUpdate(components_query, components);
		
		if (result.length != components.size()) {
			throw new RuntimeException(String.format("Wrong batch size. Expected %d but was %d.", components.size(), result.length));
		}
		
		for (int i = 0; i < result.length; i++) {
			if (result[i] != 1) {
				throw new RuntimeException("Could not insert component " + components.get(i));
			}
		}
		
		//TODO: Store starters
		
		
		logPlays();
		logComponents();
		
		return playId;
	}

	protected void populateComponents(List<Object[]> list, Number playId, ComponentType type, Collection<Integer> components) {
		for (Integer component : components) {
			list.add(new Object[] {playId, type.getSqlValue(), component});
		}
	}
	
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
	
	
	
	static public enum ComponentType {
		HERO("hero"),
		SCHEME("scheme"),
		MASTERMIND("mastermind"),
		VILLAIN("villain"),
		HENCHMAN("henchman"),
		SUPPORT("support"),
		BOARD("board");
		
		private String sqlValue;
		
		private ComponentType(String sqlValue) {
			this.sqlValue = sqlValue;
		}
		
		public String getSqlValue() {
			return this.sqlValue;
		}
	}
}
