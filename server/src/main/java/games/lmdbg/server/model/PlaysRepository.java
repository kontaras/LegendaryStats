package games.lmdbg.server.model;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface PlaysRepository extends CrudRepository<Play, Long> {
	@Query(value = 
			"SELECT p.mastermind AS id, "
			+ "	COUNT(*) AS played,"
			+ "	COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ "	COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'LOSS%' THEN 1 ELSE null END) AS lost  "
			+ "FROM Play p GROUP BY p.mastermind")
	List<IWinRate> findMastermindWinRates();
	
	@Query(value = 
			"SELECT p.scheme AS id, "
			+ "	COUNT(*) AS played,"
			+ "	COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ "	COUNT(CASE WHEN CAST(p.outcome AS string) LIKE 'LOSS%' THEN 1 ELSE null END) AS lost  "
			+ "FROM Play p GROUP BY p.scheme")
	List<IWinRate> findSchemeWinRates();
	
	@Query(value = 
			"SELECT h.hero_id AS id, "
			+ " COUNT(*) AS played, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'LOSS%' THEN 1 ELSE null END) AS lost  "
			+ "FROM play AS p JOIN play_hero AS h ON  h.play_id = p.id "
			+ "GROUP BY id",
			nativeQuery = true)
	List<IWinRate> findHeroWinRates();
	
	@Query(value = 
			"SELECT h.villain_id AS id, "
			+ " COUNT(*) AS played, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'LOSS%' THEN 1 ELSE null END) AS lost  "
			+ "FROM play AS p JOIN play_villain AS h ON  h.play_id = p.id "
			+ "GROUP BY id",
			nativeQuery = true)
	List<IWinRate> findVillainWinRates();
	
	@Query(value = 
			"SELECT h.henchman_id AS id, "
			+ " COUNT(*) AS played, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'WIN%' THEN 1 ELSE null END) AS won, "
			+ " COUNT(CASE WHEN p.outcome LIKE 'LOSS%' THEN 1 ELSE null END) AS lost  "
			+ "FROM play AS p JOIN play_henchman AS h ON  h.play_id = p.id "
			+ "GROUP BY id",
			nativeQuery = true)
	List<IWinRate> findHenchmenWinRates();
}
