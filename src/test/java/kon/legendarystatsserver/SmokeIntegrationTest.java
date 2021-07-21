package kon.legendarystatsserver;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

/**
 * A smoke test to verify that Spring can bring up a service and it has contents
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class SmokeIntegrationTest {
	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	/**
	 * Checks that the service will return something that looks like an HTML response.
	 */
	@Test
	void testHtmlResponse() {
		ResponseEntity<String> resp = restTemplate.getForEntity("http://localhost:" + port + "/", String.class);
		Assertions.assertEquals(200, resp.getStatusCodeValue());
		Assertions.assertTrue(resp.hasBody());
		Assertions.assertTrue(MediaType.TEXT_HTML.equalsTypeAndSubtype(resp.getHeaders().getContentType()));
		Assertions.assertTrue(resp.getBody().contains("<html lang=\"en\">"));
	}
}
