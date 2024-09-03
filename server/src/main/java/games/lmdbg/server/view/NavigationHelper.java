package games.lmdbg.server.view;

import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NavigationHelper {
	/**
	 * There is no reason for ever create an instance of the class.
	 */
	private NavigationHelper() {
		// Nothing to do
	}

	public static Map<String, String> commonPaths() {
		Map<String, String> paths = new LinkedHashMap<>();

		paths.put("Home", FrontPageController.FRONT_PAGE_PATH);
		paths.put("FAQ", StaticPageContoller.FAQ_PATH);

		return paths;
	}

	public static Map<String, String> authenticatedPaths() {
		Map<String, String> paths = new LinkedHashMap<>();

		paths.put("New Play", PlayFormController.PLAY_PATH);

		return paths;
	}

	public static Map<String, String> unauthenticatedPaths() {
		Map<String, String> paths = new LinkedHashMap<>();

		paths.put("Login", AccountContoller.LOGIN_PATH);
		paths.put("Register", AccountContoller.REGISTRATION_PATH);

		return paths;
	}
}
