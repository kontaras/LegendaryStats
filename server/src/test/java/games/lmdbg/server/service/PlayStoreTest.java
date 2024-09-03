package games.lmdbg.server.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import games.lmdbg.server.model.ServerPlay;
import games.lmdbg.server.service.PlayStore.MissingPlayException;
import games.lmdbg.server.service.PlayStore.UnauthorizedException;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

/**
 * Tests for {@link PlayStore} that use mocked dependencies
 */
@SpringBootTest
class PlayStoreTest {
	@MockBean
	JdbcTemplate mockJdbc;

	@MockBean
	Schema mockSchema;

	@Autowired
	PlayStore underTest;

	/**
	 * Test negative scenarios that could go wrong when reading a play.
	 */
	@Test
	void testReadErrors() {
		// Test a play not being found
		Assertions.assertThrows(MissingPlayException.class, () -> this.underTest.readPlay(-1l, null));

		// Test a play belonging to another user
		Mockito.doAnswer(invocation -> {
			ResultSet result = mock(ResultSet.class);
			when(result.getInt("player_id")).thenReturn(2);
			((RowCallbackHandler) invocation.getArgument(1)).processRow(result);
			return null;
		}).when(this.mockJdbc).query(ArgumentMatchers.anyString(), ArgumentMatchers.any(RowCallbackHandler.class),
		        ArgumentMatchers.eq(-1l));
		Assertions.assertThrows(UnauthorizedException.class, () -> this.underTest.readPlay(-1l, 1));

		// Test a play with an unknown component type
		Mockito.doAnswer(invocation -> {
			ResultSet result = mock(ResultSet.class);
			when(result.getInt("player_id")).thenReturn(1);
			when(result.getString("outcome")).thenReturn("DRAW_DECK");
			when(result.getString("players")).thenReturn("SOLO");
			when(result.getString("c_type")).thenReturn("meeple");
			((RowCallbackHandler) invocation.getArgument(1)).processRow(result);
			return null;
		}).when(this.mockJdbc).query(ArgumentMatchers.anyString(), ArgumentMatchers.any(RowCallbackHandler.class),
		        ArgumentMatchers.eq(-1l));
		Assertions.assertThrows(RuntimeException.class, () -> this.underTest.readPlay(-1l, 1));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testWriteErrors() {
		SimpleJdbcInsert mockInstert = mock(SimpleJdbcInsert.class);
		when(mockInstert.executeAndReturnKey(ArgumentMatchers.anyMap())).thenReturn(2);
		when(mockSchema.getPlayInsert()).thenReturn(mockInstert);

		String componentString =
		        "INSERT INTO " + Schema.COMPONENT_TABLE + " (play_id, c_type, component_id) " + " VALUES (?, ?, ?)";
		String starterString =
		        "INSERT INTO " + Schema.STARTER_TABLE + " (play_id, starter_id, quantity) " + " VALUES (?,?,?)";

		ServerPlay testPlay = new ServerPlay();
		when(mockJdbc.batchUpdate(ArgumentMatchers.eq(componentString), ArgumentMatchers.anyList()))
		        .thenReturn(new int[1]);
		Exception thrown = Assertions.assertThrows(RuntimeException.class, () -> underTest.createPlay(testPlay));
		Assertions.assertEquals("Wrong batch size. Expected 3 but was 1.", thrown.getMessage());

		when(mockJdbc.batchUpdate(ArgumentMatchers.eq(componentString), ArgumentMatchers.anyList()))
		        .thenAnswer(invocation -> {
			        int[] answer = new int[3];
			        List<Object[]> components = (List<Object[]>) invocation.getArgument(1);
			        for (int i = 0; i < components.size(); i++) {
				        if (Arrays.equals(components.get(i), new Object[] { 2, "board", null })) {
					        answer[i] = 0;
				        } else {
					        answer[i] = 1;
				        }
			        }
			        return answer;
		        });
		thrown = Assertions.assertThrows(RuntimeException.class, () -> underTest.createPlay(testPlay));
		Assertions.assertEquals("Could not insert component [2, board, null]", thrown.getMessage());

		when(mockJdbc.batchUpdate(ArgumentMatchers.eq(componentString), ArgumentMatchers.anyList()))
		        .thenReturn(new int[] { 1, 1, 1 });
		when(mockJdbc.batchUpdate(ArgumentMatchers.eq(starterString), ArgumentMatchers.anyList()))
		        .thenReturn(new int[2]);
		thrown = Assertions.assertThrows(RuntimeException.class, () -> underTest.createPlay(testPlay));
		Assertions.assertEquals("Wrong batch size. Expected 0 but was 2.", thrown.getMessage());

		testPlay.setStarters(Map.of(1, 2, 3, 4));
		when(mockJdbc.batchUpdate(ArgumentMatchers.eq(starterString), ArgumentMatchers.anyList()))
		        .thenAnswer(invocation -> {
			        int[] answer = new int[2];
			        List<Object[]> components = (List<Object[]>) invocation.getArgument(1);
			        for (int i = 0; i < components.size(); i++) {
				        if (components.get(i)[1] == Integer.valueOf(3)) {
					        answer[i] = 0;
				        } else {
					        answer[i] = 1;
				        }
			        }
			        return answer;
		        });
		thrown = Assertions.assertThrows(RuntimeException.class, () -> underTest.createPlay(testPlay));
		Assertions.assertEquals("Could not insert component [2, 3, 4]", thrown.getMessage());
	}
}
