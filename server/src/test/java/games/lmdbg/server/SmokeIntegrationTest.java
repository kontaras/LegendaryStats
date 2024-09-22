package games.lmdbg.server;

import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * A smoke test to verify that Spring can bring up a service and it has contents
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SmokeIntegrationTest {
	/** Port chosen by Spring to use for the test server. */
	@LocalServerPort
	private int port;

	/**
	 * Checks that the service will return something that looks like an HTML
	 * response.
	 */
	@Test
	void testHtmlResponse() {
		List<String> urls = List.of("/", "/faq", "/play");
		TestRestTemplate restTemplate2 = new TestRestTemplate("user", "password");
		for (String url : urls) {
			ResponseEntity<String> resp =
			        restTemplate2.getForEntity("http://localhost:" + this.port + url, String.class);
			Assertions.assertEquals(200, resp.getStatusCode().value());
			Assertions.assertTrue(resp.hasBody());
			Assertions.assertTrue(MediaType.TEXT_HTML.equalsTypeAndSubtype(resp.getHeaders().getContentType()));
			Assertions.assertTrue(resp.getBody().contains("<html lang=\"en\""));
		}
	}
}
