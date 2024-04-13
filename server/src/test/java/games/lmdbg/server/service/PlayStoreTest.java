
package games.lmdbg.server.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.service.PlayStore.MissingPlayException;

/**
 * Tests for {@link PlayStore}
 */
@SpringBootTest
public class PlayStoreTest extends SqlTest {
	@Autowired
	PlayStore underTest;
	
	/**
	 * @param jdbcTemplate
	 */
	public PlayStoreTest(@Autowired JdbcTemplate jdbcTemplate) {
		super(jdbcTemplate);
	}
	
	/**
	 * Test
	 * {@link PlayStore#createPlay(games.lmdbg.server.model.ServerPlay,
	 *  games.lmdbg.server.model.Account)}
	 */
	@Test
	void testCreatePlay() {
		Number player = createUser();
		ServerPlay play = new ServerPlay();
		
		play.setUser(player.intValue());
		play.setNotes("note");
		play.setOutcome(Outcome.WIN_DEFEAT_MASTERMIND);
		play.setPlayers(PlayerCount.THREE);
		play.setBoard(123);
		play.setScheme(456);
		play.setMastermind(789);
		play.getVillains().add(12);
		play.getVillains().add(345);
		play.getHenchmen().add(678);
		play.getHenchmen().add(901);
		play.getHeroes().add(234);
		play.getHeroes().add(567);
		play.getHeroes().add(890);
		play.getSupports().add(987);
		play.getSupports().add(654);
		play.getStarters().put(321, 1);
		play.getStarters().put(98, 2);
		
		Number playId = this.underTest.createPlay(play);
		
		playToClean(playId);
		
		Assertions.assertEquals(play, this.underTest.readPlay(playId.longValue(), player));
	}
	
	@Test
	void testNegative() {
		Assertions.assertThrows(MissingPlayException.class, () -> this.underTest.readPlay(-1l, null));
		
		Assertions.assertThrows(MissingPlayException.class, () -> this.underTest.readPlay(-1l, null));
	}
}
