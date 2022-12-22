
package games.lmdbg.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security Configuration
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
	/**
	 * Setup security rules
	 * 
	 * @param http Security builder to use
	 * @return Filter chain to use to authorize pages
	 * @throws Exception If there are issues configuring security
	 */
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests(requests -> requests
				.antMatchers("/play/**", "/user/**").authenticated()
				.antMatchers("/login*").permitAll()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout(LogoutConfigurer::permitAll);

		return http.build();
	}
	
	/**
	 * Get the users for the system
	 * @return	The controller to find and authenticate users
	 */
	@Bean
	public UserDetailsService userDetailsService() {
		@SuppressWarnings("deprecation")
		UserDetails user =
			 User.withDefaultPasswordEncoder()
				.username("user")
				.password("password")
				.roles("USER")
				.build();

		return new InMemoryUserDetailsManager(user);
	}
}
