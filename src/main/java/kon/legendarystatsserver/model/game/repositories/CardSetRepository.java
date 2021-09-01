package kon.legendarystatsserver.model.game.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import kon.legendarystatsserver.model.game.CardSet;

/**
 * Common repository methods to be used for all {@link CardSet}s
 */
@NoRepositoryBean
public interface CardSetRepository<T extends CardSet, ID> extends ReadOnlyRepository<T, ID> {
	@Query(value = "SELECT h.id AS id, "
			+ "         COUNT(*) AS played,"
			+ "         COUNT(CASE WHEN p.outcome = 'WIN' THEN 1 ELSE null END) AS won, "
			+ "         COUNT(CASE WHEN p.outcome = 'LOSS' THEN 1 ELSE null END) AS lost "
			+ "    FROM #{#entityName} h INNER JOIN h.plays p"
			+ "    GROUP BY h"
			+ "    ORDER BY COUNT(CASE WHEN p.outcome = 'WIN' THEN 1 ELSE null END) / "
			+ "             CAST(COUNT(*) AS float) DESC")
	List<IWinRate> findWinRates();
}
