package games.lmdbg.server.service;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.service.Schema.ComponentType;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.tinylog.Logger;

@Component
public class PlayStore {

	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Schema schema;

	public PlayStore(@Autowired JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Transactional
	public Number createPlay(ServerPlay play) {
		Map<String, Object> playValues = new HashMap<>();
		playValues.put("player_id", play.getUser());
		playValues.put("notes", play.getNotes());
		playValues.put("outcome", play.getOutcome());
		playValues.put("players", play.getPlayers());

		Number playId = this.schema.getPlayInsert().executeAndReturnKey(playValues);

		Set<TypedComponent> components = extractComponents(play);

		storeComponents(playId, components);

		storeStarters(play.getStarters(), playId);

		return playId;
	}

	public ServerPlay readPlay(Long id, Number expectedAccount) {
		ServerPlay play = new ServerPlay();

		// @formatter:off
		String query = "SELECT player_id, notes, outcome, players FROM " 
				+ Schema.PLAY_TABLE 
				+ " WHERE id = ?;";
		// @formatter:on

		AtomicBoolean missing = new AtomicBoolean(true);

		this.jdbcTemplate.query(query, row -> {
			int player = row.getInt("player_id");
			if (expectedAccount.intValue() != player) {
				throw new UnauthorizedException();
			}

			missing.setPlain(false);
			play.setUser(player);
			play.setNotes(row.getString("notes"));
			play.setOutcome(Outcome.valueOf(row.getString("outcome")));
			play.setPlayers(PlayerCount.valueOf(row.getString("players")));
		}, id);

		if (missing.get()) {
			throw new MissingPlayException();
		}

		Set<TypedComponent> components = readComponents(id);

		for (TypedComponent component : components) {
			Integer componentId = component.getId();
			switch (component.getType()) {
				case BOARD:
					play.setBoard(componentId);
					break;
				case HENCHMAN:
					play.getHenchmen().add(componentId);
					break;
				case HERO:
					play.getHeroes().add(componentId);
					break;
				case MASTERMIND:
					play.setMastermind(componentId);
					break;
				case SCHEME:
					play.setScheme(componentId);
					break;
				case SUPPORT:
					play.getSupports().add(componentId);
					break;
				case VILLAIN:
					play.getVillains().add(componentId);
					break;
				default:
					Logger.error("Cannot add component to play. Unknown component type {}", component.getType());
					break;
			}
		}

		play.setStarters(readStarters(id));

		return play;
	}

	private Map<Integer, Integer> readStarters(Long id) {
		// @formatter:off
		String query = "SELECT starter_id, quantity FROM " 
				+ Schema.STARTER_TABLE 
				+ " WHERE play_id = ?;";
		// @formatter:on

		Map<Integer, Integer> starters = new HashMap<>();

		this.jdbcTemplate.query(query, row -> {
			int starter = row.getInt("starter_id");
			int quantity = row.getInt("quantity");
			starters.put(starter, quantity);
		}, id);
		return starters;
	}

	protected Set<TypedComponent> readComponents(Long id) {
	// @formatter:off
		String query = "SELECT c_type, component_id FROM " 
				+ Schema.COMPONENT_TABLE 
				+ " WHERE play_id = ?;";
	// @formatter:on

		Set<TypedComponent> components = new HashSet<>();
		this.jdbcTemplate.query(query, row -> {
			ComponentType type = ComponentType.fromSqlValue(row.getString("c_type"));
			if (type == null) {
				Logger.error("SQL component type cannot be mapped to enum: {}", row.getString("c_type"));
				throw new RuntimeException();
			}
			int value = row.getInt("component_id");
			components.add(new TypedComponent(type, value));
		}, id);
		return components;
	}

	private void storeComponents(Number playId, Set<TypedComponent> components) {
		String componentsQuery =
		        "INSERT INTO " + Schema.COMPONENT_TABLE + " (play_id, c_type, component_id) " + " VALUES (?, ?, ?)";
		List<Object[]> tuples =
		        components.stream().map(t -> new Object[] { playId, t.getType().getSqlValue(), t.getId() }).toList();
		int[] result = this.jdbcTemplate.batchUpdate(componentsQuery, tuples);

		if (result.length != components.size()) {
			throw new RuntimeException(
			        String.format("Wrong batch size. Expected %d but was %d.", components.size(), result.length));
		}

		for (int i = 0; i < result.length; i++) {
			if (result[i] != 1) {
				throw new RuntimeException("Could not insert component " + Arrays.toString(tuples.get(i)));
			}
		}
	}

	private Set<TypedComponent> extractComponents(ServerPlay play) {
		Set<TypedComponent> components = new HashSet<>();

		components.add(new TypedComponent(Schema.ComponentType.SCHEME, play.getScheme()));
		components.add(new TypedComponent(Schema.ComponentType.MASTERMIND, play.getMastermind()));
		components.add(new TypedComponent(Schema.ComponentType.BOARD, play.getBoard()));

		components.addAll(convertComponentSet(Schema.ComponentType.HERO, play.getHeroes()));
		components.addAll(convertComponentSet(Schema.ComponentType.VILLAIN, play.getVillains()));
		components.addAll(convertComponentSet(Schema.ComponentType.HENCHMAN, play.getHenchmen()));
		components.addAll(convertComponentSet(Schema.ComponentType.SUPPORT, play.getSupports()));

		return components;
	}

	private void storeStarters(Map<Integer, Integer> starters, Number playId) {
		// @formatter:off
		String query = "INSERT INTO " + Schema.STARTER_TABLE
				+ " (play_id, starter_id, quantity) "
				+ " VALUES (?,?,?)";
		// @formatter:on

		List<Object[]> tuples =
		        starters.entrySet().stream().map(t -> new Object[] { playId, t.getKey(), t.getValue() }).toList();
		int[] result = this.jdbcTemplate.batchUpdate(query, tuples);

		if (result.length != starters.size()) {
			throw new RuntimeException(
			        String.format("Wrong batch size. Expected %d but was %d.", starters.size(), result.length));
		}

		for (int i = 0; i < result.length; i++) {
			if (result[i] != 1) {
				throw new RuntimeException("Could not insert component " + Arrays.toString(tuples.get(i)));
			}
		}
	}

	private Collection<TypedComponent> convertComponentSet(Schema.ComponentType type, Collection<Integer> components) {
		return components.stream().map((Integer id) -> new TypedComponent(type, id)).toList();
	}

	private static class TypedComponent {
		private Schema.ComponentType type;

		private Integer id;

		public TypedComponent(Schema.ComponentType type, Integer id) {
			this.type = type;
			this.id = id;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.id, this.type);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TypedComponent other = (TypedComponent) obj;
			return Objects.equals(this.id, other.id) && this.type == other.type;
		}

		public Schema.ComponentType getType() {
			return this.type;
		}

		public Integer getId() {
			return this.id;
		}
	}

	public static class MissingPlayException extends RuntimeException {
		private static final long serialVersionUID = -5498089655453926298L;
		// Nothing to add
	}

	public static class UnauthorizedException extends RuntimeException {
		private static final long serialVersionUID = 5687512914947492515L;
		// Nothing to add
	}
}
