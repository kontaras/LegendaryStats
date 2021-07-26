package kon.legendarystatsserver.model.game.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import kon.legendarystatsserver.model.game.Villain;

@RepositoryRestResource
public interface VillainsRepository extends ReadOnlyRepository<Villain, Integer> {

	@Query(value = "SELECT v.id AS id, "
			+ "         COUNT(*) AS played,"
			+ "         COUNT(CASE WHEN p.outcome = 'win' THEN 1 ELSE null END) AS won, "
			+ "         COUNT(CASE WHEN p.outcome = 'loss' THEN 1 ELSE null END) AS lost "
			+ "    FROM Villain v INNER JOIN v.plays p"
			+ "    GROUP BY v"
			+ "    ORDER BY COUNT(CASE WHEN p.outcome = 'win' THEN 1 ELSE null END) / "
			+ "             CAST(COUNT(*) AS float) DESC")
	List<IWinRate> findVillainWinRates();
}
