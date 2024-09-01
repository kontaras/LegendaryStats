package games.lmdbg.server;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import games.lmdbg.server.view.AccountContoller;
import games.lmdbg.server.view.NavigationHelper;
import jakarta.servlet.ServletRequest;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.FormLoginRequestBuilder;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class SecurityIntegrationTest {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	AccountContoller accounts;

	@BeforeAll
	public void beforeAll() {
		ServletRequest request = Mockito.mock(ServletRequest.class);
		Mockito.when(request.getParameterMap())
		        .thenReturn(Map.of("username", new String[] { "user" }, "password", new String[] { "password" }));
		accounts.register(request, null);
	}

	@Test
	public void loginWithValidUserThenAuthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin().user("user").password("password");

		mockMvc.perform(login).andExpect(authenticated().withUsername("user"));
	}

	@Test
	public void loginWithInvalidUserThenUnauthenticated() throws Exception {
		FormLoginRequestBuilder login = formLogin().user("invalid").password("invalidpassword");

		mockMvc.perform(login).andExpect(unauthenticated());
	}

	@ParameterizedTest
	@MethodSource("unsecuredResources")
	void accessUnsecuredResourceThenOk(String path) throws Exception {
		mockMvc.perform(get(path)).andExpect(status().isOk());
	}

	private Stream<String> unsecuredResources() {
		return Stream.concat(NavigationHelper.commonPaths().values().stream(),
		        NavigationHelper.unauthenticatedPaths().values().stream());
	}

	@ParameterizedTest
	@MethodSource("securedOnlyResources")
	public void accessSecuredResourceUnauthenticatedThenRedirectsToLogin(String path) throws Exception {
		mockMvc.perform(get(path)).andExpect(status().is3xxRedirection()).andExpect(redirectedUrlPattern("**/login"));
	}

	private Stream<String> securedOnlyResources() {
		return NavigationHelper.authenticatedPaths().values().stream();
	}

	@ParameterizedTest
	@MethodSource("securedResources")
	@WithMockUser
	public void accessSecuredResourceAuthenticatedThenOk(String path) throws Exception {
		mockMvc.perform(get(path)).andExpect(status().isOk()).andReturn();

	}

	private Stream<String> securedResources() {
		return Stream.concat(NavigationHelper.commonPaths().values().stream(),
		        NavigationHelper.authenticatedPaths().values().stream());
	}
}
