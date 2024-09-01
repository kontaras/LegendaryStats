package games.lmdbg.server.service;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

/**
 * Constants and helpers for working with plays data in the database
 */
@Component
public class Schema {
	public static final String PLAY_TABLE = "play";
	public static final String COMPONENT_TABLE = "play_component";
	public static final String STARTER_TABLE = "play_starter";

	private JdbcTemplate jdbcTemplate;
	private SimpleJdbcInsert playInsert;
	private SimpleJdbcInsert playComponentInsert;
	private SimpleJdbcInsert playStarterInsert;

	public Schema(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		this.playInsert =
		        new SimpleJdbcInsert(this.jdbcTemplate).withTableName(PLAY_TABLE).usingGeneratedKeyColumns("id");
		this.playComponentInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(COMPONENT_TABLE);
		this.playStarterInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName(STARTER_TABLE);
	}

	public SimpleJdbcInsert getPlayInsert() {
		return playInsert;
	}

	public SimpleJdbcInsert getPlayComponentInsert() {
		return playComponentInsert;
	}

	public SimpleJdbcInsert getPlayStarterInsert() {
		return playStarterInsert;
	}

	public static enum ComponentType {
		HERO("hero"),
		SCHEME("scheme"),
		MASTERMIND("mastermind"),
		VILLAIN("villain"),
		HENCHMAN("henchman"),
		SUPPORT("support"),
		BOARD("board");

		private static Map<String, ComponentType> bySqlValue = new HashMap<String, ComponentType>();

		private String sqlValue;

		private ComponentType(String sqlValue) {
			this.sqlValue = sqlValue;
		}

		static {
			for (ComponentType type : values()) {
				bySqlValue.put(type.sqlValue, type);
			}
		}

		public String getSqlValue() {
			return this.sqlValue;
		}

		public static ComponentType fromSqlValue(String value) {
			return bySqlValue.get(value);
		}
	}
}
