package games.lmdbg.server.service;

import games.lmdbg.rules.model.CardSet;
import games.lmdbg.rules.set.CardLookupKt;
import games.lmdbg.server.model.IWinRate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class SqlWinRate {
	@Autowired
	JdbcTemplate jdbcTemplate;

	public Map<CardSet, IWinRate> getSetWinRates(Schema.ComponentType type) {
		Map<Integer, ? extends CardSet> lookupTable = lookupTable(type);
		List<IWinRate> winRates = queryWinRates(type);

		Map<CardSet, IWinRate> winRateTable = new HashMap<>(winRates.size());

		for (IWinRate winRate : winRates) {
			CardSet card = lookupTable.get(winRate.getId());
			if (card != null) {
				winRateTable.put(card, winRate);
			}
		}

		return winRateTable;
	}

	public List<IWinRate> queryWinRates(Schema.ComponentType type) {
		String query = "SELECT component.component_id AS id, " + " COUNT(*) AS total, "
		        + " COUNT(CASE WHEN play.outcome LIKE 'WIN%' THEN 1 ELSE null END) AS wins, "
		        + " COUNT(CASE WHEN play.outcome LIKE 'LOSS%' THEN 1 ELSE null END) AS losses, " + "FROM "
		        + Schema.COMPONENT_TABLE + " AS component " + " JOIN " + Schema.PLAY_TABLE + " AS play "
		        + " ON component.play_id = play.id " + "WHERE component.c_type = ? "
		        + "GROUP BY component.component_id " + "ORDER BY wins DESC, total DESC";

		return this.jdbcTemplate.query(query, (rs, rowNum) -> new WinRateImpl(rs.getInt("id"), rs.getInt("total"),
		        rs.getInt("wins"), rs.getInt("losses")), type.getSqlValue());
	}

	private class WinRateImpl implements IWinRate {

		private Integer id;

		private Integer total;

		private Integer won;

		private Integer lost;

		public WinRateImpl(Integer id, Integer total, Integer won, Integer lost) {
			this.id = id;
			this.total = total;
			this.won = won;
			this.lost = lost;
		}

		@Override
		public Integer getId() {
			return id;
		}

		@Override
		public Integer getPlayed() {
			return total;
		}

		@Override
		public Integer getWon() {
			return won;
		}

		@Override
		public Integer getLost() {
			return lost;
		}
	}

	public static Map<Integer, ? extends CardSet> lookupTable(Schema.ComponentType type) {
		switch (type) {
			case HERO:
				return CardLookupKt.getHeroesById();
			case SCHEME:
				return CardLookupKt.getSchemesById();
			case MASTERMIND:
				return CardLookupKt.getMastermindsById();
			case VILLAIN:
				return CardLookupKt.getVillainsById();
			case HENCHMAN:
				return CardLookupKt.getHenchmanById();
			case SUPPORT:
				return CardLookupKt.getSupportsById();
			case BOARD:
				return CardLookupKt.getBoardsById();
			default:
				// Should be unreachable
				throw new RuntimeException("This should be unreachable. Type: " + type.name());
		}
	}
}
