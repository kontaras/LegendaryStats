package games.lmdbg.server.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;

/**
 * An implementation of {@link UserDetailsService} using {@link AccountsRepository} as a source of truth.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	/** Accounts that exist in the system. */
	@Autowired
	private AccountsRepository accounts;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accounts.findByUserName(username);
		if (account == null) {
			throw new UsernameNotFoundException(username);
		}
		return User.withUsername(account.getUserName())
				.password(account.getPassword())
				.authorities("USER")
				.build();
	}

}
