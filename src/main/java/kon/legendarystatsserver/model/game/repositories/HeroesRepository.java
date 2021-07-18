package kon.legendarystatsserver.model.game.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Hero;

@RepositoryRestResource
public interface HeroesRepository extends ReadOnlyRepository<Hero, Integer> {

	@Query(value = "SELECT h.id AS id, "
			+ "         COUNT(*) AS played,"
			+ "         COUNT(CASE WHEN p.outcome = 'win' THEN 1 ELSE null END) AS won, "
			+ "         COUNT(CASE WHEN p.outcome = 'loss' THEN 1 ELSE null END) AS lost "
			+ "    FROM Hero h INNER JOIN h.plays p"
			+ "    GROUP BY h"
			+ "    ORDER BY COUNT(CASE WHEN p.outcome = 'win' THEN 1 ELSE null END) / "
			+ "             CAST(COUNT(*) AS float) DESC")
	List<IWinRate> findHeroWinRates();
}
