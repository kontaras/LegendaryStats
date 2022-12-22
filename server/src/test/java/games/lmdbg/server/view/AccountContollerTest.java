package games.lmdbg.server.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import games.lmdbg.server.model.AccountsRepository;

/**
 * Tests for {@link AccountContoller}
 */
@SuppressWarnings("static-method")
@SpringBootTest
public class AccountContollerTest {
	@MockBean
	private AccountsRepository accounts;
	
	@Autowired
	AccountContoller underTest;
	
	/**
	 * Test {@link AccountContoller#loginPage()}
	 */
	@Test
	void testLoginPage() {
		Assertions.assertEquals("login", AccountContoller.loginPage());
	}
	
	/**
	 * Test {@link AccountContoller#registerPage()}
	 */
	@Test
	void testRegisterPage() {
		Assertions.assertEquals("registration", AccountContoller.registerPage());
	}
	
	/**
	 * Test {@link AccountContoller#register(javax.servlet.ServletRequest, org.springframework.ui.Model)}
	 */
	@Test
	void testRegister() {
		Model model = Mockito.mock(Model.class);
		ServletRequest request = Mockito.mock(ServletRequest.class);
		List<String> expectedErrors = new ArrayList<>();
		expectedErrors.add("Missing user name");
		expectedErrors.add("Missing password");
		Assertions.assertEquals("registration", underTest.register(request, model));
		Mockito.verify(model).addAttribute("errors", expectedErrors);
		Mockito.verify(accounts, Mockito.never()).save(ArgumentMatchers.any());
		
		Map<String, String[]> params = new HashMap<>();
		String[] empty = {};
		params.put("username", empty);
		params.put("password", empty);
		params.put("email", empty);
		Mockito.when(request.getParameterMap()).thenReturn(params);
		
		model = Mockito.mock(Model.class);
		
		Assertions.assertEquals("registration", underTest.register(request, model));
		Mockito.verify(model).addAttribute("errors", expectedErrors);
		Mockito.verify(accounts, Mockito.never()).save(ArgumentMatchers.any());
		
		String[] whiteSpace = {" "};
		params.put("username", whiteSpace);
		params.put("password", whiteSpace);
		params.put("email", whiteSpace);
		
		model = Mockito.mock(Model.class);
		
		Assertions.assertEquals("registration", underTest.register(request, model));
		Mockito.verify(model).addAttribute("errors", expectedErrors);
		Mockito.verify(accounts, Mockito.never()).save(ArgumentMatchers.any());
		
		Mockito.when(accounts.existsByUserName("bob")).thenReturn(true);
		params.put("username", List.of("bob").toArray(new String[1]));
		params.put("password", List.of("p@$$w0rd").toArray(new String[1]));
		params.put("email", List.of("e@mail").toArray(new String[1]));
		
		expectedErrors.clear();
		expectedErrors.add("Duplicate user name");
		
		Assertions.assertEquals("registration", underTest.register(request, model));
		Mockito.verify(model).addAttribute("errors", expectedErrors);
		Mockito.verify(model).addAttribute("user", "bob");
		Mockito.verify(accounts, Mockito.never()).save(ArgumentMatchers.any());
		
		Mockito.when(accounts.existsByUserName("bob")).thenReturn(false);
		Assertions.assertEquals("redirect:login?register", underTest.register(request, model));
		Mockito.verify(accounts).save(ArgumentMatchers.any());
	}
}
