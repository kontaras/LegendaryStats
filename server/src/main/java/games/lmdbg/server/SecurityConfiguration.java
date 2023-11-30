
package games.lmdbg.server;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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
				.requestMatchers("/play/**", "/user/**").authenticated()
				.requestMatchers("/login*", "/registration*").permitAll()
			)
			.formLogin(form -> form
				.loginPage("/login")
				.permitAll()
			)
			.logout(LogoutConfigurer::permitAll);

		return http.build();
	}
	
	/**
	 * The password encoding algorithm for the application
	 * @return A known secure password encoder.
	 */
	@Bean
	public PasswordEncoder encoder() {
	    return new BCryptPasswordEncoder();
	}
}
