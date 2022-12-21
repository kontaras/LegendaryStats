package games.lmdbg.server.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.tinylog.Logger;

import games.lmdbg.server.model.Account;
import games.lmdbg.server.model.AccountsRepository;

@Controller
public class AccountContoller {
	@Autowired
	private AccountsRepository accounts;
	
	@GetMapping("register")
	public static String register() {
		return "registration";
	}

	@PostMapping("register")
	public String newPlay(ServletRequest request, Model model) {
		Account user = new Account();
		
		Map<String, String[]> params = request.getParameterMap();
		for (Entry<String, String[]> param : params.entrySet()) {
			Logger.debug(param.getKey() + Arrays.toString(param.getValue()));
		}

		String[] name = params.get("username");
		String[] pass = params.get("password");
		String[] email = params.get("email");

		List<String> errors = new ArrayList<>();
		if (name == null || name.length < 1 || name[0].strip().length() < 1) {
			errors.add("Missing user name");
		} else {
			if (accounts.existsByUserName(name[0])) {
				errors.add("Duplicate user name");
			}
			user.setUserName(name[0]);
		}
		
		if (email != null && email.length >= 1 && email[0].strip().length() < 1) {
			user.setEmail(email[0]);
		}

		if (pass == null || pass.length < 1 || pass[0].strip().length() < 1) {
			errors.add("Missing password");
		} else {
			user.setPassword(pass[0]);
		}
		if (errors.isEmpty()) {
			accounts.save(user);
			return "redirect:login?register";
		} else {
			model.addAttribute("user", user.getUserName());
			model.addAttribute("errors", errors);
			return "registration";
		}
	}
}
