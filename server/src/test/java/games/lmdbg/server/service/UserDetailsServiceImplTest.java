package games.lmdbg.server.service;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;

@SpringBootTest
class UserDetailsServiceImplTest {
	@MockBean
	private AccountsRepository accounts;

	@Autowired
	UserDetailsServiceImpl underTest;

	/**
	 * Tests for {@link UserDetailsServiceImpl#loadUserByUsername(String)}
	 */
	@Test
	void loadUserByUsernameTest() {
		String testUser = "bob";
		Assertions.assertThrows(UsernameNotFoundException.class, () -> underTest.loadUserByUsername(testUser));
		
		Account accnt = Mockito.mock(Account.class);
		Mockito.when(accnt.getUserName()).thenReturn("NAME");
		Mockito.when(accnt.getPassword()).thenReturn("PASS");
		
		Mockito.when(accounts.findByUserName(testUser)).thenReturn(accnt);
		
		UserDetails user = underTest.loadUserByUsername(testUser);
		Assertions.assertEquals("NAME", user.getUsername());
		Assertions.assertEquals("PASS", user.getPassword());
		
		List<? extends GrantedAuthority> authorities = List.copyOf(user.getAuthorities());
		Assertions.assertEquals(1, authorities.size());
		Assertions.assertEquals("USER", authorities.get(0).getAuthority());
	}
}
