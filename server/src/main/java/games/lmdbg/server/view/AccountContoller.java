package games.lmdbg.server.view;

import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;
import jakarta.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * Methods to handle user accounts.
 */
@Controller
public class AccountContoller {
	public static final String LOGIN_PATH = "/login";

	public static final String REGISTRATION_PATH = "/register";

	/** Accounts table */
	@Autowired
	private AccountsRepository accounts;

	@Autowired
	private PasswordEncoder passwordEncoder;

	/** There should never be an instance. */
	private AccountContoller() {
		// Nothing to do here
	}

	/**
	 * Serve up the Login page
	 * 
	 * @return The template for the page
	 */
	@GetMapping(LOGIN_PATH)
	public static String loginPage() {
		return "login";
	}

	/**
	 * Access the registration page
	 * 
	 * @return Registration template
	 */
	@GetMapping(REGISTRATION_PATH)
	public static String registerPage(Model model) {
		model.addAttribute("errors", List.of());
		return "registration";
	}

	/**
	 * Accept the registration form, validate it, and create a new account
	 * 
	 * @param request Request parameters to get account info from
	 * @param model   Data model to fill for template
	 * @return Next template to access
	 */
	@PostMapping(REGISTRATION_PATH)
	public String register(ServletRequest request, Model model) {
		Account user = new Account();

		Map<String, String[]> params = request.getParameterMap();

		String[] name = params.get("username");
		String[] pass = params.get("password");

		List<String> errors = new ArrayList<>();
		if (name == null || name.length < 1 || name[0].strip().length() < 1) {
			errors.add("Missing user name");
		} else {
			if (accounts.existsByUserName(name[0].strip())) {
				errors.add("Duplicate user name");
			}
			user.setUserName(name[0]);
		}

		if (pass == null || pass.length < 1 || pass[0].strip().length() < 1) {
			errors.add("Missing password");
		} else {
			user.setPassword(passwordEncoder.encode(pass[0].strip()));
		}
		if (errors.isEmpty()) {
			accounts.save(user);
			return "redirect:login?register";
		}
		model.addAttribute("user", user.getUserName());
		model.addAttribute("errors", errors);
		return "registration";
	}
}
