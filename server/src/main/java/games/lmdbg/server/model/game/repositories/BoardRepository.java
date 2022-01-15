package games.lmdbg.server.model.game.repositories;

import games.lmdbg.server.model.game.Board;

/**
 * Repository to get {@link Board}
 */
public interface BoardRepository extends CardSetRepository<Board, Integer> {
	//No additional behavior beyond CardSetRepository
}
