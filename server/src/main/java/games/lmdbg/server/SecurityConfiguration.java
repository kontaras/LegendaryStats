
package games.lmdbg.server;

import games.lmdbg.server.view.AccountContoller;
import games.lmdbg.server.view.FrontPageController;
import games.lmdbg.server.view.PlayFormController;
import games.lmdbg.server.view.StaticPageContoller;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@SuppressWarnings("static-method")
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
				.requestMatchers(PlayFormController.PLAY_PATH, "/user/**").authenticated()
				.requestMatchers(AccountContoller.LOGIN_PATH, AccountContoller.REGISTRATION_PATH,
						StaticPageContoller.FAQ_PATH, FrontPageController.FRONT_PAGE_PATH, "/styles/**", "/scripts/**").permitAll()
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
