package games.lmdbg.server.model.game.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import games.lmdbg.server.model.game.CardSet;

/**
 * Common repository methods to be used for all {@link CardSet}s
 * @param <T> See {@link Repository}
 * @param <ID> See {@link Repository}
 */
@NoRepositoryBean
public interface CardSetRepository<T extends CardSet, ID> extends ReadOnlyRepository<T, ID> {
	/**
	 * Calculate win rates for all entries in the database
	 * 
	 * @return The win rates, sorted by {@link IWinRate#getWon()}/{@link IWinRate#getPlayed()}
	 */
	@Query(value = "SELECT h.id AS id, "
			+ "         COUNT(*) AS played,"
			+ "         COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ "         COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'LOSS%' THEN 1 ELSE null END) AS lost "
			+ "    FROM #{#entityName} h INNER JOIN h.plays p"
			+ "    GROUP BY h"
			+ "    ORDER BY CAST(COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'WIN%' THEN 1 ELSE null END) AS float) / "
			+ "             CAST(COUNT(*) AS float) DESC")
	List<IWinRate> findWinRates();
}
